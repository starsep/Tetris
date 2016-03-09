package com.starsep.tetris;

public class TetrominoT extends Tetromino3 {
    public TetrominoT() {
        super();
    }

    private final static TetrisBlock block = new TetrisBlock(0.5f, 0.0f, 1.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[]{0, 1, 1, 1, 1, 2, 2, 1};

    @Override
    public int[] coords() {
        return coords;
    }
}
