package com.starsep.tetris;

public class TetrominoI extends Tetromino {
    public TetrominoI() {
        super();
    }

    private static int[] middleSwapsCoords = new int[] {1, 1, 2, 1, 2, 2, 1, 2};
    private static int[] edge1SwapsCoords = new int[] {0, 1, 2, 0, 3, 2, 1, 3};
    private static int[] edge2SwapsCoords = new int[] {1, 0, 3, 1, 2, 3, 0, 2};

    @Override
    public void rotate(boolean left) {
        if (left) {
            //TODO
            rotate(false);
            rotate(false);
            rotate(false);
            return;
        }
        swap(middleSwapsCoords);
        swap(edge1SwapsCoords);
        swap(edge2SwapsCoords);
    }

    @Override
    public int size() {
        return 4;
    }

    private final static TetrisBlock block = new TetrisBlock(0.0f, 1.0f, 1.0f, 1.0f);

    @Override
    public TetrisBlock block() {
        return block;
    }

    private final static int[] coords = new int[] {0, 2, 1, 2, 2, 2, 3, 2};

    @Override
    public int[] coords() {
        return coords;
    }
}
