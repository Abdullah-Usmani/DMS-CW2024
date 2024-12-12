package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;

public class SoundManager {

    public void playSparkSound() {
        playSound(Config.ENEMY_TAKE_DAMAGE_AUDIO);
    }

    public void playDestructionSound() {
        playSound(Config.DESTRUCTION_AUDIO);
    }

    public void playCollisionSound() {
        playSound(Config.PLANE_COLLISION_AUDIO);

    }

    public void playTakeDamageSound() {
        playSound(Config.FRIENDLY_TAKE_DAMAGE_AUDIO);
    }

    private void playSound(String soundPath) {
        AudioClip sound = new AudioClip(getClass().getResource(soundPath).toExternalForm());
        sound.play();
    }
}
