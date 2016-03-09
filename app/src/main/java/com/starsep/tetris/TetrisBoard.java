package com.starsep.tetris;

import android.opengl.Matrix;

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

    public TetrisBoard() {
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

    private void rotate() {
        clear();
        currentHeight--;
        if (currentHeight < 0) {
            currentHeight = 16;
        }
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                blocks[currentWidth + i][currentHeight + j] = currentBlocks[i][j];
            }
        }
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

    private void update() {
        if (counter % 100 == 0 || clicked) {
            counter = 0;
            clicked = false;
            setRandom();
            currentWidth += tmpRandom.nextInt(3) - 1;
            if (currentWidth < 0) {
                currentWidth = 0;
            }
            if (currentWidth > 6) {
                currentWidth = 6;
            }
        }
        if (counter % 20 == 0) {
            rotate();
        }
    }

    public void draw(float[] VPMatrix) {
        float[] model = new float[16];
        float[] MVPMatrix = new float[16];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Matrix.setIdentityM(model, 0);
                Matrix.translateM(model, 0, 30 + 60 * i + 150, 30 + 60 * j + 200, 0.0f);
                Matrix.scaleM(model, 0, 30, 30, 1.0f);
                Matrix.multiplyMM(MVPMatrix, 0, VPMatrix, 0, model, 0);
                blocks[i][j].draw(MVPMatrix);
            }
        }
        counter++;
        update();
    }

    public void onClick() {
        clicked = true;
    }
}
