package com.starsep.tetris;

public abstract class Tetromino {
    protected TetrisBlock[][] blocks;

    public TetrisBlock[][] getBlocks() {
        return blocks;
    }

    public Tetromino() {
        blocks = new TetrisBlock[size()][];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TetrisBlock[size()];
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = TetrisBlockEmpty.get();
            }
        }
        int[] coords = coords();
        if (coords.length != 8) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < 4; i++) {
            blocks[coords[2 * i]][coords[2 * i + 1]] = block();
        }
    }

    public abstract void rotate(boolean left);

    public abstract int size();

    protected void swap(int[] coords) {
        if (coords == null || coords.length != 8) {
            throw new IllegalArgumentException();
        }
        TetrisBlock tmp = blocks[coords[0]][coords[1]];
        for (int i = 0; i < 3; i++) {
            blocks[coords[2 * i]][coords[2 * i + 1]] = blocks[coords[2 * i + 2]][coords[2 * i + 3]];
        }
        blocks[coords[6]][coords[7]] = tmp;
    }

    public abstract TetrisBlock block();

    public abstract int[] coords();

}
