package com.example.demo;

/**
 * Configuration class for the game "F-15: Strike Eagle."
 *
 * This class contains constants and methods for managing game settings, paths,
 * and other values required for the game's functionality. It includes screen resolution,
 * file paths, spawn probabilities, and other gameplay-specific attributes.
 *
 * This is a utility class and cannot be instantiated.
 */
public class Config {

    /**
     * Private constructor to prevent instantiation of the Config class.
     */
    private Config() {
        throw new IllegalStateException("Config class");
    }

    // General Game Settings

    /**
     * The title of the game and the delay between game loop iterations in milliseconds.
     */
    public static final String GAME_TITLE = "F-15: Strike Eagle";
    public static final int MILLISECOND_DELAY = 30;

    // Screen Resolution and Adjustments

    /**
     * Screen resolution settings and adjustments for UI elements.
     */
    private static int screenWidth = 1280; // Default Screen Width
    private static int screenHeight = 720;  // Default Screen Height
    private static boolean isFirstRun = true; // Flag to track first run
    public static double SCREEN_HEIGHT_ADJUSTMENT = screenHeight * .15;

    // File Paths

    /**
     * Paths for levels, images, audio, and fonts.
     */
    public static final String LEVEL_PATH = "com.example.demo.levels.";
    public static final String IMAGE_PATH = "/com/example/demo/images/";
    public static final String AUDIO_PATH = "/com/example/demo/audio/";
    public static final String FONT_PATH = "/com/example/demo/fonts/";

    // Font Files

    /**
     * Paths for regular and bold font files.
     */
    public static final String FONT_REGULAR = FONT_PATH + "HornetDisplay-Regular.ttf";
    public static final String FONT_BOLD = FONT_PATH + "HornetDisplay-Bold.ttf";

    // Level Files

    /**
     * Class names for levels.
     */
    public static final String LEVEL_ONE_CLASS_NAME = LEVEL_PATH + "LevelOne";
    public static final String LEVEL_TWO_CLASS_NAME = LEVEL_PATH + "LevelTwo";
    public static final String LEVEL_THREE_CLASS_NAME = LEVEL_PATH + "LevelThree";
    public static final String LEVEL_BOSS_CLASS_NAME = LEVEL_PATH + "LevelBoss";


    // Image Resources

    /**
     * Paths for images of user plane, enemy planes, boss, and effects
     */
    public static final String USER_IMAGE = IMAGE_PATH + "userplane.png";
    public static final String ENEMY1_IMAGE = IMAGE_PATH + "enemyplane.png";
    public static final String ENEMY2_IMAGE = IMAGE_PATH + "enemymig-29.png";
    public static final String ENEMY3_IMAGE = IMAGE_PATH + "enemya-10c.png";
    public static final String BOSS_IMAGE = IMAGE_PATH + "enemyc-17.png";
    public static final String EXPLOSION_IMAGE = IMAGE_PATH + "explosion.png";
    public static final String SPARK_IMAGE = IMAGE_PATH + "sparks.png";

    public static final String FRIENDLY_GUN = IMAGE_PATH + "userfire.png";
    public static final String FRIENDLY_MISSILE = IMAGE_PATH + "usersidewinder.png";
    public static final String ENEMY_GUN = IMAGE_PATH + "enemyfire.png";
    public static final String ENEMY_MISSILE = IMAGE_PATH + "enemymissiler-33.png";
    public static final String BOSS_MISSILE = IMAGE_PATH + "bossfire.png";

    public static final String HEART_IMAGE = IMAGE_PATH + "heart.png";
    public static final String SHIELD_IMAGE = IMAGE_PATH + "shieldbubble.png";
    public static final String KILL_IMAGE = IMAGE_PATH + "killcount.png";

    /**
     * Images displayed at the end of the game.
     */
    public static final String WIN_IMAGE = IMAGE_PATH + "you-win-AI.png";
    public static final String LOSE_IMAGE = IMAGE_PATH + "game-over-AI.png";

    /**
     * Background images for various game states and levels.
     */
    public static final String START_BACKGROUND = IMAGE_PATH + "backgroundstart2.jpg";
    public static final String LEVEL1_BACKGROUND = IMAGE_PATH + "background2.jpg";
    public static final String LEVEL2_BACKGROUND = IMAGE_PATH + "background6.jpg";
    public static final String LEVEL3_BACKGROUND = IMAGE_PATH + "background9.jpg";
    public static final String LEVELBOSS_BACKGROUND = IMAGE_PATH + "background10.jpg";

    // Audio Resources

    /**
     * Audio for collisions.
     */
    public static final String FRIENDLY_TAKE_DAMAGE_AUDIO = AUDIO_PATH + "userhit.mp3";
    public static final String ENEMY_TAKE_DAMAGE_AUDIO = AUDIO_PATH + "spark.mp3";
    public static final String PLANE_COLLISION_AUDIO = AUDIO_PATH + "collision.mp3";
    public static final String DESTRUCTION_AUDIO = AUDIO_PATH + "roblox-explosion.mp3";

    /**
     * Audio for projectiles.
     */
    public static final String FRIENDLY_GUN_AUDIO = AUDIO_PATH + "single-shot.mp3";
    public static final String FRIENDLY_MISSILE_AUDIO = AUDIO_PATH + "fortnite-rpg-shoot.mp3";
    public static final String ENEMY_GUN_AUDIO = AUDIO_PATH + "enemyfire.mp3";
    public static final String ENEMY_MISSILE_AUDIO = AUDIO_PATH + "enemymissile.mp3";

    /**
     * Audio clips for background, game transitions and outcomes.
     */
    public static final String BACKGROUND_AUDIO = AUDIO_PATH + "backgroundOST.mp3";
    public static final String START_AUDIO = AUDIO_PATH + "start-audio.mp3";
    public static final String TRANSITION_AUDIO = AUDIO_PATH + "nextlevel.mp3";
    public static final String WIN_AUDIO = AUDIO_PATH + "tf2-win.mp3";
    public static final String LOSE_AUDIO = AUDIO_PATH + "tf2-lose.mp3";

    // Gameplay Constants

    /**
     * Level-based constants - Total Enemies
     */
    public static final int LEVEL_ONE_TOTAL_ENEMIES = 5;
    public static final int LEVEL_TWO_TOTAL_ENEMIES = 10;
    public static final int LEVEL_THREE_TOTAL_ENEMIES = 10;

    /**
     * Level-based constants - Kills to Advance
     */
    public static final int LEVEL_ONE_KILLS_TO_ADVANCE = 10;
    public static final int LEVEL_TWO_KILLS_TO_ADVANCE = 15;
    public static final int LEVEL_THREE_KILLS_TO_ADVANCE = 20;

    /**
     * Level-based constants - Initial Player Health
     */
    public static final int LEVEL_ONE_INITIAL_HEALTH = 5;
    public static final int LEVEL_TWO_INITIAL_HEALTH = 10;
    public static final int LEVEL_THREE_INITIAL_HEALTH = 15;
    public static final int LEVEL_BOSS_INITIAL_HEALTH = 5;

    /**
     * Level-based constants - Enemy Spawn Probabilities
     */
    public static final double ENEMY1_SPAWN_PROBABILITY = .20;
    public static final double ENEMY2_SPAWN_PROBABILITY = .10;
    public static final double ENEMY3_SPAWN_PROBABILITY = .05;

    /**
     * User plane constants
     */
    public static final double USER_SCALAR = .05;
    public static final double USER_INITIAL_X_POSITION_SCALAR = .01;
    public static final double USER_INITIAL_Y_POSITION_SCALAR = .5;
    public static double USER_VERTICAL_VELOCITY = screenHeight * .015;
    public static final long USER_GUN_COOLDOWN = 150; // Cooldown in milliseconds
    public static final long USER_MISSILE_COOLDOWN = 1000; // Cooldown in milliseconds

    /**
     * Boss plane constants
     */
    public static int BOSS_IMAGE_HEIGHT =  (int) (screenHeight * .2);
    public static int BOSS_IMAGE_WIDTH =  (int) (screenWidth * .2);   
    public static double BOSS_INITIAL_X_POSITION = screenWidth - (screenWidth * .3);
    public static double BOSS_INITIAL_Y_POSITION = screenHeight * .5;
    public static int BOSS_Y_POSITION_UPPER_BOUND =  (int) -(screenHeight * .01);
    public static int BOSS_Y_POSITION_LOWER_BOUND = screenHeight - (2*BOSS_IMAGE_HEIGHT) - BOSS_Y_POSITION_UPPER_BOUND;
    public static int BOSS_VERTICAL_VELOCITY = (int) (screenHeight * .0075);
    public static final double BOSS_FIRE_RATE = .04;
    public static final double BOSS_SHIELD_PROBABILITY = .05;
    public static final int BOSS_HEALTH = 15;
    public static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    public static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    public static final int MAX_FRAMES_WITH_SHIELD = 50;

    /**
     * Enemy1 plane constants
     */
    public static final double ENEMY1_SCALAR = .05;
    public static int ENEMY1_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
    public static final int ENEMY1_INITIAL_HEALTH = 1;
    public static final double ENEMY1_FIRE_RATE = .01;

    /**
     * Enemy2 plane constants
     */
    public static final double ENEMY2_SCALAR = .07;
    public static int ENEMY2_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
    public static final int ENEMY2_INITIAL_HEALTH = 2;
    public static final double ENEMY2_FIRE_RATE = .02;

    /**
     * Enemy3 plane constants
     */
    public static final double ENEMY3_SCALAR = .08;
    public static int ENEMY3_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
    public static final int ENEMY3_INITIAL_HEALTH = 3;
    public static final double ENEMY3_FIRE_RATE = .02;

    /**
     * Enemy projectile constants
     */
    public static final double ENEMY_PROJECTILE_SCALAR = .02;
    public static double ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
    public static final int ENEMY_PROJECTILE_DAMAGE = 1;

    /**
     * Enemy missile constants
     */
    public static final double ENEMY_MISSILE_SCALAR = .03;
    public static double ENEMY_MISSILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
    public static final int ENEMY_MISSILE_DAMAGE = 3;

    /**
     * User projectile constants
     */
    public static final double USER_PROJECTILE_SCALAR = .01;
    public static double USER_PROJECTILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
    public static final int USER_PROJECTILE_DAMAGE = 1;

    /**
     * User missile constants
     */
    public static final double USER_MISSILE_SCALAR = .01;
    public static double USER_MISSILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
    public static final int USER_MISSILE_DAMAGE = 3;

    // Display Constants

    /**
     * HUD icon sizing
     */
    public static double HEART_SIZE = screenWidth*.04;
    public static double HEART_SPACING = screenWidth*.01;
    public static double KILL_SIZE = screenWidth*.04;
    public static double SHIELD_SIZE = screenWidth*.1;

    /**
     * HUD positioning
     */
    public static double HEART_X_POSITION = .5*HEART_SIZE;
    public static double HEART_Y_POSITION = (.5*HEART_SIZE);
    public static double KILL_X_POSITION = screenWidth - (4*KILL_SIZE);
    public static double KILL_Y_POSITION = (.5*KILL_SIZE);
    public static double BOSS_HEALTH_X_POSITION = screenWidth - (6*HEART_SIZE);
    public static double BOSS_HEALTH_Y_POSITION = (.5*HEART_SIZE);

    /**
     * Effect constants
     */
    public static double SPARK_SIZE = screenWidth*.04;
    public static double EXPLOSION_SIZE = screenWidth*.05;
    public static final int SPARK_DURATION = 1;
    public static final int EXPLOSION_DURATION = 2;

    // Methods

    /**
     * Retrieves the current screen height.
     *
     * @return the current screen height.
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Retrieves the current screen width.
     *
     * @return the current screen width.
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Updates the screen resolution and recalculates dependent values.
     *
     * @param width  the new screen width.
     * @param height the new screen height.
     */
    public static void setResolution(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        calculateResolutionDependentValues();
    }

    /**
     * Checks if it is the first run of the game.
     *
     * @return true if it is the first run, false otherwise.
     */
    public static boolean isFirstRun() {
        return isFirstRun;
    }
    /**
     * Sets the first run flag for the game.
     *
     * @param firstRun true to indicate the first run, false otherwise.
     */
    public static void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    /**
     * Calculates resolution-dependent values based on screen width and height.
     *
     * This method is called when the resolution is updated to adjust gameplay attributes.
     */
    public static void calculateResolutionDependentValues() {
        USER_VERTICAL_VELOCITY = screenHeight * .015;
        BOSS_IMAGE_HEIGHT =  (int) (screenHeight * .2);
        BOSS_IMAGE_WIDTH =  (int) (screenWidth * .2);
        BOSS_INITIAL_X_POSITION = screenWidth - (screenWidth * .3);
        BOSS_INITIAL_Y_POSITION = screenHeight * .5;
        BOSS_Y_POSITION_UPPER_BOUND =  (int) -(screenHeight * .01);
        BOSS_Y_POSITION_LOWER_BOUND = screenHeight - (2*BOSS_IMAGE_HEIGHT) - BOSS_Y_POSITION_UPPER_BOUND;
        BOSS_VERTICAL_VELOCITY = (int) (screenHeight * .0075);
        ENEMY1_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
        ENEMY2_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
        ENEMY3_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);
        ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
        ENEMY_MISSILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
        USER_PROJECTILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
        USER_MISSILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
        HEART_SIZE = screenWidth*.04;
        HEART_SPACING = screenWidth*.01;
        KILL_SIZE = screenWidth*.04;
        SHIELD_SIZE = screenWidth*.04;
        SPARK_SIZE = screenWidth*.04;
        EXPLOSION_SIZE = screenWidth*.05;
        HEART_X_POSITION = .5*HEART_SIZE;
        KILL_X_POSITION = screenWidth - (4*KILL_SIZE);
        BOSS_HEALTH_X_POSITION = screenWidth - (6*HEART_SIZE);
        HEART_Y_POSITION = (.5*HEART_SIZE);
        KILL_Y_POSITION = (.5*KILL_SIZE);
        BOSS_HEALTH_Y_POSITION = (.5*HEART_SIZE);
    }
}
