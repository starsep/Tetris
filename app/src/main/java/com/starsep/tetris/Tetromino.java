package com.starsep.tetris;

import java.util.Random;

public abstract class Tetromino {
    protected TetrisBlock[][] blocks;
    protected static Random random = new Random();

    public TetrisBlock[][] getBlocks() {
        return blocks;
    }

    public Tetromino() {
        blocks = new TetrisBlock[size()][];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TetrisBlock[size()];
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = TetrisBlockEmpty.get();
            }
        }
        int[] coords = coords();
        if (coords.length != 8) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < 4; i++) {
            blocks[coords[2 * i]][coords[2 * i + 1]] = block();
        }
    }

    protected abstract void rotateRight();

    public void rotate(boolean left) {
        if (left) {
            rotateRight();
            rotateRight();
            rotateRight();
            return;
        }
        rotateRight();
    }

    public abstract int size();

    protected void swap(int[] coords) {
        if (coords == null || coords.length != 8) {
            throw new IllegalArgumentException();
        }
        TetrisBlock tmp = blocks[coords[0]][coords[1]];
        for (int i = 0; i < 3; i++) {
            blocks[coords[2 * i]][coords[2 * i + 1]] = blocks[coords[2 * i + 2]][coords[2 * i + 3]];
        }
        blocks[coords[6]][coords[7]] = tmp;
    }

    public abstract TetrisBlock block();

    public abstract int[] coords();

    public static Tetromino getRandom() {
        switch (random.nextInt(7)) {
            case 0:
                return I;
            case 1:
                return J;
            case 2:
                return L;
            case 3:
                return O;
            case 4:
                return S;
            case 5:
                return T;
            case 6:
                return Z;
        }
        return null;
    }

    private final static int[] middleSwapsCoords = new int[]{1, 1, 2, 1, 2, 2, 1, 2};
    private final static int[] edge1SwapsCoords = new int[]{0, 1, 2, 0, 3, 2, 1, 3};
    private final static int[] edge2SwapsCoords = new int[]{1, 0, 3, 1, 2, 3, 0, 2};

    private final static TetrisBlock blockI = new TetrisBlock(0.0f, 1.0f, 1.0f, 1.0f);
    private final static int[] coordsI = new int[]{0, 2, 1, 2, 2, 2, 3, 2};
    private final static Tetromino I = new Tetromino() {
        @Override
        protected void rotateRight() {
            swap(middleSwapsCoords);
            swap(edge1SwapsCoords);
            swap(edge2SwapsCoords);
        }

        @Override
        public int size() {
            return 4;
        }

        @Override
        public TetrisBlock block() {
            return blockI;
        }

        @Override
        public int[] coords() {
            return coordsI;
        }
    };

    private static int[] cornerSwapsCoords = new int[]{0, 0, 0, 2, 2, 2, 2, 0};
    private static int[] edgeSwapsCoords = new int[]{0, 1, 1, 2, 2, 1, 1, 0};
    private abstract static class Tetromino3 extends Tetromino {
        public Tetromino3() {
            super();
        }

        @Override
        public void rotateRight() {
            swap(cornerSwapsCoords);
            swap(edgeSwapsCoords);
        }

        @Override
        public int size() {
            return 3;
        }
    }

    private final static TetrisBlock blockJ = new TetrisBlock(0.0f, 0.0f, 1.0f, 1.0f);
    private final static int[] coordsJ = new int[]{0, 2, 0, 1, 1, 1, 2, 1};
    private final static Tetromino J = new Tetromino3() {
        @Override
        public TetrisBlock block() {
            return blockJ;
        }

        @Override
        public int[] coords() {
            return coordsJ;
        }
    };

    private final static TetrisBlock blockL = new TetrisBlock(1.0f, 0.5f, 0.0f, 1.0f);
    private final static int[] coordsL = new int[]{0, 1, 1, 1, 2, 1, 2, 2};
    private final static Tetromino L = new Tetromino3() {
        @Override
        public TetrisBlock block() {
            return blockL;
        }

        @Override
        public int[] coords() {
            return coordsL;
        }
    };

    private final static TetrisBlock blockS = new TetrisBlock(0.0f, 1.0f, 0.0f, 1.0f);
    private final static int[] coordsS = new int[]{0, 1, 1, 1, 1, 2, 2, 2};
    private final static Tetromino S = new Tetromino3() {
        @Override
        public TetrisBlock block() {
            return blockS;
        }

        @Override
        public int[] coords() {
            return coordsS;
        }
    };

    private final static TetrisBlock blockT = new TetrisBlock(0.5f, 0.0f, 1.0f, 1.0f);
    private final static int[] coordsT = new int[]{0, 1, 1, 1, 1, 2, 2, 1};
    private final static Tetromino T = new Tetromino3() {
        @Override
        public TetrisBlock block() {
            return blockT;
        }

        @Override
        public int[] coords() {
            return coordsT;
        }
    };

    private final static TetrisBlock blockZ = new TetrisBlock(1.0f, 0.0f, 0.0f, 1.0f);
    private final static int[] coordsZ = new int[]{0, 2, 1, 1, 1, 2, 2, 1};
    private final static Tetromino Z = new Tetromino3() {
        @Override
        public TetrisBlock block() {
            return blockZ;
        }

        @Override
        public int[] coords() {
            return coordsZ;
        }
    };

    private final static TetrisBlock blockO = new TetrisBlock(1.0f, 1.0f, 0.0f, 1.0f);
    private final static int[] coordsO = new int[] {0, 0, 0, 1, 1, 0, 1, 1};
    private final static Tetromino O = new Tetromino() {
        @Override
        protected void rotateRight() {
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public TetrisBlock block() {
            return blockO;
        }

        @Override
        public int[] coords() {
            return coordsO;
        }
    };
}
