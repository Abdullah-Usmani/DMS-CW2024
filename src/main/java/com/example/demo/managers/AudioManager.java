package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;

public class AudioManager {

    public void sparkAudio() {
        playAudio(Config.ENEMY_TAKE_DAMAGE_AUDIO);
    }

    public void destructionAudio() {
        playAudio(Config.DESTRUCTION_AUDIO);
    }

    public void collisionAudio() {
        playAudio(Config.PLANE_COLLISION_AUDIO);
    }

    public void takeDamageAudio() {
        playAudio(Config.FRIENDLY_TAKE_DAMAGE_AUDIO);
    }

    public static void winAudio() {
        playAudio(Config.WIN_AUDIO);
    }

    public static void loseAudio() {
        playAudio(Config.LOSE_AUDIO);
    }

    public static void backgroundOST() {
        playAudio(Config.BACKGROUND_AUDIO);
    }

    public static void playAudio(String AudioPath) {
        AudioClip Audio = new AudioClip(AudioManager.class.getResource(AudioPath).toExternalForm());
        Audio.play();
    }
}
