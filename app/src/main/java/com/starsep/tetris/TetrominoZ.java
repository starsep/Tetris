package com.starsep.tetris;

public class TetrominoZ extends Tetromino3 {
    public TetrominoZ() {
        super();
    }

    private final static TetrisBlock block = new TetrisBlock(1.0f, 0.0f, 0.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[]{0, 2, 1, 1, 1, 2, 2, 1};

    @Override
    public int[] coords() {
        return coords;
    }
}
