package com.example.demo;

public class Config {
    private static int screenWidth = 1280; // Default width
    private static int screenHeight = 720; // Default height

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
}