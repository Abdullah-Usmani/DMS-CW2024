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

	private final List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
	private final List<Projectile> projectiles = new ArrayList<>();
	private int currentNumberOfEnemies;
	protected final LevelView levelView;
	protected abstract LevelView instantiateLevelView();

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
		// Process all collisions in a single pass
		boolean userProjectileCollision = handleCollisions(projectiles, enemyUnits);
//		handleCollisions(enemyUnits, friendlyUnits);
		handleCollisions(projectiles, friendlyUnits);
		// Update hit count only if user projectile hits an enemy
		if (userProjectileCollision) {
//			System.out.println("User projectile collision detected");
			updateHitCount(true);
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
//				if (kc == KeyCode.SPACE) fireProjectile(user);
				if (kc == KeyCode.SPACE) user.fireProjectile();
//				if (kc == KeyCode.M) fireMissile(user);
				if (kc == KeyCode.M) user.fireMissile();
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
	}

	private void fireProjectile(FighterPlane plane) {
//		Projectile userprojectile = ProjectileFactory.createUserProjectile(plane.getProjectileXPosition(), plane.getProjectileYPosition());
		Projectile userprojectile = ProjectileFactory.createUserProjectile(plane.getProjectileXPosition(), plane.getProjectileYPosition());
		root.getChildren().add(userprojectile);
		projectiles.add(userprojectile);
	}
	private void fireMissile(FighterPlane plane) {
//		Projectile missile = ProjectileFactory.createMissile(plane.getMissileType(), plane.getProjectileInitialX(), plane.getProjectileInitialY());
		Projectile missile = ProjectileFactory.createUserMissile(plane.getProjectileXPosition(), plane.getProjectileYPosition());
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

	private boolean handleCollisions(List<Projectile> projectiles, List<ActiveActorDestructible> targets) {
		Iterator<Projectile> projectileIterator = projectiles.iterator();
		boolean collisionOccurred = false;
		while (projectileIterator.hasNext()) {
			Projectile projectile = projectileIterator.next();
			for (ActiveActorDestructible target : targets) {
				if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
//					System.out.println("User Health = " + user.getHealth());
					target.takeDamage(projectile.getDamage());
//					System.out.println("User Health = " + user.getHealth());
//					System.out.println("Target = " + target);
//					System.out.println("Projectile Damage = " + projectile.getDamage());
					projectileIterator.remove(); // Remove projectile on collision
					root.getChildren().remove(projectile);
//					System.out.println("User Health = " + user.getHealth());
					handleCollisionEffects(projectile.getLayoutX(), projectile.getLayoutY(), projectile.getImageName());
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
//			case "PLANE_PROJECTILE":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						50, 50, 1.0,
//						"/com/example/demo/audio/fortnite-pump.mp3"
//				);
//			break;
//			case "PLANE_PLANE":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						100, 100, 1.5,
//						"/com/example/demo/audio/fortnite-pump.mp3"
//				);
//				break;
//			case "PROJECTILE_PROJECTILE":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						30, 30, 0.5,
//						"/com/example/demo/audio/fortnite-pump.mp3"
//				);
//				break;
			default:
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						null
				);
		}

		// Trigger the effect
		explosionEffect.createEffect(x, y, root);
	}


	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		projectiles.forEach(Projectile::updateActor);
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
//		System.out.println("update hit count called");
		if (collisionDetected) {
			user.incrementHitCount(); // Update hit count if a collision occurred
			System.out.println("User hit-count: " + user.getNumberOfHits());
//			System.out.println("User health: " + user.getHealth());
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
