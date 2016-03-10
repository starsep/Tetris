package com.starsep.tetris;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TetrisGLSurfaceView extends GLSurfaceView {

    private GestureDetectorCompat gestureDetector;
    private GestureListener gestureListener;
    private int mAspectRatioWidth = 1;
    private int mAspectRatioHeight = 2;

    public TetrisGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(TetrisRenderer.renderer());
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        gestureListener = new GestureListener();
        gestureDetector = new GestureDetectorCompat(context, gestureListener);
        gestureDetector.setOnDoubleTapListener(gestureListener);
    }

    public TetrisGLSurfaceView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return true;
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
            //Log.d("LEL", "42");
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
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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
            requestRender();
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
            requestRender();
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight = originalWidth * mAspectRatioHeight / mAspectRatioWidth;
        int finalWidth, finalHeight;
        if (calculatedHeight > originalHeight) {
            finalWidth = originalHeight * mAspectRatioWidth / mAspectRatioHeight;
            finalHeight = originalHeight;
        } else {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }

}
