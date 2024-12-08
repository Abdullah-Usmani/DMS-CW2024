package com.example.demo.levels;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actors.*;
import com.example.demo.controller.Controller;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.ExplosionEffect;
import com.example.demo.menus.EndMenu;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;

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
	private final List<ActiveActorDestructible> friendlyProjectiles = new ArrayList<>();
	private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
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

	private void updateScene() {
		spawnEnemyUnits();                // Spawns enemies
		updateActors();                   // Updates all actor positions
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();         // Check for enemies penetrating defenses
		handleCollisions(enemyProjectiles, friendlyUnits);
		boolean userProjectileCollision = handleCollisions(friendlyProjectiles, enemyUnits); 		// Process all collisions in a single pass
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

	public Scene initializeScene() {
		initializeBackground();
		initializeKeyListeners();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		return scene;
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

	// New method to initialize level after construction
	public void initializeLevel() {
		List<ActorInfo> actorsInfo = getActorsInfo(); // Abstract method to get level-specific actor info
		int killsNeeded = getKillsNeeded();           // Abstract method for kills needed in the level

		// Show the level overlay
		showLevelOverlay(getLevelName(), actorsInfo, killsNeeded);
	}

	public void showLevelOverlay(String levelName, List<ActorInfo> actorsInfo, int killsNeeded) {
		// Create the overlay container
		StackPane overlay = new StackPane();
		overlay.setStyle("-fx-background-color: black;"); // Black background
		overlay.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight());

		// Main overlay label for the level info
		Label overlayText = new Label(
				"Level: " + levelName + "\n" +
						"Kills Needed: " + killsNeeded
		);
		overlayText.setStyle("-fx-font-family: 'Arial'; " +
				"-fx-font-size: 36px; " +
				"-fx-text-fill: #00ffff; " + // Neon blue color
				"-fx-effect: dropshadow(gaussian, #00ffff, 15, 0.5, 0, 0);");

		// Create separate lists for each category
		List<ActorInfo> userPlanes = new ArrayList<>();
		List<ActorInfo> userProjectiles = new ArrayList<>();
		List<ActorInfo> enemyPlanes = new ArrayList<>();
		List<ActorInfo> enemyProjectiles = new ArrayList<>();

		// Categorize actors
		for (ActorInfo info : actorsInfo) {
			if (info.isPlane) {
				if (info.isFriendly) {
					userPlanes.add(info);
				} else {
					enemyPlanes.add(info);
				}
			} else {
				if (info.isFriendly) {
					userProjectiles.add(info);
				} else {
					enemyProjectiles.add(info);
				}
			}
		}
		// Create boxes for each category
		VBox userPlanesBox = createCategoryBox("User Planes", userPlanes);
		VBox userProjectilesBox = createCategoryBox("User Projectiles", userProjectiles);
		VBox enemyPlanesBox = createCategoryBox("Enemy Planes", enemyPlanes);
		VBox enemyProjectilesBox = createCategoryBox("Enemy Projectiles", enemyProjectiles);

		// Layout the categories in an HBox
		HBox categoriesBox = new HBox(20, userPlanesBox, userProjectilesBox, enemyPlanesBox, enemyProjectilesBox);
		categoriesBox.setAlignment(Pos.TOP_CENTER);

		// Combine everything into the overlay
		VBox overlayContent = new VBox(20, overlayText, categoriesBox); // Spacing of 20
		overlayContent.setAlignment(Pos.CENTER);

		overlay.getChildren().add(overlayContent);
		root.getChildren().add(overlay);

		// Fade-out transition for the overlay
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), overlay);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);

		// Remove overlay and start the game after fade-out
		fadeOut.setOnFinished(e -> {
			root.getChildren().remove(overlay);
			startGame(); // Start the game
		});

		// Pause before fade-out
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(e -> fadeOut.play());

		delay.play(); // Begin the delay
	}


	// Create a function to generate a VBox for each category
	public VBox createCategoryBox(String title, List<ActorInfo> actors) {
		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white;");
		VBox categoryBox = new VBox(10, titleLabel); // Spacing of 10
		categoryBox.setAlignment(Pos.TOP_CENTER);

		for (ActorInfo info : actors) {
			HBox actorRow = new HBox(10);
			actorRow.setAlignment(Pos.CENTER_LEFT);

			ImageView actorImage = new ImageView(getClass().getResource(info.imagePath).toExternalForm());
			actorImage.setFitWidth(50);
			actorImage.setFitHeight(50);

			Label actorName = new Label(info.name);
			actorName.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");

			String typeText = info.isPlane ? "Health: " : "Damage: ";
			Label actorStat = new Label(typeText + info.statValue);
			actorStat.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");

			actorRow.getChildren().addAll(actorImage, actorName, actorStat);
			categoryBox.getChildren().add(actorRow);
		}
		return categoryBox;
	}

	protected abstract String getLevelName();
	protected abstract int getKillsNeeded();
	protected abstract List<ActorInfo> getActorsInfo();

	public void startGame() {
		background.requestFocus(); // Sets focus on the game background
		timeline.play();          // Starts the game timeline
	}

	public void goToNextLevel(String levelName) {
		timeline.stop();
		setChanged();
		notifyObservers(levelName);
	}

	private void fireProjectile() {
		Projectile userprojectile = (Projectile) user.fireProjectile();
		root.getChildren().add(userprojectile);
		friendlyProjectiles.add(userprojectile);
	}
	private void fireMissile() {
		Projectile missile = (Projectile) user.fireMissile();
		root.getChildren().add(missile);
		friendlyProjectiles.add(missile);
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

					// Specific logic for enemy planes
					if (target instanceof EnemyPlane ||
							target instanceof EnemyPlane2 ||
							target instanceof EnemyPlane3 ||
							target instanceof BossPlane) {

						if (target.isDestroyed()) { // Check if the enemy unit is destroyed
							updateKillCount(true); // Increment the kill count
						}
					}
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
			case "userplane.png":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						75, 75, 1.5,
						"/com/example/demo/audio/fortnite-pump.mp3"
				);
				break;
			case "usersidewinder.png":
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						100, 100, 1.5,
						"/com/example/demo/audio/roblox-explosion.mp3"
				);
				break;
			default:
				explosionEffect = new ExplosionEffect(
						"/com/example/demo/images/explosion1.png",
						50, 50, 1.0,
						"/com/example/demo/audio/tf2-crit.mp3"
				);
		}

		// Trigger the effect
		explosionEffect.createEffect(x, y, root);
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		friendlyProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
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

	private void updateHitCount(boolean collisionDetected) {
		if (collisionDetected) {
			user.incrementHitCount(); // Update hit count if a collision occurred
//			System.out.println("User hit-count: " + user.getNumberOfHits());
		}
	}

	private void updateKillCount(boolean killDetected) {
		if (killDetected) {
			user.incrementKillCount(); // Update hit count if a collision occurred
			System.out.println("User kill-count: " + user.getNumberOfKills());
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
		levelView.updateKills(user.getNumberOfKills());
	}

	// Static method to set the Controller
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		EndMenu endMenu = new EndMenu(
				screenWidth,
				screenHeight,
				() -> controller.goToMainMenu(),       // Exit to main menu logic
				() -> controller.restartCurrentLevel() // Restart level logic
		);
		root.getChildren().add(endMenu);
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		EndMenu endMenu = new EndMenu(
				screenWidth,
				screenHeight,
				() -> controller.goToMainMenu(),       // Exit to main menu logic
				() -> controller.restartCurrentLevel() // Restart level logic
		);
		root.getChildren().add(endMenu);
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

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}