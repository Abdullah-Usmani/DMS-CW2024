package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Config;
import com.example.demo.actors.*;
import com.example.demo.controller.Controller;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.StartOverlay;
import com.example.demo.managers.CollisionManager;
import com.example.demo.managers.EffectManager;
import com.example.demo.managers.SoundManager;
import com.example.demo.menus.EndMenu;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;

public abstract class LevelParent extends Observable {

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private static Controller controller; // Reference to Controller
	private final Group root;
	private final GameLoop gameLoop; // Game loop for updating the scene
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final StartOverlay startOverlay;
	private final CollisionManager collisionManager;

    private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> friendlyProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
	private int currentNumberOfEnemies;
	protected final LevelView levelView;


	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.gameLoop = new GameLoop(this::updateScene, Config.MILLISECOND_DELAY);
        this.root = new Group();
		this.startOverlay = new StartOverlay(root, this::startGame);
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.user = new UserPlane(playerInitialHealth);
        SoundManager soundManager = new SoundManager();
        EffectManager effectManager = new EffectManager();
		this.collisionManager = new CollisionManager(root, this::updateHitCount, this::updateKillCount, effectManager, soundManager);

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - Config.SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		friendlyUnits.add(user);
	}

	// Static method to set the Controller
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	protected abstract LevelView instantiateLevelView();

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	private void updateScene() {
		spawnEnemyUnits();                // Spawns enemies
		updateActors();                   // Updates all actor positions
		generateEnemyFire();
		updateNumberOfEnemies();
		collisionManager.handleAllCollisions(friendlyUnits, enemyUnits, friendlyProjectiles, enemyProjectiles);
		removeActors();       // Removes destroyed actors after collision checks
		updateLevelView();                // Update health, kills, etc.
		checkIfGameOver();                // Check game-over conditions
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeKeyListeners();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		return scene;
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateKills(user.getNumberOfKills());
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

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

	// New method to initialize level after construction
	public void initializeLevel() {
		List<ActorInfo> actorsInfo = getActorsInfo(); // Abstract method to get level-specific actor info
		int killsNeeded = getKillsNeeded();           // Abstract method for kills needed in the level

		// Show the level overlay
		startOverlay.showLevelOverlay(getLevelName(), actorsInfo, killsNeeded);
	}

	protected abstract String getLevelName();
	protected abstract int getKillsNeeded();
	protected abstract List<ActorInfo> getActorsInfo();

	public void startGame() {
		background.requestFocus(); // Sets focus on the game background
		gameLoop.start();
	}

	public void goToNextLevel(String levelName) {
		gameLoop.stop();
		setChanged();
		notifyObservers(levelName);
	}

	private void fireProjectile() {
		Projectile userprojectile = (Projectile) user.fireProjectile();
		if (userprojectile != null) {
			root.getChildren().add(userprojectile);
			friendlyProjectiles.add(userprojectile);
		}
	}
	private void fireMissile() {
		Projectile usermissile = (Projectile) user.fireMissile();
		if (usermissile != null) {
			root.getChildren().add(usermissile);
			friendlyProjectiles.add(usermissile);
		}
	}

	private void generateEnemyFire() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemy instanceof FighterPlane fighter) { // Check if the unit is a FighterPlane
				// Safe casting
				ActiveActorDestructible enemyProjectile = fighter.fireProjectile(); // Call fireProjectile method
				if (enemyProjectile != null) { // Ensure a projectile was created
					root.getChildren().add(enemyProjectile);
					enemyProjectiles.add(enemyProjectile); // Add to projectiles list
				}
			}
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		friendlyProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(friendlyProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<? extends ActiveActorDestructible> actors) {
		List<? extends ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void updateHitCount() {
		user.incrementHitCount(); // Update hit count if a collision occurred
	}

	private void updateKillCount() {
		user.incrementKillCount(); // Update hit count if a collision occurred
	}

	public void stopGame() {
		gameLoop.stop();

		friendlyUnits.clear();
		enemyUnits.clear();
		friendlyProjectiles.clear();
		enemyProjectiles.clear();
	}

	protected void winGame() {
		stopGame();
		levelView.showWinImage();
		EndMenu endMenu = new EndMenu(
				screenWidth,
				screenHeight,
				() -> controller.goToMainMenu(),       // Exit to main menu logic
				() -> controller.restartGame(),       // Exit to main menu logic
				() -> controller.restartCurrentLevel() // Restart level logic
		);
		root.getChildren().add(endMenu);
	}

	protected void loseGame() {
		stopGame();
		levelView.showGameOverImage();
		EndMenu endMenu = new EndMenu(
				screenWidth,
				screenHeight,
				() -> controller.goToMainMenu(),       // Exit to main menu logic
				() -> controller.restartGame(),       // Exit to main menu logic
				() -> controller.restartCurrentLevel() // Restart level logic
		);
		root.getChildren().add(endMenu);
	}

	public void pauseGame() {
		gameLoop.stop();
	}

	public void resumeGame() {
		gameLoop.start();
	}

	protected UserPlane getUser() {
		return user;
	}

	public Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}