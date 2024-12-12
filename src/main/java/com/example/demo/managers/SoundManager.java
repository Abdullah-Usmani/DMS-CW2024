package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;

public class SoundManager {

    public void sparkSound() {
        playSound(Config.ENEMY_TAKE_DAMAGE_AUDIO);
    }

    public void destructionSound() {
        playSound(Config.DESTRUCTION_AUDIO);
    }

    public void collisionSound() {
        playSound(Config.PLANE_COLLISION_AUDIO);
    }

    public void takeDamageSound() {
        playSound(Config.FRIENDLY_TAKE_DAMAGE_AUDIO);
    }

    public static void winSound() {
        playSound(Config.WIN_AUDIO);
    }

    public static void loseSound() {
        playSound(Config.LOSE_AUDIO);
    }

    public static void backgroundOST() {
        playSound(Config.BACKGROUND_AUDIO);
    }

    public static void playSound(String soundPath) {
        AudioClip sound = new AudioClip(SoundManager.class.getResource(soundPath).toExternalForm());
        sound.play();
    }
}
