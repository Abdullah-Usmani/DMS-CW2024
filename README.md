# F-15: Strike Eagle (DMS-CW2024)
  Abdullah Usmani - 20615297  
  [Repository](https://github.com/Abdullah-Usmani/DMS-CW2024.git)

---
## Compilation Instructions:
- Ensure you have Maven and JavaFX configured on your system.
- Use the provided `pom.xml` to handle dependencies.
- Compile and run the project using IntelliJ IDEA or a compatible IDE.

---

## Features Working

### Game Mechanics:
- Scalable gameplay adjusting to screen size.
- Collision Manager based on actual collisions, scalable.
- Kill counter working based on actual destroyed enemies from collisions.
- Projectiles dealing variable damage.
- New Projectiles: Missiles.
- New Planes: MiG-29, A-10C, C-17.
- New Levels: Two, Three, Boss.
- **New features:**
  - Power-ups (e.g., Sidewinder missiles).
  - Dynamic boss shields.

### Start Menu:
- Settings.
- Resolution Change.

### Additional Features:
- Pause and Exit game menus.
- Level Overlay Cutscenes.
- **Updated visuals:**
  - Enhanced projectiles, planes, and backgrounds.
- **Enhanced sound effects:**
  - Shooting, taking damage, collisions, and level transitions.
- Audio Manager.
- Collision Manager.
- Effect Manager.

---

## Features Not Working:
- Power-ups.

---

## Features Not Implemented:
- Endless game mode.
- Custom game mode (adjust enemies, spawn probabilities, damages).
- Stat tracking at game end:
  - Time survived, total kills, total hits, projectiles dodged.
- Day/night cycles.
- Updated cooldown visualizations and game overlays.
- About page.
- Level Transition cutscene.
- Level Transition with better audio.
- Changeable keybinds.

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
- **Destructible.java**.
- **LevelViewLevelTwo.java**.
- **ProjectileFactory.java**: Replaced with streamlined projectile logic.
- **ExplosionEffect.java**: Consolidated into `EffectManager`.
- **BaseMenuController.java**: Merged into specific menu controllers.

---

## Changed Java Classes:
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

### Desync Issues:
- Game state continues after exiting the menu.
- **Fix:** Introduced delay buffers.

### Resolution Changes:
- Elements not resizing correctly post-change.
- **Fix:** Locked resolution change to the start menu.

### Collision Handling Bugs:
- **Fix:** Addressed by introducing `CollisionManager`.

### Boss Mechanics:
- Early respawn bugs resolved by adding destruction checks.
- Boss projectiles sometimes stay on screen.

---

## Change Log:
- [Detailed Change Log](https://docs.google.com/document/d/1h2jttGEQPfeJLj6ohuvfWqr4ALNNde4Zin-bcGCXwMk/)

---

## Resources:
- **Audio:**
  - Fortnite: RPG, Pump.
  - TF2: Win, Lose, Crit.
  - CS: Source: Let’s go.
  - Jet: Sample.
  - Background OST: YouTube.

---

## Bug Fixes:
- Fixed pause menu desyncs and incorrect restarts.
- Resolved projectile collision inconsistencies.
- Ensured proper level transitions and boss mechanics.
- Adjusted visual elements to fit various resolutions.

---

## Styling:
- Enhanced UI components:
  - Smoother transitions, updated fonts, and improved backgrounds.
  - Added icons for kills and shields.

---

## Documentation:
- Completed JavaDocs for all classes.
- Added JUnit tests for core features.
- Generated class diagrams using Visual Paradigm.


---
This README outlines the development process, challenges, and accomplishments during the coursework for "F-15: Strike Eagle." The game combines enhanced visuals, dynamic gameplay, and robust software engineering practices.

