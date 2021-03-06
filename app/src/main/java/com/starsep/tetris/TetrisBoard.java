package com.starsep.tetris;

import android.opengl.Matrix;
import android.util.Log;

public class TetrisBoard {
    private TetrisBlock[][] blocks;
    private static final int HEIGHT = 20;
    private static final int WIDTH = 10;
    private static final int HEIGHT_HIDDEN = 5;
    private Tetromino current = null;
    private int currentWidth;
    private int currentHeight;
    private int frameCounter = 0;
    private long last = 0;

    private TetrisBoard() {
        blocks = new TetrisBlock[WIDTH][];
        for (int i = 0; i < WIDTH; i++) {
            blocks[i] = new TetrisBlock[HEIGHT + HEIGHT_HIDDEN];
        }
        clearBoard();
        spawnRandom();
    }

    private void clearBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 1; j < HEIGHT + HEIGHT_HIDDEN; j++) {
                blocks[i][j] = TetrisBlock.empty();
            }
            blocks[i][0] = TetrisBlock.wall();
        }
    }

    private static TetrisBoard instance = new TetrisBoard();

    public static TetrisBoard board() {
        return instance;
    }

    private void update() {
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                if (currentBlocks[i][j] != TetrisBlock.empty()) {
                    blocks[currentWidth + i][currentHeight + j] = currentBlocks[i][j];
                }
            }
        }
    }

    private void rotate() {
        clear();
        current.rotate(false);
        if (collisionRotation()) {
            current.rotate(true);
        }
        update();
    }

    private void clear() {
        if (current != null) {
            TetrisBlock[][] currentBlocks = current.getBlocks();
            for (int i = 0; i < currentBlocks.length; i++) {
                for (int j = 0; j < currentBlocks[i].length; j++) {
                    if (currentBlocks[i][j] != TetrisBlock.empty()) {
                        blocks[currentWidth + i][currentHeight + j] = TetrisBlock.empty();
                    }
                }
            }
        }
    }

    private void spawnRandom() {
        current = Tetromino.getRandom();
        currentWidth = (WIDTH - current.size()) / 2;
        currentHeight = HEIGHT;
        update();
        if (collisionDown()) {
            clearBoard();
            spawnRandom();
        }
    }

    private void debug() {
        if (frameCounter % 20 == 0) {
            down();
        }
    }

    public void draw(float[] VPMatrix) {
        float[] model = new float[16];
        float[] MVPMatrix = new float[16];
        TetrisBlock.initDraw();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 1; j <= HEIGHT; j++) {
                Matrix.setIdentityM(model, 0);
                Matrix.translateM(model, 0, 5 + 10 * i, -5 + 10 * j, 0.0f);
                Matrix.scaleM(model, 0, 5, 5, 1.0f);
                Matrix.multiplyMM(MVPMatrix, 0, VPMatrix, 0, model, 0);
                blocks[i][j].draw(MVPMatrix);
            }
        }
        TetrisBlock.endDraw();
        frameCounter++;
        debug();
    }

    public void showFps() {
        Log.d("Fps:", String.valueOf(frameCounter - last));
        last = frameCounter;
    }

    public void onClick() {
        rotate();
    }

    public void left() {
        if (!collisionLeft()) {
            clear();
            currentWidth--;
            update();
        }
    }

    public void right() {
        if (!collisionRight()) {
            clear();
            currentWidth++;
            update();
        }
    }

    private boolean collisionDown() {
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                if (currentBlocks[i][j].isNotEmpty()) {
                    boolean downNotEmpty = ((j == 0) ||
                            (currentBlocks[i][j - 1].isEmpty())) &&
                            blocks[i + currentWidth][j - 1 + currentHeight].isNotEmpty();
                    if (downNotEmpty) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean collisionRight() {
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                if (currentBlocks[i][j].isNotEmpty()) {
                    if (i + 1 + currentWidth == WIDTH) {
                        return true;
                    }
                    if (((i == currentBlocks[i].length - 1) ||
                            (currentBlocks[i + 1][j].isEmpty())) &&
                            blocks[i + 1 + currentWidth][j + currentHeight].isNotEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean collisionLeft() {
        TetrisBlock[][] currentBlocks = current.getBlocks();
        for (int i = 0; i < currentBlocks.length; i++) {
            for (int j = 0; j < currentBlocks[i].length; j++) {
                if (currentBlocks[i][j].isNotEmpty()) {
                    if (i + currentWidth == 0) {
                        return true;
                    }
                    if (((i == 0) ||
                            (currentBlocks[i - 1][j].isEmpty())) &&
                            blocks[i - 1 + currentWidth][j + currentHeight].isNotEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean collisionRotation() {
        try {
            TetrisBlock[][] currentBlocks = current.getBlocks();
            for (int i = 0; i < currentBlocks.length; i++) {
                for (int j = 0; j < currentBlocks[i].length; j++) {
                    if (currentBlocks[i][j].isNotEmpty() &&
                            blocks[i + currentWidth][j + currentHeight].isNotEmpty()) {
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException unused) {
            return true;
        }
        return false;
    }


    public void down() {
        if (collisionDown()) {
            spawnRandom();
            return;
        }
        clear();
        currentHeight--;
        update();
        checkCollapse();
    }

    public void top() {
        while (!collisionDown()) {
            down();
        }
    }

    private boolean levelCollapse(int q) {
        for (int i = 0; i < WIDTH; i++) {
            if (blocks[i][q].isEmpty() || blocks[i][q].isWall()) {
                return false;
            }
        }
        return true;
    }

    private void collapse(int q) {
        for (int i = q; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                blocks[j][i] = blocks[j][i + 1];
            }
        }
    }

    private void checkCollapse() {
        clear();
        for(int i = 0; i < HEIGHT; i++) {
            if (levelCollapse(i)) {
                collapse(i);
                i--;
            }
        }
        update();
    }
}
