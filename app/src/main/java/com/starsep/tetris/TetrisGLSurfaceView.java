package com.starsep.tetris;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class TetrisGLSurfaceView extends GLSurfaceView {
    private final TetrisRenderer renderer = new TetrisRenderer();

    public TetrisGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View unused) {
                renderer.onClick();
            }
        });
    }
}
