package com.example.demo;

public class Config {
    public static final int MILLISECOND_DELAY = 30;

    private static int screenWidth = 1280; // Default Screen Width
    private static int screenHeight = 720;  // Default Screen Height
    private static boolean isFirstRun = true; // Flag to track first run
    public static double SCREEN_HEIGHT_ADJUSTMENT = screenHeight * .1;

    public static final String LevelsPath = "com.example.demo.levels.";
    public static final String ImagePath = "/com/example/demo/images/";
    public static final String AudioPath = "/com/example/demo/audio/";
    public static final String FontPath = "/com/example/demo/fonts/";

    public static final String EXPLOSION_IMAGE = ImagePath + "explosion.png";
    public static final String SPARK_IMAGE = ImagePath + "sparks.png";

    public static final String USER_IMAGE = ImagePath + "userplane.png";
    public static final String ENEMY1_IMAGE = ImagePath + "enemyplane.png";
    public static final String ENEMY2_IMAGE = ImagePath + "enemymig-29.png";
    public static final String ENEMY3_IMAGE = ImagePath + "enemya-10c.png";
    public static final String BOSS_IMAGE = ImagePath + "enemyc-17.png";

    public static final String FRIENDLY_GUN = ImagePath + "userfire.png";
    public static final String FRIENDLY_MISSILE = ImagePath + "usersidewinder.png";
    public static final String ENEMY_GUN = ImagePath + "enemyfire.png";
    public static final String ENEMY_MISSILE = ImagePath + "enemymissiler-33.png";
    public static final String BOSS_MISSILE = ImagePath + "bossfire.png";

    public static final String HEART_IMAGE = ImagePath + "heart.png";
    public static final String SHIELD_IMAGE = ImagePath + "shield.png";
    public static final String KILL_IMAGE = ImagePath + "killcount.png";

    public static final String LEVEL1_BACKGROUND = ImagePath + "background9.jpg";
    public static final String LEVEL2_BACKGROUND = ImagePath + "background4.jpg";
    public static final String LEVEL3_BACKGROUND = ImagePath + "background1.jpg";
    public static final String LEVELBOSS_BACKGROUND = ImagePath + "background10.jpg";

    public static final String LEVEL_ONE_CLASS_NAME = LevelsPath + "LevelOne";
    public static final String LEVEL_TWO_CLASS_NAME = LevelsPath + "LevelTwo";
    public static final String LEVEL_THREE_CLASS_NAME = LevelsPath + "LevelThree";
    public static final String LEVEL_BOSS_CLASS_NAME = LevelsPath + "LevelBoss";

    public static final String START_IMAGE = ImagePath + "background10.jpg";
    public static final String WIN_IMAGE = ImagePath + "you-win.jpg";
    public static final String LOSE_IMAGE = ImagePath + "game-over.jpg";

    public static final String BACKGROUND_AUDIO = AudioPath + "backgroundOST.mp3";

    public static final String FRIENDLY_TAKE_DAMAGE_AUDIO = AudioPath + "userhit.mp3";
    public static final String ENEMY_TAKE_DAMAGE_AUDIO = AudioPath + "spark.mp3";
    public static final String PLANE_COLLISION_AUDIO = AudioPath + "collision.mp3";
    public static final String DESTRUCTION_AUDIO = AudioPath + "roblox-explosion.mp3";

    public static final String FRIENDLY_GUN_AUDIO = AudioPath + "single-shot.mp3";
    public static final String FRIENDLY_MISSILE_AUDIO = AudioPath + "fortnite-rpg-shoot.mp3";
    public static final String ENEMY_GUN_AUDIO = AudioPath + "enemyfire.mp3";
    public static final String ENEMY_MISSILE_AUDIO = AudioPath + "enemymissile.mp3";

    public static final String WIN_AUDIO = AudioPath + "tf2-win.mp3";
    public static final String LOSE_AUDIO = AudioPath + "tf2-lose.mp3";

    public static final int LEVEL_ONE_TOTAL_ENEMIES = 5;
    public static final int LEVEL_TWO_TOTAL_ENEMIES = 10;
    public static final int LEVEL_THREE_TOTAL_ENEMIES = 10;

    public static final int LEVEL_ONE_KILLS_TO_ADVANCE = 10;
    public static final int LEVEL_TWO_KILLS_TO_ADVANCE = 15;
    public static final int LEVEL_THREE_KILLS_TO_ADVANCE = 20;

    public static final int LEVEL_ONE_INITIAL_HEALTH = 5;
    public static final int LEVEL_TWO_INITIAL_HEALTH = 10;
    public static final int LEVEL_THREE_INITIAL_HEALTH = 15;
    public static final int LEVEL_BOSS_INITIAL_HEALTH = 5;

    public static final double LEVEL_ONE_ENEMY_SPAWN_PROBABILITY = .20;
    public static final double LEVEL_TWO_ENEMY_SPAWN_PROBABILITY = .20;
    public static final double LEVEL_THREE_ENEMY_SPAWN_PROBABILITY = .20;

    public static final double LEVEL_TWO_ENEMY2_SPAWN_PROBABILITY = .10;
    public static final double LEVEL_THREE_ENEMY2_SPAWN_PROBABILITY = .10;

    public static final double LEVEL_THREE_ENEMY3_SPAWN_PROBABILITY = .05;

// initialize all INITIAL_X/Y POS
// initialize all IMAGE_WIDTH/HEIGHT
// initialize all Y_UPPER/LOWER BOUNDS
// initialize all VERTICAL_VELOCITIES
// initialize all HORIZONTAL_VELOCITIES
// initialize all HEALTH

    public static final double USER_SCALAR = .05;
    public static final double USER_INITIAL_X_POSITION_SCALAR = .01;
    public static final double USER_INITIAL_Y_POSITION_SCALAR = .5;
    public static final double USER_VERTICAL_VELOCITY = screenHeight * .015;
    public static final long USER_GUN_COOLDOWN = 150; // Cooldown in milliseconds
    public static final long USER_MISSILE_COOLDOWN = 1000; // Cooldown in milliseconds
    
    public static final int BOSS_IMAGE_HEIGHT =  (int) (screenHeight * .2);
    public static final int BOSS_IMAGE_WIDTH =  (int) (screenWidth * .2);   // Dynamically get width
    public static final double BOSS_INITIAL_X_POSITION = screenWidth - (screenWidth * .3);
    public static final double BOSS_INITIAL_Y_POSITION = screenHeight * .5;
    public static final int BOSS_Y_POSITION_UPPER_BOUND =  (int) -(screenHeight * .01);
    public static final int BOSS_Y_POSITION_LOWER_BOUND = screenHeight - (2*BOSS_IMAGE_HEIGHT) - BOSS_Y_POSITION_UPPER_BOUND;
    public static final int BOSS_VERTICAL_VELOCITY = (int) (screenHeight * .0075);
    public static final double BOSS_FIRE_RATE = .04;
    public static final double BOSS_SHIELD_PROBABILITY = .05;
    public static final int BOSS_HEALTH = 15;
    public static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    public static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    public static final int MAX_FRAMES_WITH_SHIELD = 50;

    public static final double ENEMY1_SCALAR = .05;
    public static final int ENEMY1_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);;
    public static final int ENEMY1_INITIAL_HEALTH = 1;
    public static final double ENEMY1_FIRE_RATE = .01;

    public static final double ENEMY2_SCALAR = .07;
    public static final int ENEMY2_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);;
    public static final int ENEMY2_INITIAL_HEALTH = 2;
    public static final double ENEMY2_FIRE_RATE = .02;

    public static final double ENEMY3_SCALAR = .08;
    public static final int ENEMY3_HORIZONTAL_VELOCITY = (int) -(screenWidth * .0025);;
    public static final int ENEMY3_INITIAL_HEALTH = 3;
    public static final double ENEMY3_FIRE_RATE = .02;

    public static final double ENEMY_PROJECTILE_SCALAR = .02;
    public static final double ENEMY_PROJECTILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
    public static final int ENEMY_PROJECTILE_DAMAGE = 1;

    public static final double ENEMY_MISSILE_SCALAR = .03;
    public static final double ENEMY_MISSILE_HORIZONTAL_VELOCITY = -(screenWidth * .01);
    public static final int ENEMY_MISSILE_DAMAGE = 3;

    public static final double USER_PROJECTILE_SCALAR = .01;
    public static final double USER_PROJECTILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
    public static final int USER_PROJECTILE_DAMAGE = 1;

    public static final double USER_MISSILE_SCALAR = .01;
    public static final double USER_MISSILE_HORIZONTAL_VELOCITY = (screenWidth * .01);
    public static final int USER_MISSILE_DAMAGE = 3;

// initialize all FIRE_RATES

    public static final int HEART_SIZE = 50;
    public static final int HEART_SPACING = 10;
    public static final int KILL_SIZE = 50;
    public static final int SHIELD_SIZE = 50;

    public static final int SPARK_SIZE = 50;
    public static final int EXPLOSION_SIZE = 75;
    public static final int SPARK_DURATION = 1;
    public static final int EXPLOSION_DURATION = 2;

    public static final double HEART_X_POSITION = .5*HEART_SIZE;
    public static final double HEART_Y_POSITION = (.5*HEART_SIZE);

    public static final double KILL_X_POSITION = screenWidth - (3*KILL_SIZE);
    public static final double KILL_Y_POSITION = (.5*KILL_SIZE);

    public static final double BOSS_HEALTH_X_POSITION = screenWidth - (6*HEART_SIZE);
    public static final double BOSS_HEALTH_Y_POSITION = (.5*HEART_SIZE);

    public static final double SHIELD_X_POSITION = screenWidth - (8*SHIELD_SIZE);
    public static final double SHIELD_Y_POSITION = (.5*SHIELD_SIZE);


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setResolution(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public static boolean isFirstRun() {
        return isFirstRun;
    }

    public static void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }
}
