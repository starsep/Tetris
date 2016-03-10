package com.starsep.tetris;

import android.opengl.Matrix;
import android.util.Log;

import java.sql.Time;
import java.util.Random;

public class TetrisBoard {
    private TetrisBlock[][] blocks;
    private static final int HEIGHT = 20;
    private static final int WIDTH = 10;
    private static final int HEIGHT_HIDDEN = 4;
    private Tetromino current = null;
    private int currentWidth = 6;
    private int currentHeight = 16;
    private int counter = 0;
    private boolean clicked = false;
    private Random tmpRandom;
    private long last = 0;

    private TetrisBoard() {
        tmpRandom = new Random();
        blocks = new TetrisBlock[WIDTH][];
        for (int i = 0; i < WIDTH; i++) {
            blocks[i] = new TetrisBlock[HEIGHT + HEIGHT_HIDDEN];
            for (int j = 0; j < HEIGHT + HEIGHT_HIDDEN; j++) {
                blocks[i][j] = TetrisBlockEmpty.get();
            }
        }
        setRandom();
    }

    private static TetrisBoard instance = new TetrisBoard();

    public static TetrisBoard board() {
        return instance;
    }

    private void update() {
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                blocks[currentWidth + i][currentHeight + j] = currentBlocks[i][j];
            }
        }
    }

    private void rotate() {
        current.rotate(false);
    }

    private void clear() {
        if (current != null) {
            TetrisBlock[][] currentBlocks = current.getBlocks();
            for (int i = 0; i < currentBlocks.length; i++) {
                for (int j = 0; j < currentBlocks[i].length; j++) {
                    blocks[currentWidth + i][currentHeight + j] = TetrisBlockEmpty.get();
                }
            }
        }
    }

    private void setRandom() {
        clear();
        current = TetrominoRandom.get();
    }

    private void debug() {
        if (counter % 100 == 0 || clicked) {
            counter = 0;
            clicked = false;
            setRandom();
            /*if (tmpRandom.nextInt(2) == 0) {
                left();
            } else {
                right();
            }*/
        }
        if (counter % 20 == 0) {
            down();
            rotate();
        }
    }

    public void draw(float[] VPMatrix) {
        float[] model = new float[16];
        float[] MVPMatrix = new float[16];
        //last = System.currentTimeMillis();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Matrix.setIdentityM(model, 0);
                Matrix.translateM(model, 0, 30 + 60 * i + 150, 30 + 60 * j + 200, 0.0f);
                Matrix.scaleM(model, 0, 30, 30, 1.0f);
                Matrix.multiplyMM(MVPMatrix, 0, VPMatrix, 0, model, 0);
                blocks[i][j].draw(MVPMatrix);
            }
        }
        //Log.d("Time:", String.valueOf(System.currentTimeMillis() - last));
        //last = System.currentTimeMillis();
        counter++;
        debug();
    }

    public void onClick() {
        clicked = true;
    }

    public void left() {
        clear();
        currentWidth--;
        if (currentWidth < 0) {
            currentWidth = 0;
        }
        update();
    }

    public void right() {
        clear();
        currentWidth++;
        if (currentWidth > 6) {
            currentWidth = 6;
        }
        update();
    }

    public void down() {
        clear();
        currentHeight--;
        if (currentHeight < 0) {
            currentHeight = 16;
        }
        update();
    }

    public void top() {
        clear();
        currentHeight = 0;
        update();
    }
}
