package com.starsep.tetris;

public class TetrominoL extends Tetromino3 {
    public TetrominoL() {
        super();
    }

    private final static TetrisBlock block = new TetrisBlock(1.0f, 0.5f, 0.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[]{0, 1, 1, 1, 2, 1, 2, 2};

    @Override
    public int[] coords() {
        return coords;
    }
}
