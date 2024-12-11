package com.example.demo.managers;

import javafx.scene.media.AudioClip;

public class SoundManager {

    public void playSound(String soundPath) {
        if (soundPath != null) {
            AudioClip sound = new AudioClip(getClass().getResource(soundPath).toExternalForm());
            sound.play();
        }
    }
}
