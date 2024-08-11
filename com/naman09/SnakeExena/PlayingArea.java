package com.naman09.SnakeExena;

import java.util.*;

public class PlayingArea {
    List<List<Character>> grid;
    Integer size;

    Character fillCharacter;
    Character bottomFillCharacter;
    Character sideFillCharacter;

    public PlayingArea() {
        this.size = 20;
        this.fillCharacter = '_';
        this.bottomFillCharacter = '-';
        this.sideFillCharacter = '|';
        this.grid = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            ArrayList<Character> row = new ArrayList<>();
            for (int j = 0; j < this.size; j++) {
                row.add(' ');  // Initialize each element with 0 (or any other value)
            }
            this.grid.add(row);
        }

        this.fillBoundary();
    }

    private void fillBoundary() {
        for (int i = 0; i < this.size; i++) {
            this.grid.get(i).set(0, this.sideFillCharacter);
            this.grid.get(i).set(this.size - 1, this.sideFillCharacter);
        }

        for(int i=0;i<this.size;i++) {
            this.grid.get(0).set(i, this.fillCharacter);
            this.grid.get(this.size-1).set(i, this.bottomFillCharacter);
        }
    }

    public void debugPrintGrid() {
        for (List<Character> row : this.grid) {
            StringBuilder sb = new StringBuilder();
            for (Character c : row) {
                sb.append(c);
            }
            System.out.println(sb.toString());
        }
    }
}