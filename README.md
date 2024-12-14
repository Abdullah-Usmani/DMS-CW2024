# F-15: Strike Eagle (DMS-CW2024)
---

 **Abdullah Usmani** - 20615297  
  [Github Repository](https://github.com/Abdullah-Usmani/DMS-CW2024.git)

## About
This game draws heavy inspiration from the **DCS** and **Ace Combat** series, blending their iconic mechanics and art styles to deliver an engaging aerial combat experience. The art style is rooted in modern-day aircraft and air combat, showcasing the F-15 as the centerpiece. Known for its reliability and air-to-air superiority in real-life scenarios, the F-15 takes the spotlight as the ultimate fighter jet in this game.

---

## Compilation Instructions
To compile and run the project, follow these steps:

1. **Java (JDK 19 or above)** and **Maven** must be installed on the system.

   Verify Java and Maven installation by running the following commands in a terminal:

   ```bash
   java -version
   mvn -v
   ```

   If Java is not installed, download Java from [Oracle's JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

   If Maven is not installed, download Maven from [Apache Maven](https://maven.apache.org/download.cgi).

2. Navigate to the project's root directory using a terminal.

3. Compile and run the project using Maven:

   ```bash
   mvn clean javafx:run
   ```

   Use the provided `pom.xml` and `module-info.java` to handle dependencies.

   Alternatively, compile and run the project using IntelliJ IDEA or a compatible IDE.

---

## Features Implemented

### Game Mechanics
- `CollisionManager` basing collisions on actual collisions, scalable.
- Kill counter working based on actual destroyed enemies from collisions.
- Projectiles dealing variable damage.
- New projectiles: Sidewinders, R-33s, Hellfire missiles.
- Planes with variable health.
- New planes: MiG-29, A-10C, C-17.
- Adjusted levels: Two, Three, Boss.
- Power-ups (e.g., Sidewinder missiles).
- Scalable gameplay adjusting to screen size.

### UI Experience
- Start Menu.
- Settings Menu - Resolution Change.
- Help Menu - Control Description.
- Pause Menu - restart level, restart game, exit to main menu.
- End Game Menu - restart level, restart game, exit to main menu.
- Overlay Cutscenes - show dynamically updatable level actor details upon level instantiation.

### Audio/Visuals
#### Visuals:
- Projectiles.
- Planes.
- Display Icons.
- Backgrounds.
- Win/Lose Screens.
- Stylized Buttons, Drop-down Lists, Counters.

#### Audio+Visual Effects Upon:
- Different projectiles being shot.
- Taking damage.
- Destruction.
- Plane-to-plane collisions.
- Enemy penetration.

#### Audio:
- Background OST.
- Game start.
- Level transitions.
- Win/Lose audio.

---

## Refactoring

### Goals
- Reduce redundancies and follow design patterns (SOLID, SRP, Singleton).

### Improvements
- Package separation: `actors`, `controllers`, `displays`, `levels`, `managers`, `menus` (each contains a new `package-info.java` class).
- `module-info.java`: Included new requirements for `javafx.media`, `java.desktop`, `java.logging`, along with openings for JavaFX reflections and exports.
- `pom.xml`: Included a dependency for `javafx.media`.
- Renamed for better readability:
  - `userPlane`/`projectiles` → `friendlyPlane`/`Projectiles`(variables, methods).
  - `GameOver` → `Lose` (classes, methods, images).
  - `ShieldImage` → `ShieldDisplay`.
  - `LevelTwo.java` → `LevelBoss.java`.

## Modified & New Java Classes

### New Classes

- **Config**:
  - Centralized configuration for all constants used in the game, including fire rates, total enemies, spawn probabilities, velocities, image sizes, and paths for images, audio, fonts, and screen resolution settings.

- **PauseMenuController**:
  - Handles all methods related to the pause menu: `resumeGame()`, `pauseGame()`, `restartCurrentLevel()`, `restartGame()`, `goToMainMenu()`.

- **CollisionManager**:
  - Handles all collision-related logic and usage of effects on different collision types.
  - Individual collision type methods: `friendlyProjectiles` with `enemyUnits`, `enemyProjectiles` with `friendlyUnits`, and plane-to-plane collisions (`friendlyUnits` with `enemyUnits`).
  - Based on the `detectCollisions()` method, which returns a boolean if a target `ActiveActorDestructible` collides with another.
  - Increments kill count in `LevelParent.java` if a friendly projectile collision results in target destruction.
  - Also handles enemy penetration
  - Collectivized into one main function, .`handleAllCollisions()`.

- **EffectManager**:
  - Manages visual effects for collisions and events using different methods.

- **AudioManager**:
  - Manages methods for background audio, sound effects, and transition sounds.

- **StylingManager**:
  - Manages all styling options for menu items: labels, buttons, drop-down lists.

- **StartMenu**:
  - Initializes and displays the main menu with buttons to start the game, adjust settings, view the help menu, or exit the game.

- **SettingsMenu**:
  - User interface for modifying game settings, specifically setting the game’s resolution.

- **HelpMenu**:
  - Menu with a label showing the game’s controls.

- **PauseMenu**:
  - Displays a pop-up pane with buttons for pause-related actions. Methods are connected to `PauseMenuController`.

- **EndMenu**:
  - Displays a pop-up pane upon the game ending with buttons for end-game actions. Methods are connected to `Controller`.

- **ActorInfo**:
  - Placeholder class to hold plane and projectile details for use in the level start overlay.

- **StartOverlay**:
  - Class that shows the level introduction, displaying details about the actors, required kills, and other relevant information.

- **BossHeartDisplay**:
  - Icon container that shows the current boss health using the `updateHealth()` method.

- **GameLoop**:
  - Manages the timeline and game loop for improved frame handling.

- **PowerUp**:
  - Implements power-up logic and effects.

- **LevelTwo.java, LevelThree**:
  - New levels of the game that contain 2 and 3 types of enemies respectively.

- **EnemyPlane2.java, EnemyPlane3**:
  - New planes having 2 and 3 health, and being replicas of the MiG-29 and A-10C aircraft respectively.

- **UserMissile.java, EnemyProjectile2**:
  - New projectiles having 3 damage each and being replicas of the Sidewinder and R-33 missiles respectively.

### Changed Classes

All classes were updated to include resolution-based scaling logic.
Most, if not, all hard-coded constants within classes were either removed or moved to Config.java.

- **Controller**:
  - `goToLevel()` now includes logic to handle key presses for pausing, initializes levels with cutscenes instead of calling the `startGame()` method .
  - `restartGame()` and `restartCurrentLevel()` methods use `goToLevel()` to level 1 and the currently played level respectively, whereas `goToMainMenu()` method changes the scene to the `StartMenu`.

- **Main.java**: 
  - `start()` takes the user first to the `StartMenu` instead of the game directly.
  - `RestartGame()` method for use upon applying new resolution in the settings menu to refresh all changes.

- **LevelParent**:
  - Added `setController()` method to be used in `Controller.java`.
  - `initializeScene()` and `updateLevelView()` methods now add `KillDisplay` to the screen.
  - All timeline related logic delegated to `GameLoop.java`.
  - All collision and penetration logic delegated to `CollisionManager.java`.
  - `updateLevelView()` method now updates both heart removal and kill counter updates.
  - `updateScene()` method shortened up.
  - Renamed `RemoveAllDestroyedActors()` to `RemoveActors()`.
  - `stopGame()` and `endGame()` methods to clear actors from memory and display delayed win/lose images with effects respectively.
  - `resetGameState()` method upon level initializing to reset isGameOver boolean in order to prevent repeated calls of end/stopGame methods.
  - Plays a transition delay when the level is over according to the `checkIfGameOver()` logic in the subclass levels.
  - Added `resetGameState()` to prevent repeated calls to `endGame()` and `stopGame()`.

- **LevelOne.java`, `LevelTwo.java`, `LevelBoss**:
  - Added methods for getting names, required kills, and actor details.
  - `LevelBoss` only spawns the boss the plane is not destroyed, along with adding the specialized `bossHeartDisplay` and `shieldDisplay` containers on screen.

- **LevelView**
  - Added `KillDisplay` constructor and show method along with `addKills()` method to update kill counter on screen.
  - Added `showWinImage()` and `showLoseImage()` methods.

- **ShieldDisplay**: 
  - Renamed from `ShieldImage` to `ShieldDisplay`.
  - Now extends from `ActiveActor` instead of `ImageView`, in order to be dynamically moved around along with the Boss Plane, thus having an `updatePosition()` method now.

- **WinImage.java, LoseImage**
  - Made images cover the whole screen, got rid of `setLayoutX()` and `setLayoutY()` in the constructor.
  - Moved `showWinImage()` and `showLoseImage()` methods to `LevelView.java`.

- **ActiveActor**: 
  - Takes in new `int imageWidth` parameter for the sake of setting actor images properly when rescaled due to changed resolution, as `imageHeight` already exists as a parameter.

- **ActiveActorDestructible**: 
  - `IsOutOfBounds()` is a new boolean method that returns `true` if the actor goes beyond the screenWidth.
  - `updatePosition()` method here now uses the method above to destroy the actor if it goes out of bounds, and as such is called by all of its children.
  - `takeDamage()` method adjusted to take an int damage parameter, allowing for variable damage to be taken.
  - `shouldTakeDamage()` returns if the actor should take damage (used in `BossPlaneTest.java`).
  - `getHealth()`, `getDamage()`, `getImageName()` are new methods that return the health, damage, and image name respectively.

- **FighterPlane**: 
  - Removed `takeDamage()` method.
  - `playFiringAudio()` method that uses the `playAudio()` method from the `AudioManager`.
  - `getProjectileXOffset` that returns a `double` which is the `imageWidth` times a positive or negative value if the instance is a `UserPlane` or not, and a negative half value if the instance is a `BossPlane`.
  - Thus also got rid of the extra `xPositionOffset` parameter for `getProjectileXPosition()` method, and replaced the `yPositionOffset` parameter in the `getProjectileYPosition()` offset with directly getting the value of the `imageHeight / 2`.

- **UserPlane**: 
  - `fireProjectile()` and `fireMissile()` methods allow the creation of the respective projectiles if the time between the last time fired is greater than or equal to the fire rates of each projectile.
  - Added `getNumberOfHits()` and `incrementHitCount()` methods for display on future stats page feature.

- **BossPlane**: 
  - Renamed from `Boss.java` to `BossPlane.java`.
  - Enhanced boss mechanics with dynamic shields by adding `updateShieldPosition()`, `updateShield()`, and `shouldTakeDamage()` methods.
  - Boss fires different missiles.

- **Projectile.java**: 
Added `getDamage()` and `getImageName()` methods.

- **HeartDisplay, EnemyPlane, UserProjectile, EnemyProjectile, BossProjectile**: 
  - No changes.

## Features Implemented But Not Working
- **Power-ups**:
  - The `PowerUp.java` class was created, but no subclasses of it exist yet, nor their implementations, on the basis of lack of time.

---

## Features Not Implemented

All of these below were not implemented purely on the basis of not having enough time.

### Features:
- Firefight/Endless game mode.
- Custom game mode: adjust enemies, spawn probabilities, damages.
- Stat tracking at game end: Time survived, total kills, total hits, projectiles dodged.
- Day/night cycles.
- Updated cooldown visualizations and game overlays.
- Level Transition cutscene.
- Changeable keybinds.
- About menu.

### Refactoring
- Custom `Observer` patterns to increase compatibility instead of using old deprecated observer
- Merging `ActiveActor.java` and `ActiveActorDestructible.java` into one `ActiveActor.java` class
- Reducing redundant classes through more managers, such as:
  - `ProjectileManager.java` class
  - `PlaneManager.java` class
  - `MenuManager.java` class
  - `HUDManager.java` class
  - `GameEndScreenManager.java` class
- Putting `Plane`/`Projectile` name constants in `Config.java` instead of using strings when defining `ActorInfo` for `StartOverlay` in each level

---

## Unexpected Problems

- **Desync issues**:
  - Game state ends quickly before collision/animation occurs on screen.
    - Addressed by introducing delay buffers.
  - Game state sometimes spawns a user-fired projectile during the overlay cutscene if the fire button is pressed, but the projectile stays in position until the game starts
  - In a similar issue with the overlay cutscene, the pause menu can be opened but the game does not pause
- **Resolution changes**:
  - Had a customizable resolution feature, which included a settings menu for users to adjust the resolution on the fly, but elements would not resize correctly post-change.
    - Addressed by locking resolution change to the start menu, which is to be only run on the first launch before the game is started, thus rolling back the dynamically updatable resolution feature.
- **ProjectileFactory**: 
  - Due to lack of time, I had to roll back the class and could not therefore refactor all the `fireProjectile()` methods.
- **Collision handling bugs**:
  - Addressed by introducing `CollisionManager.java` class and revamping the entire collision detection functionality.
- **Boss mechanics**:
  - Early respawn bugs resolved by adding destruction checks.
  - Boss projectiles sometimes stay on screen but do not cause any damage, could be a memory leak issue


---

## Change Log
[Google Doc](https://docs.google.com/document/d/1h2jttGEQPfeJLj6ohuvfWqr4ALNNde4Zin-bcGCXwMk/)

---

## Documentation
- Generated a high-level class diagram using Visual Paradigm.
- Completed JavaDocs for all classes.
- Added JUnit tests for core classes.
