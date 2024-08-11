package com.naman09.SnakeExena;

import com.naman09.SnakeExena.enums.KeyboardKey;
import java.util.*;

public class Snake {
    Vector2 head;
    PlayingArea playingArea;
    List<List<Vector2>> bodyGrid;

    Snake(PlayingArea playingArea) {
        
        this.playingArea = playingArea;
        
        this.head  = new Vector2(this.playingArea.grid.get(0).size() - 2, this.playingArea.grid.size() - 5);
        this.bodyGrid = new ArrayList<>();
        initialBody();
    }

    private void initialBody() {
        for (int  i = 0; i < this.playingArea.grid.size(); i++) {
            ArrayList<Vector2> row = new ArrayList<>();
            for (int j = 0; j < this.playingArea.grid.get(0).size(); j++) {
                row.add(new Vector2(null, null));
            }
            this.bodyGrid.add(row);
        }
        this.bodyGrid.get(this.head.y).set(this.head.x, new Vector2(0, 1));
        this.bodyGrid.get(this.head.y + 1).set(this.head.x, new Vector2(0, 1));
        this.bodyGrid.get(this.head.y + 2).set(this.head.x, new Vector2(0, 1));
        this.bodyGrid.get(this.head.y + 3).set(this.head.x, new Vector2(0, 0));

        draw();
    }

    private void draw() {
        for (int i = 0; i < this.bodyGrid.size(); i++) {
            for (int j = 0; j < this.bodyGrid.size(); j++) {
                Vector2 pos = this.bodyGrid.get(i).get(j);
                if (pos.x != null && pos.y != null) {
                    if (pos.x == -1 && pos.y == -1) {
                        this.playingArea.grid.get(i).set(j, ' ');  
                        this.bodyGrid.get(i).set(j, new Vector2(null, null));
                    } else {
                        this.playingArea.grid.get(i).set(j, '*');
                    }
                }
            }
        }

        this.playingArea.grid.get(this.head.y).set(this.head.x, '@');
    }

    public void update(KeyboardKey keyPressed) {
        // System.out.println("Key pressed inside snake update:" + keyPressed.name());
        Vector2 move = getMove(keyPressed);

        //find tail
        Vector2 tailParent = findTailParent();
        if (tailParent == null) {
            System.err.println("could not find tail parent");
            return;
        }
        // System.out.println("tP: " + tailParent);
        Vector2 tail = new Vector2(
            tailParent.x + this.bodyGrid.get(tailParent.y).get(tailParent.x).x,
            tailParent.y + this.bodyGrid.get(tailParent.y).get(tailParent.x).y
        );
        // System.out.println("t: " + tail);
        
        //del tail
        this.bodyGrid.get(tail.y).set(tail.x, new Vector2(-1, -1));

        //update tail
        this.bodyGrid.get(tailParent.y).set(tailParent.x, new Vector2(0, 0));

        //create new head
        this.head.x += move.x;
        this.head.y += move.y;

        // set prev value for new head
        this.bodyGrid.get(this.head.y).set(this.head.x, new Vector2(-move.x, -move.y));

        draw();
    }

    private Vector2 getMove(KeyboardKey keyPressed) {
        if (keyPressed == KeyboardKey.A) {
            return new Vector2(-1, 0);
        }

        if (keyPressed == KeyboardKey.D) {
            return new Vector2(1, 0);
        }

        if (keyPressed == KeyboardKey.S) {
            return new Vector2(0, 1);
        }

        if (keyPressed == KeyboardKey.W) {
            return new Vector2(0, -1);
        }
        Vector2 headPrevDir = this.bodyGrid.get(this.head.y).get(this.head.x);
        
        return new Vector2(-headPrevDir.x, -headPrevDir.y); //opposite of head prev
    }

    private Vector2 findTailParent() {
        // return new Vector2(this.head.x, this.head.y+2);
        //TODO
        // find 0,0
        
        Vector2 tail = null;
        for(int i=0;i<this.bodyGrid.size();i++) {
            for(int j=0;j<this.bodyGrid.get(0).size(); j++) {
                Vector2 pos = this.bodyGrid.get(i).get(j);
                if (pos.x == null || pos.y == null) {
                    continue;
                }
                if (pos.x == 0 && pos.y == 0) {
                    tail = new Vector2(j, i);
                    break;
                }
            }
        }
        
        if (tail == null) {
            return null;
        }

        Integer[] dirX = { 1, 0, -1, 0 };
        Integer[] dirY = { 0, 1, 0, -1 };
        // System.out.println("tail: " + tail);
        for(int i=0;i<4;i++) {
            Vector2 pos = this.bodyGrid.get(tail.y + dirY[i]).get(tail.x + dirX[i]);
            if (pos.x != null && pos.y != null) {
                return new Vector2(tail.x + dirX[i], tail.y + dirY[i]);
            }
        }

        return null;
    }
}
