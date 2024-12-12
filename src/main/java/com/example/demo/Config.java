package com.example.demo;

public class Config {
    public static final int MILLISECOND_DELAY = 30;

    private static int screenWidth = 1280;
    private static int screenHeight = 720;
    private static boolean isFirstRun = true; // Flag to track first run
    public static double SCREEN_HEIGHT_ADJUSTMENT = screenHeight * .1;

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


//    public static final String START_IMAGE = ImagePath + "background10.jpg";
//    public static final String WIN_IMAGE = ImagePath + "background10.jpg";
//    public static final String LOSE_IMAGE = ImagePath + "background10.jpg";


    public static final String FRIENDLY_TAKE_DAMAGE_AUDIO = AudioPath + "ricochet-2.mp3";
    public static final String ENEMY_TAKE_DAMAGE_AUDIO = AudioPath + "tf2-crit.mp3";
    public static final String PLANE_COLLISION_AUDIO = AudioPath + "fortnite-pump.mp3";
    public static final String DESTRUCTION_AUDIO = AudioPath + "roblox-explosion.mp3";

    public static final String FRIENDLY_GUN_AUDIO = AudioPath + "single-shot.mp3";
    public static final String FRIENDLY_MISSILE_AUDIO = AudioPath + "fortnite-rpg.mp3";
    public static final String ENEMY_GUN_AUDIO = AudioPath + "ricochet-1.mp3";
    public static final String ENEMY_MISSILE_AUDIO = AudioPath + "fortnite-rpg.mp3";

    public static final String WIN_AUDIO = AudioPath + "tf2-win.mp3";
    public static final String LOSE_AUDIO = AudioPath + "tf2-lose.mp3";

// initialize all INITIAL_X/Y POS
// initialize all IMAGE_WIDTH/HEIGHT
// initialize all Y_UPPER/LOWER BOUNDS
// initialize all VERTICAL_VELOCITIES
// initialize all HORIZONTAL_VELOCITIES
// initialize all HEALTH

    public static final int SPARK_SIZE = 50;
    public static final int EXPLOSION_SIZE = 75;
    public static final int SPARK_DURATION = 1;
    public static final int EXPLOSION_DURATION = 2;
// initialize all FIRE_RATES

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
