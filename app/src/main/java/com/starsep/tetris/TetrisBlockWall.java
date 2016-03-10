package com.starsep.tetris;

public class TetrisBlockWall extends TetrisBlock {
    private TetrisBlockWall() {
        super(1.0f, 0.0f, 0.0f, 1.0f);
    }
    private static TetrisBlock instance = new TetrisBlockWall();
    public static TetrisBlock get() {
        return instance;
    }
}
