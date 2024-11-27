package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.controller.Controller;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 30;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private boolean isPaused;

	private static Controller controller; // Reference to Controller
	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	protected final LevelView levelView;
	protected abstract LevelView instantiateLevelView();

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	public Scene initializeScene() {
		initializeBackground();
		initializeKeyListeners();
//		initializePauseMenuActions();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		timeline.stop();
		setChanged();
		notifyObservers(levelName);
	}

	private void updateScene() {
		spawnEnemyUnits();                // Spawns enemies
		updateActors();                   // Updates all actor positions
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();         // Check for enemies penetrating defenses
		boolean userProjectileCollisions = handleUserProjectileCollisions();
		boolean enemyProjectileCollisions = handleEnemyProjectileCollisions();
		boolean planeCollisions = handlePlaneCollisions();
		removeAllDestroyedActors();       // Removes destroyed actors after collision checks
		updateHitCount(userProjectileCollisions);
		updateLevelView();                // Update health, kills, etc.
		checkIfGameOver();                // Check game-over conditions
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
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
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile != null) { // Only add the projectile if it is not null
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors); // Remove from scene
		actors.removeAll(destroyedActors);            // Remove from list
	}
	private boolean handlePlaneCollisions() {
		return handleCollisions(friendlyUnits, enemyUnits);
	}

	private boolean handleUserProjectileCollisions() {
		boolean collision = handleCollisions(userProjectiles, enemyUnits);
		return collision;
	}


	private boolean handleEnemyProjectileCollisions() {
		return handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private boolean handleCollisions(List<ActiveActorDestructible> actors1,
									 List<ActiveActorDestructible> actors2) {
		boolean targetHit = false;
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
//				System.out.println("Checking collision between " + actor + " and " + otherActor);
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					targetHit = true;
				}
			}
		}
		return targetHit;
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateKills(user.getNumberOfHits());
	}

	private void updateHitCount(boolean collisionDetected) {
		if (collisionDetected) {
			user.incrementHitCount(); // Update kill count if a collision occurred
			System.out.println("User hit-count: " + user.getNumberOfHits());
//			System.out.println("User health: " + user.getHealth());
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	// Static method to set the Controller
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();

		// Show EndGameMenu with "You Win" message
		EndGameMenu endGameMenu = new EndGameMenu("You Win!", screenWidth, screenHeight);
		setupEndGameMenuActions(endGameMenu);
		root.getChildren().add(endGameMenu);
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();

		// Show EndGameMenu with "Game Over" message
		EndGameMenu endGameMenu = new EndGameMenu("Game Over", screenWidth, screenHeight);
		setupEndGameMenuActions(endGameMenu);
		root.getChildren().add(endGameMenu);
	}

	private void setupEndGameMenuActions(EndGameMenu endGameMenu) {
		endGameMenu.getExitButton().setOnAction(event -> {
			System.out.println("Exiting game...");
			System.exit(0); // Exit the application
		});

		endGameMenu.getRestartButton().setOnAction(event -> {
			System.out.println("Restarting game...");
			restartGame();
		});
	}

	private void restartGame() {
		try {
			controller.goToLevel(Controller.LEVEL_ONE_CLASS_NAME); // Call Controller to restart the game
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to restart the game.");
		}
	}

	public void pauseGame() {
		if (!isPaused) {
			System.out.println("Pausing game...");
			timeline.pause(); // Pauses the main game loop
			isPaused = true;
		}
	}

	public void resumeGame() {
		if (isPaused) {
			System.out.println("Resuming game...");
			timeline.play(); // Resumes the main game loop
			isPaused = false;
		}
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
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

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
