package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;

public class AudioManager {

    public void sparkAudio() {
        playAudio(Config.enemyTakeDamageAudio);
    }

    public void destructionAudio() {
        playAudio(Config.destructionAudio);
    }

    public void collisionAudio() {
        playAudio(Config.planeCollisionAudio);
    }

    public void takeDamageAudio() {
        playAudio(Config.friendlyTakeDamageAudio);
    }

    public static void winAudio() {
        playAudio(Config.winAudio);
    }

    public static void loseAudio() {
        playAudio(Config.loseAudio);
    }

    public static void transitionAudio() {
        playAudio(Config.transitionAudio);
    }

    public static void startAudio() {
        playAudio(Config.startAudio);
    }

    public static void backgroundOST() {
        playAudio(Config.backgroundAudio);
    }

    public static void playAudio(String AudioPath) {
        AudioClip Audio = new AudioClip(AudioManager.class.getResource(AudioPath).toExternalForm());
        Audio.play();
    }
}
