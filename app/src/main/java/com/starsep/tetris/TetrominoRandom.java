package com.starsep.tetris;

import java.util.Random;

public class TetrominoRandom {
    public static Tetromino get() {
        Random random = new Random();
        switch (random.nextInt(7)) {
            case 0:
                return new TetrominoI();
            case 1:
                return new TetrominoJ();
            case 2:
                return new TetrominoL();
            case 3:
                return new TetrominoO();
            case 4:
                return new TetrominoS();
            case 5:
                return new TetrominoT();
            case 6:
                return new TetrominoZ();
        }
        return null;
    }
}
