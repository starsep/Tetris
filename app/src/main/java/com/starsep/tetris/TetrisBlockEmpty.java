package com.starsep.tetris;

public class TetrisBlockEmpty extends TetrisBlock {
    private static TetrisBlock instance = new TetrisBlockEmpty();

    private TetrisBlockEmpty() {
        super(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static TetrisBlock get() {
        return instance;
    }

}
