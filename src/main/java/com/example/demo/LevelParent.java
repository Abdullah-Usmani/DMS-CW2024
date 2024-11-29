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
//	private final List<ActiveActorDestructible> friendlyUnits;
//	private final List<ActiveActorDestructible> enemyUnits;
//	private final List<ActiveActorDestructible> userProjectiles;
//	private final List<ActiveActorDestructible> userMissiles;
//	private final List<ActiveActorDestructible> enemyProjectiles;
//	private final List<ActiveActorDestructible> enemyMissiles;

	private int currentNumberOfEnemies;
	protected final LevelView levelView;
	protected abstract LevelView instantiateLevelView();

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
        this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
//		this.friendlyUnits = new ArrayList<>();
//		this.enemyUnits = new ArrayList<>();
//		this.userProjectiles = new ArrayList<>();
//		this.userMissiles = new ArrayList<>();
//		this.enemyProjectiles = new ArrayList<>();
//		this.enemyMissiles = new ArrayList<>();

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
		handleCollisions(projectiles, enemyUnits);
		handleCollisions(projectiles, friendlyUnits);
		handleCollisions(enemyUnits, friendlyUnits);
		removeAllDestroyedActors();       // Removes destroyed actors after collision checks
//		updateHitCount(userProjectileCollisions);
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
		Projectile projectile = ProjectileFactory.createUserProjectile(
				user.getLayoutX() + user.getWidth(),
				user.getLayoutY() + user.getHeight() / 2
		);
		if (projectile != null) {
			root.getChildren().add(projectile);
			projectiles.add(projectile);
		}
	}
	private void fireMissile(FighterPlane plane) {
		Projectile missile = ProjectileFactory.createMissile(plane.getMissileType(), plane.getProjectileInitialX(), plane.getProjectileInitialY());
		if (missile != null) {
			root.getChildren().add(missile);
			projectiles.add(missile);
		}
	}

	private void handleCollisions(List<Projectile> projectiles, List<ActiveActorDestructible> targets) {
		Iterator<Projectile> projectileIterator = projectiles.iterator();
		while (projectileIterator.hasNext()) {
			Projectile projectile = projectileIterator.next();
			for (ActiveActorDestructible target : targets) {
				if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
					target.takeDamage(projectile.getDamage());
					projectileIterator.remove(); // Remove projectile on collision
					root.getChildren().remove(projectile);
					handleCollisionEffects(projectile.getLayoutX(), projectile.getLayoutY(), projectile.getImageName());
					break;
				}
			}
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> {
			Projectile projectile = ProjectileFactory.createEnemyProjectile(enemy.getProjectileInitialX(), enemy.getProjectileInitialY());
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		});
	}
//
//	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
//		if (projectile != null) {
//			root.getChildren().add(projectile);
//			enemyProjectiles.add(projectile);
//		}
//	}
//
//	private void spawnEnemyMissile(ActiveActorDestructible projectile) {
//		if (projectile != null) {
//			root.getChildren().add(projectile);
//			enemyMissiles.add(projectile);
//		}
//	}

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

//	private boolean handlePlaneCollisions() {
//		return handleCollisions(friendlyUnits, enemyUnits);
//	}
//
//	private boolean handleUserProjectileCollisions() { return  handleCollisions(userProjectiles, enemyUnits); }
//
//	private boolean handleUserMissileCollisions() { return  handleCollisions(userMissiles, enemyUnits); }
//
//	private boolean handleEnemyProjectileCollisions() { return handleCollisions(enemyProjectiles, friendlyUnits); }
//
//	private boolean handleEnemyMissileCollisions() { return handleCollisions(enemyMissiles, friendlyUnits); }
//
//	private boolean handleCollisions(List<ActiveActorDestructible> attackers, List<ActiveActorDestructible> defenders, String collisionType) {
//		boolean collisionOccurred = false;
//
//		for (ActiveActorDestructible defender : defenders) {
//			for (ActiveActorDestructible attacker : attackers) {
//				if (attacker.getBoundsInParent().intersects(defender.getBoundsInParent())) {
//					defender.takeDamage(attacker.getDamage()); // Apply attacker's damage
//					attacker.destroy(); // Destroy the projectile/missile
//					handleCollisionEffects(attacker.getLayoutX(), attacker.getLayoutY(), collisionType); // Add visual/audio effects
//					collisionOccurred = true;
//				}
//			}
//		}
//
//		return collisionOccurred;
//	}
	private void handleCollisionEffects(double x, double y, String collisionType) {
		ExplosionEffect explosionEffect;

		switch (collisionType) {
			case "PLANE_PROJECTILE":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
				break;
			case "PLANE_PLANE":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						100, 100, 1.5,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
				break;
			case "PROJECTILE_PROJECTILE":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						30, 30, 0.5,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
				break;
			default:
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
		}

		// Trigger the effect
		explosionEffect.createEffect(x, y, root);
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
			user.incrementHitCount(); // Update hit count if a collision occurred
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
