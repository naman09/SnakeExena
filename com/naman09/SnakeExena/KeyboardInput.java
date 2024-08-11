package com.naman09.SnakeExena;

import java.util.Scanner;
import java.util.concurrent.*; 
import com.naman09.SnakeExena.enums.KeyboardKey;

public class KeyboardInput implements IInput{
    Thread listener;
    Scanner scanner;
    Semaphore sem1;
    KeyboardKey currentPressedKey;

    KeyboardInput() {
        this.scanner = new Scanner(System.in);
        this.sem1 = new Semaphore(1);
    }

    public void startListening() {
        this.listener = new Thread(this);
        this.currentPressedKey = KeyboardKey.NIL;
        this.listener.start();
    }

    public void stopListening() throws InterruptedException{
        System.out.println("Stopping keyboard listener");

        this.listener.join();
        System.out.println("||Stopped keyboard listener");
        scanner.close();
    }
    
    @Override
    public void run(){
        while(scanner.hasNext()) {
            String inputString = scanner.next();
            inputString = inputString.toUpperCase();
            System.out.println("Key pressed:" + inputString.charAt(0));
            try {
                KeyboardKey newKey = KeyboardKey.valueOf(String.valueOf(inputString.charAt(0)));
                sem1.acquire();
                this.currentPressedKey = newKey;
                sem1.release();
                if (newKey == KeyboardKey.Q) {
                    System.out.println("exiting keyboard thread");
                    break;
                }
            } catch(IllegalArgumentException e) {
                System.out.println("invalid key pressed!");
            } catch (InterruptedException e) { //NOSONAR
                e.printStackTrace();
            }
        }
    }
    
    KeyboardKey getKeyPressed() throws InterruptedException {
        sem1.acquire();
        KeyboardKey copyCurrPressedKey = this.currentPressedKey;
        this.currentPressedKey = KeyboardKey.NIL;
        sem1.release();
        if (currentPressedKey == null) {
            return KeyboardKey.NIL;
        }
        return copyCurrPressedKey;
    }
}
