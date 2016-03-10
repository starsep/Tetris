package com.starsep.tetris;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class TetrisGLSurfaceView extends GLSurfaceView {
    public TetrisGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(TetrisRenderer.renderer());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
