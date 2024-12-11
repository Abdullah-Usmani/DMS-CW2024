package com.example.demo;

public class Config {
    public static final int MILLISECOND_DELAY = 30;
    private static int screenWidth = 1280;
    private static int screenHeight = 720;
    private static boolean isFirstRun = true; // Flag to track first run
    public static double SCREEN_HEIGHT_ADJUSTMENT = screenHeight * .1;

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
