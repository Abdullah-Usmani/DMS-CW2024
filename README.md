# F-15: Strike Eagle (DMS-CW2024)
  Abdullah Usmani - 20615297  
  [Repository](https://github.com/Abdullah-Usmani/DMS-CW2024.git)

---
## Compilation Instructions:
- Ensure you have Maven and JavaFX configured on your system.
- Use the provided `pom.xml` to handle dependencies.
- Compile and run the project using IntelliJ IDEA or a compatible IDE.

---
## Features Implemented:

### Functionality
- Scalable gameplay adjusting to screen size.
- Updated visuals:
  - Enhanced projectiles, planes, and backgrounds.
  - Added day/night cycles.
  - Updated cooldown visualizations and game overlays.
- Added new levels and boss mechanics.
- Improved pause and exit game menus.
- Stat tracking at game end:
  - Time survived, total kills, projectiles dodged.
- Enhanced sound effects:
  - Shooting, taking damage, collisions, and level transitions.
- New features:
  - Power-ups (e.g., Sidewinder missiles).
  - Dynamic boss shields.

### Refactoring
- Introduced modular managers for better code organization:
  - `EffectManager`, `SoundManager`, `CollisionManager`, and `StyleManager`.
- Simplified logic across classes adhering to SOLID principles.

---
## Features Not Implemented:
- Endless mode.
- Advanced gamemodes (e.g., firefight, multiplayer).
- Projectile factory and image factory.

---
## New Java Classes:
- **Config.java**: Centralized configuration for images, audio, and screen settings.
- **CollisionManager.java**: Handles all collision-related logic and effects.
- **EffectManager.java**: Manages visual and audio effects for collisions and events.
- **AudioManager.java**: Controls background music and sound effects.
- **GameLoop.java**: Manages the game loop for improved frame handling.
- **PowerUp.java**: Implements power-up logic and effects.
- **ShieldDisplay.java**: Manages shield visuals and their interactions.
- **PauseMenuController.java**: Handles user interactions in the pause menu.

---
## Removed Java Classes:
- **ProjectileFactory.java**: Replaced with streamlined projectile logic.
- **ExplosionEffect.java**: Consolidated into `EffectManager`.
- **BaseMenuController.java**: Merged into specific menu controllers.

---
## Modified Java Classes:
- **ActiveActor.java**: Updated to include scaling logic and unified actor interactions.
- **BossPlane.java**: Enhanced boss mechanics with dynamic shields and projectiles.
- **LevelParent.java**: Refactored to delegate collision logic to `CollisionManager`.
- **Controller.java**: Simplified pause, restart, and game state handling.
- **LevelOne.java**, **LevelTwo.java**, **LevelBoss.java**: Adjusted transitions and mechanics.
- **FighterPlane.java**: Added support for new power-ups.
- **UserPlane.java**: Improved health and projectile management.
- **ActorInfo.java**: Updated to include new visual overlays.

---
## Unexpected Problems:
- **Desync issues**:
  - Game state continuing after exiting to menu.
  - Addressed by introducing delay buffers.
- **Resolution changes**:
  - Elements not resizing correctly post-change.
  - Locked resolution change to the start menu.
- **Collision handling bugs**:
  - Addressed by introducing `CollisionManager`.
- **Boss mechanics**:
  - Early respawn bugs resolved by adding destruction checks.

---
## Development Log Summary:

### Bug Fixes
- Fixed pause menu desyncs and incorrect restarts.
- Resolved projectile collision inconsistencies.
- Ensured proper level transitions and boss mechanics.
- Adjusted visual elements to fit various resolutions.

### Styling
- Enhanced UI components:
  - Smoother transitions, updated fonts, and improved backgrounds.
  - Added icons for kills and shields.

### Documentation
- Completed JavaDocs for all classes.
- Added JUnit tests for core features.
- Generated class diagrams using Visual Paradigm.

---
## Outstanding Issues:
- Rare collision miscounts.
- Boss projectiles need further balancing.

---
## Documentation & Resources:
- **JavaDocs**: Comprehensive documentation generated using IntelliJ and GitHub Copilot.
- **Class Diagram**: Created with Visual Paradigm.
- **Unit Tests**: Developed using JUnit.
- **Video**: Overview of the gameplay and features.

---
This README outlines the development process, challenges, and accomplishments during the coursework for "F-15: Strike Eagle." The game combines enhanced visuals, dynamic gameplay, and robust software engineering practices.

