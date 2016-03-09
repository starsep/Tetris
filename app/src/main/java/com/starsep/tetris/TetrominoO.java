package com.starsep.tetris;

public class TetrominoO extends Tetromino {
    public TetrominoO() {
        super();
    }

    @Override
    public void rotate(boolean unused) {
    }

    @Override
    public int size() {
        return 2;
    }

    private final static TetrisBlock block = new TetrisBlock(1.0f, 1.0f, 0.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[] {0, 0, 0, 1, 1, 0, 1, 1};

    @Override
    public int[] coords() {
        return coords;
    }
}
