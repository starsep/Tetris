package com.starsep.tetris;

public class TetrominoS extends Tetromino3 {
    public TetrominoS() {
        super();
    }

    private final static TetrisBlock block = new TetrisBlock(0.0f, 1.0f, 0.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[] {0, 1, 1, 1, 1, 2, 2, 2};

    @Override
    public int[] coords() {
        return coords;
    }
}
