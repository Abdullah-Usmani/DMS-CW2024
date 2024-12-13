/**
 * Abstract class representing the parent structure of a game level.
 * Manages core level operations, including actor initialization, updates, and collision handling.
 */
package com.example.demo.levels;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

import com.example.demo.Config;
import com.example.demo.actors.*;
import com.example.demo.controller.Controller;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.StartOverlay;
import com.example.demo.managers.CollisionManager;
import com.example.demo.managers.EffectManager;
import com.example.demo.managers.AudioManager;
import com.example.demo.menus.EndMenu;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;

public abstract class LevelParent extends Observable {

	private boolean isGameOver = false;

	/**
	 * The screen height of the game.
	 */
	private final double screenHeight;

	/**
	 * The screen width of the game.
	 */
	private final double screenWidth;

	/**
	 * The maximum Y position for enemy actors.
	 */
	private final double enemyMaximumYPosition;

	/**
	 * Reference to the game controller.
	 */
	private static Controller controller;

	/**
	 * The root group for the scene.
	 */
	private final Group root;

	/**
	 * Game loop for updating the scene.
	 */
	private final GameLoop gameLoop;

	/**
	 * User's plane actor.
	 */
	private final UserPlane user;

	/**
	 * The scene for the level.
	 */
	private final Scene scene;

	/**
	 * The background image for the level.
	 */
	private final ImageView background;

	/**
	 * Overlay displayed at the start of the level.
	 */
	private final StartOverlay startOverlay;

	/**
	 * Manager for handling collisions in the level.
	 */
	private final CollisionManager collisionManager;

	/**
	 * List of friendly units in the level.
	 */
	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();

	/**
	 * List of enemy units in the level.
	 */
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();

	/**
	 * List of friendly projectiles in the level.
	 */
	private final List<ActiveActorDestructible> friendlyProjectiles = new ArrayList<>();

	/**
	 * List of enemy projectiles in the level.
	 */
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();

	/**
	 * The current number of enemy actors.
	 */
	private int currentNumberOfEnemies;

	/**
	 * The view associated with the level.
	 */
	protected final LevelView levelView;

	/**
	 * Constructs a new LevelParent.
	 *
	 * @param backgroundImageName the name of the background image resource.
	 * @param screenHeight        the height of the screen.
	 * @param screenWidth         the width of the screen.
	 * @param playerInitialHealth the initial health of the player's plane.
	 */
	protected LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.gameLoop = new GameLoop(this::updateScene, Config.MILLISECOND_DELAY);
		this.root = new Group();
		this.startOverlay = new StartOverlay(root, this::startGame);
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth);
		AudioManager AudioManager = new AudioManager();
		EffectManager effectManager = new EffectManager();
		this.collisionManager = new CollisionManager(root, this::updateHitCount, this::updateKillCount, effectManager, AudioManager);

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - Config.SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		friendlyUnits.add(user);
	}

	/**
	 * Sets the game controller.
	 *
	 * @param gameController the controller to be set.
	 */
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	/**
	 * Instantiates the level-specific view.
	 *
	 * @return the level view instance.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes friendly units in the level.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Checks if the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units in the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Updates the scene during each game loop iteration.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		collisionManager.handleAllCollisions(friendlyUnits, enemyUnits, friendlyProjectiles, enemyProjectiles);
		removeActors();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Initializes the scene for the level.
	 *
	 * @return the initialized scene.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeKeyListeners();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		return scene;
	}

	/**
	 * Updates the level view, including health and kill counts.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.addKills(user.getNumberOfKills());
	}

	/**
	 * Initializes the background image for the level.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	/**
	 * Initializes key listeners for user input.
	 */
	private void initializeKeyListeners() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
				if (kc == KeyCode.M) fireMissile();
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
	}

	/**
	 * Initializes the level.
	 */
	public void initializeLevel() {
		resetGameState(); // Reset the game state
		List<ActorInfo> actorsInfo = getActorsInfo();
		int killsNeeded = getKillsNeeded();
		startOverlay.showLevelOverlay(getLevelName(), actorsInfo, killsNeeded);
	}

	/**
	 * Gets the name of the level.
	 *
	 * @return the level name.
	 */
	protected abstract String getLevelName();

	/**
	 * Gets the number of kills needed to complete the level.
	 *
	 * @return the number of kills needed.
	 */
	protected abstract int getKillsNeeded();

	/**
	 * Gets information about actors in the level.
	 *
	 * @return a list of actor information.
	 */
	protected abstract List<ActorInfo> getActorsInfo();

	/**
	 * Starts the game loop.
	 */
	public void startGame() {
		background.requestFocus();
		gameLoop.start();
	}

	/**
	 * Transitions to the next level.
	 *
	 * @param levelName the name of the next level.
	 */
	public void goToNextLevel(String levelName) {
		if (isGameOver) {
			return; // Prevent duplicate execution
		}

		isGameOver = true; // Set the flag to true
		PauseTransition transition = new PauseTransition();
		transition.setOnFinished(event -> {
			AudioManager.transitionAudio();
			stopGame();
			setChanged();
			notifyObservers(levelName);
		});
		transition.play();
	}

	/**
	 * Fires a projectile from the user.
	 */
	private void fireProjectile() {
		Projectile userProjectile = (Projectile) user.fireProjectile();
		if (userProjectile != null) {
			root.getChildren().add(userProjectile);
			friendlyProjectiles.add(userProjectile);
		}
	}

	/**
	 * Fires a missile from the user.
	 */
	private void fireMissile() {
		Projectile userMissile = (Projectile) user.fireMissile();
		if (userMissile != null) {
			root.getChildren().add(userMissile);
			friendlyProjectiles.add(userMissile);
		}
	}

	/**
	 * Generates enemy fire projectiles.
	 */
	private void generateEnemyFire() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemy instanceof FighterPlane fighter) {
				ActiveActorDestructible enemyProjectile = fighter.fireProjectile();
				if (enemyProjectile != null) {
					root.getChildren().add(enemyProjectile);
					enemyProjectiles.add(enemyProjectile);
				}
			}
		}
	}

	/**
	 * Updates the position of all actors in the level.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		friendlyProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes destroyed actors from the level.
	 */
	private void removeActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(friendlyProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes destroyed actors from a list and the scene.
	 *
	 * @param actors the list of actors to process.
	 */
	private void removeDestroyedActors(List<? extends ActiveActorDestructible> actors) {
		List<? extends ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Updates the user's hit count.
	 */
	private void updateHitCount() {
		user.incrementHitCount();
	}

	/**
	 * Updates the user's kill count.
	 */
	private void updateKillCount() {
		user.incrementKillCount();
	}

	/**
	 * Stops the game loop and clears all actors.
	 */
	public void stopGame() {
		gameLoop.stop();
		friendlyUnits.clear();
		enemyUnits.clear();
		friendlyProjectiles.clear();
		enemyProjectiles.clear();
	}

	/**
	 * Handles the end of the game, either win or lose.
	 * Stops the game loop, plays the appropriate audio,
	 * displays the end screen image, and creates an EndMenu.
	 *
	 * @param isWin true if the user wins the game, false otherwise.
	 */

	protected void endGame(boolean isWin) {
		if (isGameOver) {
			return; // Prevent duplicate execution
		}

		isGameOver = true; // Set the flag to true

		// Stop the game loop
		PauseTransition transition = new PauseTransition();
		transition.setOnFinished(event -> {

			// Play the appropriate audio for win or lose
			if (isWin) {
				AudioManager.winAudio(); // Play win audio
			} else {
				AudioManager.loseAudio(); // Play lose audio
			}

			// Show the corresponding image for win or lose
			if (isWin) {
				levelView.showWinImage(); // Display win image
			} else {
				levelView.showLoseImage(); // Display lose image
			}

			// Create the EndMenu with logic for main menu, restart, and restart level options
			EndMenu endMenu = new EndMenu(
					screenWidth,
					screenHeight,
					() -> controller.goToMainMenu(),       // Logic to exit to main menu
					() -> controller.restartGame(),       // Logic to restart the game
					() -> controller.restartCurrentLevel() // Logic to restart the current level
			);
			// Add the EndMenu to the root node
			root.getChildren().add(endMenu);
			endGame(isWin);
		});
		transition.play();
		stopGame();
	}

	/**
	 * Resets the game state. Called when the level or game is restarted.
	 */
	public void resetGameState() {
		isGameOver = false; // Reset the game-over flag
	}

	/**
	 * Pauses the game by stopping the game loop.
	 */
	public void pauseGame() {
		gameLoop.stop(); // Stop the game loop
	}

	/**
	 * Resumes the game by starting the game loop.
	 */
	public void resumeGame() {
		gameLoop.start(); // Start the game loop
	}

	/**
	 * Gets the user's plane object.
	 *
	 * @return the UserPlane object controlled by the player.
	 */
	protected UserPlane getUser() {
		return user; // Return the user's plane
	}

	/**
	 * Gets the root node of the game scene graph.
	 *
	 * @return the root Group node.
	 */
	public Group getRoot() {
		return root; // Return the root node
	}

	/**
	 * Gets the current number of enemy units in the game.
	 *
	 * @return the number of enemy units currently in the game.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size(); // Return the size of the enemyUnits list
	}

	/**
	 * Adds a new enemy unit to the game.
	 * The enemy is added to both the list of enemy units and the scene graph.
	 *
	 * @param enemy the enemy unit to be added.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy); // Add the enemy to the list
		root.getChildren().add(enemy); // Add the enemy to the scene graph
	}

	/**
	 * Gets the maximum Y position that enemies can reach in the game.
	 *
	 * @return the maximum Y position for enemies.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition; // Return the maximum Y position for enemies
	}

	/**
	 * Checks whether the user's plane is destroyed.
	 *
	 * @return true if the user's plane is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed(); // Check if the user's plane is destroyed
	}

	/**
	 * Updates the count of current enemy units in the game.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size(); // Update the count of enemies
	}
}
