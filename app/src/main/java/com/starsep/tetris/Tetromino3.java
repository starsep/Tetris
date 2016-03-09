package com.starsep.tetris;

public abstract class Tetromino3 extends Tetromino {

    public Tetromino3() {
        super();
    }

    private static int[] cornerSwapsCoords = new int[]{0, 0, 0, 2, 2, 2, 2, 0};
    private static int[] edgeSwapsCoords = new int[]{0, 1, 1, 2, 2, 1, 1, 0};

    @Override
    public void rotate(boolean left) {
        if (left) {
            //TODO
            rotate(false);
            rotate(false);
            rotate(false);
            return;
        }
        swap(cornerSwapsCoords);
        swap(edgeSwapsCoords);
    }

    @Override
    public int size() {
        return 3;
    }
}
