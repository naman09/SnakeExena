package com.naman09.SnakeExena;

import java.io.*;

public class Renderer {
    public static void clearFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error in clearing file");
        }
    }

    private static void writeToFile(String filename, String text) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error in writing to file");
        }
    }
    public static void draw(String text) {
        String filename = "GameScreen";
        clearFile(filename);
        writeToFile(filename, text);
    }

    public void render(PlayingArea playingArea) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < playingArea.grid.size(); i++) {
            for (int j = 0; j < playingArea.grid.size(); j++) {
                sb.append(playingArea.grid.get(i).get(j));
            }
            sb.append("\n");
        }

        draw(sb.toString());
    }   
}
