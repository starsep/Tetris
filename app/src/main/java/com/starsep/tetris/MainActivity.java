package com.starsep.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private TetrisGLSurfaceView surfaceView;
    private Handler handler;
    private Runnable renderRun;

    //private final static String DEBUG_TAG = "MainActivity:";

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void setMaximumBrightness() {
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = 1F;
        getWindow().setAttributes(layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMaximumBrightness();

        ResourceReader.assign(this);
        surfaceView = new TetrisGLSurfaceView(this);
        //setContentView(surfaceView);
        setContentView(R.layout.main_activity);

        /*renderRun = new Runnable() {
            @Override
            public void run() {
                surfaceView.requestRender();
                handler.postDelayed(this, 30);
            }
        };

        handler = new Handler();
        handler.postDelayed(renderRun, 30);*/

    }
}

