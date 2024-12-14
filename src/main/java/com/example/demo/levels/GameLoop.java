/**
 * Manages the main game loop for "F-15: Strike Eagle."
 *
 * The GameLoop class handles the timing and execution of repeated game actions,
 * such as updating game state and rendering frames.
 */
package com.example.demo.levels;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * GameLoop is responsible for running the core game actions at a fixed interval.
 */
public class GameLoop {

    /** Timeline for managing the game loop. */
    final Timeline timeline;

    /**
     * Constructs a GameLoop with the specified action and interval.
     *
     * @param action             the action to be executed on each loop iteration.
     * @param intervalMilliseconds the interval between iterations, in milliseconds.
     */
    public GameLoop(Runnable action, int intervalMilliseconds) {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(intervalMilliseconds), e -> action.run()));
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        timeline.stop();
    }
}