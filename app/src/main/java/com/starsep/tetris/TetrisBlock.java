package com.starsep.tetris;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class TetrisBlock {
    private static FloatBuffer vertexBuffer;
    private static ShortBuffer drawListBuffer;
    private static int mProgram = -1;
    private static int mPositionHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final float squareCoords[] = {
            -1.0f, 1.0f, 0.0f,   // top left
            -1.0f, -1.0f, 0.0f,   // bottom left
            1.0f, -1.0f, 0.0f,   // bottom right
            1.0f, 1.0f, 0.0f}; // top right

    private static final short drawOrder[] = {0, 1, 2, 0, 2, 3}; // order to draw vertices
    private static int mColorHandle;
    private static int mMVPMatrixHandle;

    private final float[] color;

    private static void initGL() {
        if (mProgram != -1) {
            return;
        }
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = TetrisRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                ResourceReader.get(R.raw.normal_vertex));
        int fragmentShader = TetrisRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                ResourceReader.get(R.raw.normal_fragment));

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public TetrisBlock(float red, float green, float blue, float alpha) {
        color = new float[]{red, green, blue, alpha};
        initGL();
    }

    public static void initDraw() {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        int vertexStride = COORDS_PER_VERTEX * 4;
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);


        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        TetrisRenderer.checkGlError("glGetUniformLocation");
    }

    public void draw(float[] mvpMatrix) {
        //initDraw();

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        //TetrisRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        //endDraw();
    }

    public static void endDraw() {
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    private static class TetrisBlockNoDraw extends TetrisBlock {
        public TetrisBlockNoDraw() {
            super(0.0f, 0.0f, 0.0f, 0.0f);
        }

        @Override
        public void draw(float[] unused) {
        }
    }

    private final static TetrisBlock wall = new TetrisBlockNoDraw();

    public static TetrisBlock wall() {
        return wall;
    }

    private final static TetrisBlock empty = new TetrisBlockNoDraw();

    public static TetrisBlock empty() {
        return empty;
    }

    public boolean isEmpty() {
        return this == empty;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isWall() {
        return this == wall;
    }
}
