package com.example.demo;

public class Config {

    private Config() {
        throw new IllegalStateException("Config class");
    }

    public static final String gameTitle = "F-15: Strike Eagle";

    public static final int MILLISECOND_DELAY = 30;

    private static int screenWidth = 1280; // Default Screen Width
    private static int screenHeight = 720;  // Default Screen Height
    private static boolean isFirstRun = true; // Flag to track first run
    public static double screenHeightAdjustment = screenHeight * .1;

    public static final String levelPath = "com.example.demo.levels.";
    public static final String imagePath = "/com/example/demo/images/";
    public static final String audioPath = "/com/example/demo/audio/";
    public static final String fontPath = "/com/example/demo/fonts/";

    public static final String explosionImage = imagePath + "explosion.png";
    public static final String sparkImage = imagePath + "sparks.png";

    public static final String userImage = imagePath + "userplane.png";
    public static final String enemy1Image = imagePath + "enemyplane.png";
    public static final String enemy2Image = imagePath + "enemymig-29.png";
    public static final String enemy3Image = imagePath + "enemya-10c.png";
    public static final String bossImage = imagePath + "enemyc-17.png";

    public static final String friendlyGun = imagePath + "userfire.png";
    public static final String friendlyMissile = imagePath + "usersidewinder.png";
    public static final String enemyGun = imagePath + "enemyfire.png";
    public static final String enemyMissile = imagePath + "enemymissiler-33.png";
    public static final String bossMissile = imagePath + "bossfire.png";

    public static final String heartImage = imagePath + "heart.png";
    public static final String shieldImage = imagePath + "shieldbubble.png";
    public static final String killImage = imagePath + "killcount.png";

    public static final String startBackground = imagePath + "backgroundstart2.jpg";
    public static final String level1Background = imagePath + "background2.jpg";
    public static final String level2Background = imagePath + "background6.jpg";
    public static final String level3Background = imagePath + "background9.jpg";
    public static final String levelBossBackground = imagePath + "background10.jpg";

    public static final String level1ClassName = levelPath + "level1";
    public static final String level2ClassName = levelPath + "level2";
    public static final String level3ClassName = levelPath + "level3";
    public static final String levelBossClassName = levelPath + "LevelBoss";

    public static final String winImage = imagePath + "you-win-AI.png";
    public static final String lossImage = imagePath + "game-over-AI.png";

    public static final String backgroundAudio = audioPath + "backgroundOST.mp3";

    public static final String friendlyTakeDamageAudio = audioPath + "userhit.mp3";
    public static final String enemyTakeDamageAudio = audioPath + "spark.mp3";
    public static final String planeCollisionAudio = audioPath + "collision.mp3";
    public static final String destructionAudio = audioPath + "roblox-explosion.mp3";

    public static final String friendlyGunAudio = audioPath + "single-shot.mp3";
    public static final String friendlyMissileAudio = audioPath + "fortnite-rpg-shoot.mp3";
    public static final String enemyGunAudio = audioPath + "enemyfire.mp3";
    public static final String enemyMissileAudio = audioPath + "enemymissile.mp3";

    public static final String startAudio = audioPath + "start-audio.mp3";
    public static final String transitionAudio = audioPath + "nextlevel.mp3";
    public static final String winAudio = audioPath + "tf2-win.mp3";
    public static final String loseAudio = audioPath + "tf2-lose.mp3";

    public static final int level1TotalEnemies = 5;
    public static final int level2TotalEnemies = 10;
    public static final int level3TotalEnemies = 10;

    public static final int level1KillsToAdvance = 10;
    public static final int level2KillsToAdvance = 15;
    public static final int level3KillsToAdvance = 20;

    public static final int level1InitialHealth = 5;
    public static final int level2InitialHealth = 10;
    public static final int level3InitialHealth = 15;
    public static final int levelBossInitialHealth = 5;

    public static final double enemy1SpawnProbability = .20;
    public static final double enemy2SpawnProbability = .10;
    public static final double enemy3SpawnProbability = .05;

    public static final double userScalar = .05;
    public static final double userInitialXPositionScalar = .01;
    public static final double userInitialYPositionScalar = .5;
    public static double userVerticalVelocity = screenHeight * .015;
    public static final long userGunCooldown = 150; // Cooldown in milliseconds
    public static final long userMissileCooldown = 1000; // Cooldown in milliseconds
    
    public static int bossImageHeight =  (int) (screenHeight * .2);
    public static int bossImageWidth =  (int) (screenWidth * .2);   // Dynamically get width
    public static double bossInitialXPosition = screenWidth - (screenWidth * .3);
    public static double bossInitialYPosition = screenHeight * .5;
    public static int bossYPositionUpperBound =  (int) -(screenHeight * .01);
    public static int bossYPositionLowerBound = screenHeight - (2* bossImageHeight) - bossYPositionUpperBound;
    public static int bossVerticalVelocity = (int) (screenHeight * .0075);
    public static final double bossFireRate = .04;
    public static final double bossShieldProbability = .02;
    public static final int bossHealth = 15;
    public static final int bossMoveFrequency = 5;
    public static final int bossMaxSameMoveFrames = 10;
    public static final int bossMaxShieldFrames = 50;

    public static final double enemy1Scalar = .05;
    public static int enemy1HorizontalVelocity = (int) -(screenWidth * .0025);
    public static final int enemy1InitialHealth = 1;
    public static final double enemy1FireRate = .01;

    public static final double enemy2Scalar = .07;
    public static int enemy2HorizontalVelocity = (int) -(screenWidth * .0025);
    public static final int enemy2InitialHealth = 2;
    public static final double enemy2FireRate = .02;

    public static final double enemy3Scalar = .08;
    public static int enemy3HorizontalVelocity = (int) -(screenWidth * .0025);
    public static final int enemy3InitialHealth = 3;
    public static final double enemy3FireRate = .02;

    public static final double enemyProjectileScalar = .02;
    public static double enemyProjectileHorizontalVelocity = -(screenWidth * .01);
    public static final int enemyProjectileDamage = 1;

    public static final double enemyMissileScalar = .03;
    public static double enemyMissileHorizontalVelocity = -(screenWidth * .01);
    public static final int enemyMissileDamage = 3;

    public static final double userProjectileScalar = .01;
    public static double userProjectileHorizontalVelocity = (screenWidth * .01);
    public static final int userProjectileDamage = 1;

    public static final double userMissileScalar = .01;
    public static double userMissileHorizontalVelocity = (screenWidth * .01);
    public static final int userMissileDamage = 3;

// initialize all FIRE_RATES

    public static double heartSize = screenWidth*.04;
    public static double heartSpacing = screenWidth*.01;
    public static double killSize = screenWidth*.04;
    public static double shieldSize = screenWidth*.1;

    public static double sparkSize = screenWidth*.04;
    public static double explosionSize = screenWidth*.05;
    public static final int sparkDuration = 1;
    public static final int explosionDuration = 2;

    public static double heartXPosition = .5* heartSize;
    public static double heartYPosition = (.5* heartSize);

    public static double killXPosition = screenWidth - (4* killSize);
    public static double killYPosition = (.5* killSize);

    public static double bossHealthXPosition = screenWidth - (7* heartSize);
    public static double bossHealthYPosition = (.5* heartSize);

//    public static double shieldXPosition = screenWidth - (8* shieldSize);
//    public static double shieldYPosition = (.5* shieldSize);


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setResolution(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        calculateResolutionDependentValues();
    }

    public static void calculateResolutionDependentValues() {
        userVerticalVelocity = screenHeight * .015;
        bossImageHeight =  (int) (screenHeight * .2);
        bossImageWidth =  (int) (screenWidth * .2);
        bossInitialXPosition = screenWidth - (screenWidth * .3);
        bossInitialYPosition = screenHeight * .5;
        bossYPositionUpperBound =  (int) -(screenHeight * .01);
        bossYPositionLowerBound = screenHeight - (2* bossImageHeight) - bossYPositionUpperBound;
        bossVerticalVelocity = (int) (screenHeight * .0075);
        enemy1HorizontalVelocity = (int) -(screenWidth * .0025);
        enemy2HorizontalVelocity = (int) -(screenWidth * .0025);
        enemy3HorizontalVelocity = (int) -(screenWidth * .0025);
        enemyProjectileHorizontalVelocity = -(screenWidth * .01);
        enemyMissileHorizontalVelocity = -(screenWidth * .01);
        userProjectileHorizontalVelocity = (screenWidth * .01);
        userMissileHorizontalVelocity = (screenWidth * .01);
        heartSize = screenWidth*.04;
        heartSpacing = screenWidth*.01;
        killSize = screenWidth*.04;
        shieldSize = screenWidth*.04;
        sparkSize = screenWidth*.04;
        explosionSize = screenWidth*.05;
        heartXPosition = .5* heartSize;
        killXPosition = screenWidth - (4* killSize);
        bossHealthXPosition = screenWidth - (7* heartSize);
        heartYPosition = (.5* heartSize);
        killYPosition = (.5* killSize);
        bossHealthYPosition = (.5* heartSize);
    }

    public static boolean isFirstRun() {
        return isFirstRun;
    }

    public static void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }
}
