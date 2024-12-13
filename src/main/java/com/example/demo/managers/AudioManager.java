/**
 * Manages audio playback for the game "F-15: Strike Eagle."
 *
 * The AudioManager class provides methods to play various sound effects and background music
 * associated with in-game events such as collisions, destruction, and transitions.
 */
package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.media.AudioClip;

/**
 * AudioManager handles the playback of audio clips for different game events and actions.
 */
public class AudioManager {

    /**
     * Plays the sound effect for a spark collision.
     */
    public void sparkAudio() {
        playAudio(Config.ENEMY_TAKE_DAMAGE_AUDIO);
    }

    /**
     * Plays the sound effect for a destruction event.
     */
    public void destructionAudio() {
        playAudio(Config.DESTRUCTION_AUDIO);
    }

    /**
     * Plays the sound effect for a collision between planes.
     */
    public void collisionAudio() {
        playAudio(Config.PLANE_COLLISION_AUDIO);
    }

    /**
     * Plays the sound effect for a unit taking damage.
     */
    public void takeDamageAudio() {
        playAudio(Config.FRIENDLY_TAKE_DAMAGE_AUDIO);
    }

    /**
     * Plays the sound effect for winning the game.
     */
    public static void winAudio() {
        playAudio(Config.WIN_AUDIO);
    }

    /**
     * Plays the sound effect for losing the game.
     */
    public static void loseAudio() {
        playAudio(Config.LOSE_AUDIO);
    }

    /**
     * Plays the sound effect for transitioning to the next level.
     */
    public static void transitionAudio() {
        playAudio(Config.TRANSITION_AUDIO);
    }

    /**
     * Plays the sound effect for starting the game.
     */
    public static void startAudio() {
        playAudio(Config.START_AUDIO);
    }

    /**
     * Plays the background music for the game.
     */
    public static void backgroundOST() {
        playAudio(Config.BACKGROUND_AUDIO);
    }

    /**
     * Plays the specified audio clip.
     *
     * @param AudioPath the path to the audio file.
     */
    public static void playAudio(String AudioPath) {
        AudioClip Audio = new AudioClip(AudioManager.class.getResource(AudioPath).toExternalForm());
        Audio.play();
    }
}
