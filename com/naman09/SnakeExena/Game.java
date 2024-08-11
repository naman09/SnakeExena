package com.naman09.SnakeExena;

import com.naman09.SnakeExena.enums.KeyboardKey;
import java.time.Instant;
import java.time.Duration;
/*
 * Manages all components in the game and run them in game loop
 */
public class Game {
    Snake snake;
    PlayingArea playingArea;
    KeyboardInput keyboardInput;
    Renderer renderer;
    Boolean isRunning;
    
    private void initialize() {
        this.playingArea = new PlayingArea();
        this.snake = new Snake(this.playingArea);
        this.renderer = new Renderer();
        this.keyboardInput = new KeyboardInput();
        isRunning = true;
    }

    public void run() throws InterruptedException {
        initialize();
        this.keyboardInput.startListening();
        gameLoop();
        System.out.println("Game is exiting");
    }
    private void gameLoop() throws InterruptedException {
        System.out.println("****");
        Instant lastTS = Instant.ofEpochSecond(0);
        while(Boolean.TRUE.equals(this.isRunning)) {
            Instant currentTS = Instant.now();
            Duration duration = Duration.between(lastTS, currentTS);
            long delta = 1000/10;
            
            if (duration.toMillis() < delta) {
                continue;
            }
            lastTS = currentTS;
            KeyboardKey keyPressed = this.keyboardInput.getKeyPressed();
            this.update(keyPressed);
            this.draw();
        }
    }

    private void update(KeyboardKey keyPressed) throws InterruptedException {
        if (keyPressed == KeyboardKey.Q) {
            this.exit();
            return;
        } 
        this.snake.update(keyPressed);
        if (keyPressed == null || keyPressed == KeyboardKey.NIL) {
            return;
        }
        
        System.out.println("Key pressed inside update:" + keyPressed.name());
        // TODO: detectCollision
    }

    private void draw() {
        this.renderer.render(playingArea);
    }

    private void exit() throws InterruptedException{
        this.keyboardInput.stopListening();
        this.isRunning = false;
    }
}
