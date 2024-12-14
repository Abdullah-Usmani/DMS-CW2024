package com.example.demo.managers;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AudioManagerTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit required for AudioClip
        Platform.startup(() -> {
        });
    }

    @Test
    public void testSparkAudio() {

        // Call the method
        AudioManager audioManager = new AudioManager();
        audioManager.sparkAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.ENEMY_TAKE_DAMAGE_AUDIO),
                "Spark audio path is invalid.");
    }

    @Test
    public void testDestructionAudio() {

        // Call the method
        AudioManager audioManager = new AudioManager();
        audioManager.destructionAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.DESTRUCTION_AUDIO),
                "Destruction audio path is invalid.");
    }

    @Test
    public void testCollisionAudio() {
        // Call the method
        AudioManager audioManager = new AudioManager();
        audioManager.collisionAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.PLANE_COLLISION_AUDIO),
                "Collision audio path is invalid.");
    }

    @Test
    public void testTakeDamageAudio() {

        // Call the method
        AudioManager audioManager = new AudioManager();
        audioManager.takeDamageAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.FRIENDLY_TAKE_DAMAGE_AUDIO),
                "Take damage audio path is invalid.");
    }

    @Test
    public void testWinAudio() {

        // Call the static method
        AudioManager.winAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.WIN_AUDIO),
                "Win audio path is invalid.");
    }

    @Test
    public void testLoseAudio() {

        // Call the static method
        AudioManager.loseAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.LOSE_AUDIO),
                "Lose audio path is invalid.");
    }

    @Test
    public void testTransitionAudio() {

        // Call the static method
        AudioManager.transitionAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.TRANSITION_AUDIO),
                "Transition audio path is invalid.");
    }

    @Test
    public void testStartAudio() {

        // Call the static method
        AudioManager.startAudio();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.START_AUDIO),
                "Start audio path is invalid.");
    }

    @Test
    public void testBackgroundOST() {
        // Call the static method
        AudioManager.backgroundOST();

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(Config.BACKGROUND_AUDIO),
                "Background OST path is invalid.");
    }

    @Test
    public void testPlayAudio() {
        // Stub a valid audio path
        String testAudioPath = Config.START_AUDIO;

        // Verify the audio file exists
        assertNotNull(AudioManager.class.getResource(testAudioPath),
                "Audio path is invalid.");

        // Simulate playing the audio file and ensure it does not throw an exception
        assertDoesNotThrow(() -> {
            AudioClip audioClip = new AudioClip(AudioManager.class.getResource(testAudioPath).toExternalForm());
            audioClip.play();
        }, "Audio playback threw an exception.");
    }
}