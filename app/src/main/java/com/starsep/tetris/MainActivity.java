package com.starsep.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private TetrisGLSurfaceView surfaceView;
    private GestureDetectorCompat gestureDetector;
    private GestureListener gestureListener;
    private Handler handler;
    private Runnable renderRun;

    //private final static String DEBUG_TAG = "MainActivity:";

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && surfaceView != null) {
            surfaceView.setSystemUiVisibility(
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
        setContentView(surfaceView);

        gestureListener = new GestureListener();
        gestureDetector = new GestureDetectorCompat(this, gestureListener);
        gestureDetector.setOnDoubleTapListener(gestureListener);

        renderRun = new Runnable() {
            @Override
            public void run() {
                surfaceView.requestRender();
                handler.postDelayed(this, 30);
            }
        };

        handler = new Handler();
        handler.postDelayed(renderRun, 30);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    private final class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = event2.getY() - event1.getY();
                float diffX = event2.getX() - event1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            TetrisBoard.board().right();
                        } else {
                            TetrisBoard.board().left();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        TetrisBoard.board().down();
                    } else {
                        TetrisBoard.board().top();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            surfaceView.requestRender();
            return result;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            //Log.d(DEBUG_TAG, "onScroll: X: " + String.valueOf(distanceX) + "Y: " + String.valueOf(distanceY));
            return false;
        }

        @Override
        public void onShowPress(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
            TetrisBoard.board().onClick();
            surfaceView.requestRender();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            //Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
            return true;
        }
    }

}

