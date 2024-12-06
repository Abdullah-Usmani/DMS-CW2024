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
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

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

	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> projectiles = new ArrayList<>();
	private int currentNumberOfEnemies;
	protected final LevelView levelView;


	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract LevelView instantiateLevelView();

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	public Scene initializeScene() {
		initializeBackground();
		initializeKeyListeners();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void startLevel(String levelName, String enemyDetails, int killsNeeded, String controls) {
		showLevelOverlay(levelName, enemyDetails, killsNeeded, controls);

		// Delay starting the game until the overlay is shown
		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(e -> {
			background.requestFocus(); // Sets focus on the game background
			timeline.play();          // Starts the game timeline
		});
		delay.play();
	}

	private void showLevelOverlay(String levelName, String enemyDetails, int killsNeeded, String controls) {
		StackPane overlay = new StackPane();
		Label overlayText = new Label("Level: " + levelName + "\n" +
				"Enemies: " + enemyDetails + "\n" +
				"Kills Needed: " + killsNeeded + "\n" +
				"Controls: " + controls);

		overlayText.setFont(new Font("Arial", 24));
		overlay.getChildren().add(overlayText);
		root.getChildren().add(overlay);

		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(e -> root.getChildren().remove(overlay));
		delay.play();
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
		handleCollisions(projectiles, friendlyUnits);
		boolean userProjectileCollision = handleCollisions(projectiles, enemyUnits); 		// Process all collisions in a single pass
		boolean planeCollision = handleCollisions(enemyUnits, friendlyUnits);
		if (userProjectileCollision) {
			updateHitCount(true); 		// Update hit count only if user projectile hits an enemy
		}
		if (planeCollision) {
			user.takeDamage(1);		// Update hit count only if user projectile hits an enemy
		}
		removeAllDestroyedActors();       // Removes destroyed actors after collision checks
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

	private void fireProjectile() {
		Projectile userprojectile = (Projectile) user.fireProjectile();
		root.getChildren().add(userprojectile);
		projectiles.add(userprojectile);
	}
	private void fireMissile() {
		Projectile missile = (Projectile) user.fireMissile();
        root.getChildren().add(missile);
        projectiles.add(missile);
    }

	private void generateEnemyFire() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemy instanceof FighterPlane fighter) { // Check if the unit is a FighterPlane
                // Safe casting
                ActiveActorDestructible enemyProjectile = fighter.fireProjectile(); // Call fireProjectile method
				if (enemyProjectile != null) { // Ensure a projectile was created
					root.getChildren().add(enemyProjectile);
					projectiles.add((Projectile) enemyProjectile); // Add to projectiles list
				}
			}
		}
	}

	private boolean handleCollisions(List<ActiveActorDestructible> projectiles, List<ActiveActorDestructible> targets) {
		Iterator<ActiveActorDestructible> projectileIterator = projectiles.iterator();
		boolean collisionOccurred = false;
		while (projectileIterator.hasNext()) {
			ActiveActorDestructible projectile = projectileIterator.next();
			for (ActiveActorDestructible target : targets) {
				if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
					target.takeDamage(projectile.getDamage());
					double collisionX = projectile.getLayoutX() + projectile.getTranslateX();
					double collisionY = projectile.getLayoutY() + projectile.getTranslateY();
					handleCollisionEffects(collisionX, collisionY, projectile.getImageName());
					projectileIterator.remove();
					root.getChildren().remove(projectile);
					collisionOccurred = true;
					break;
				}
			}
		}
		return collisionOccurred;
	}

	private void handleCollisionEffects(double x, double y, String collisionType) {
		ExplosionEffect explosionEffect;

		switch (collisionType) {
			case "userfire.png":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						"/com/example/demo/audio/ricochet-1.mp3"
				);
			break;
			case "userplane1.png":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						75, 75, 1.5,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
				break;
			case "sidewinder.png":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						100, 100, 1.5,
						"/com/example/demo/audio/roblox-explosion-sound.mp3"
				);
				break;
			default:
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						"/com/example/demo/audio/roblox-explosion-sound.mp3"
				);
		}

		// Trigger the effect
		explosionEffect.createEffect(x, y, root);
	}


	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		projectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(projectiles);
	}

	private void removeDestroyedActors(List<? extends ActiveActorDestructible> actors) {
		List<? extends ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void updateHitCount(boolean collisionDetected) {
		if (collisionDetected) {
			user.incrementHitCount(); // Update hit count if a collision occurred
			System.out.println("User hit-count: " + user.getNumberOfHits());
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage(1);
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateKills(user.getNumberOfHits());
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
