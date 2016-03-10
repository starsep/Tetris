package com.starsep.tetris;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TetrisRenderer implements GLSurfaceView.Renderer {
    private static TetrisRenderer instance = new TetrisRenderer();
    private static final String TAG = "TetrisRenderer";

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] VPMatrix = new float[16];
    private final float[] ProjectionMatrix = new float[16];
    private final float[] ViewMatrix = new float[16];

    private TetrisRenderer() {
        super();
    }

    private TetrisBoard board;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.2f, 0.1f, 1.0f);
        board = TetrisBoard.board();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(ProjectionMatrix, 0, -ratio, ratio, -1, 1, 2, 7);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        //Matrix.setIdentityM(ViewMatrix, 0);
        //Matrix.setLookAtM(ViewMatrix, 0, 0, 0, 0, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        //Matrix.multiplyMM(VPMatrix, 0, ProjectionMatrix, 0, ViewMatrix, 0);

        Matrix.setIdentityM(VPMatrix, 0);
        Matrix.orthoM(VPMatrix, 0, 0.0f, 900f, 0, 1600f, -1.0f, 1.0f);
        board.draw(VPMatrix);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    public static void debugMatrix(float[] matrix) {
        if (matrix.length != 16) {
            throw new IllegalArgumentException();
        }
        Log.d(TAG, "MATRIX BEGIN");
        for (int i = 0; i < 4; i++) {
            String line = "";
            for (int j = 0; j < 4; j++) {
                line += String.valueOf(matrix[4 * i + j]) + " ";
            }
            Log.d(TAG, line);
        }
        Log.d(TAG, "MATRIX END");
    }

    public static GLSurfaceView.Renderer renderer() {
        return instance;
    }
}
