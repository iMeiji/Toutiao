package com.meiji.toutiao.widget.imagebrowser;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.meiji.toutiao.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Meiji on 2018/3/9.
 * reference <a href="https://github.com/mingdroid/SETransitionDemo"/>
 */

public class SwipeGestureDetector {

    public static final int DIRECTION_LEFT = 0x00;
    public static final int DIRECTION_RIGHT = 0x01;
    public static final int DIRECTION_TOP = 0x02;
    public static final int DIRECTION_BOTTOM = 0x03;
    private static final String TAG = "SwipeGestureDetector";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private final Handler handler = new Handler();
    private OnSwipeGestureListener listener;
    private int touchSlop;
    private float initialMotionX, initialMotionY;
    private float lastMotionX, lastMotionY;
    private boolean isBeingDragged;
    @Direction
    private int direction;

    public SwipeGestureDetector(Context context, @NonNull OnSwipeGestureListener listener) {
        this.listener = listener;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();

        // two fingers
        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            return true;
        }

        float x = event.getRawX();
        float y = event.getRawY();
        if (DEBUG)
            Log.d(TAG, "onInterceptTouchEvent: " + x + "-" + y);

        // Always take care of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the drag.
            reset(initialMotionX, initialMotionY);
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN) {
            if (isBeingDragged) {
                return true;
            }
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialMotionX = lastMotionX = x;
                initialMotionY = lastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float xDiff = Math.abs(x - initialMotionX);
                final float yDiff = Math.abs(y - initialMotionY);
                if (xDiff > touchSlop && xDiff > yDiff) {
                    isBeingDragged = true;
                    if (x - initialMotionX > 0) {
                        direction = DIRECTION_RIGHT;
                        if (DEBUG) Log.d(TAG, "onInterceptTouchEvent: RIGHT");
                    } else {
                        direction = DIRECTION_LEFT;
                        if (DEBUG) Log.d(TAG, "onInterceptTouchEvent: LEFT");
                    }
                } else if (yDiff > touchSlop && yDiff > xDiff) {
                    isBeingDragged = true;
                    if (y - initialMotionY > 0) {
                        direction = DIRECTION_BOTTOM;
                        if (DEBUG) Log.d(TAG, "onInterceptTouchEvent: BOTTOM");
                    } else {
                        direction = DIRECTION_TOP;
                        if (DEBUG) Log.d(TAG, "onInterceptTouchEvent: TOP");
                    }
                }
                break;
        }
        return isBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            return true;
        }

        float x = event.getRawX();
        float y = event.getRawY();
        if (DEBUG) Log.d(TAG, "onTouchEvent: " + x + "-" + y);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialMotionX = lastMotionX = x;
                initialMotionY = lastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = x - lastMotionX;
                final float deltaY = y - lastMotionY;
                lastMotionX = x;
                lastMotionY = y;
                if (isBeingDragged) {
                    if (direction == DIRECTION_LEFT) {
                        listener.onSwipeLeft(deltaX, deltaY);
                    } else if (direction == DIRECTION_RIGHT) {
                        listener.onSwipeRight(deltaX, deltaY);
                    } else if (direction == DIRECTION_TOP) {
                        listener.onSwipeTop(deltaX, deltaY);
                    } else if (direction == DIRECTION_BOTTOM) {
                        listener.onSwipeBottom(deltaX, deltaY);
                    }
                } else {
                    final float xDiff = Math.abs(x - initialMotionX);
                    final float yDiff = Math.abs(y - initialMotionY);
                    if (xDiff > touchSlop && xDiff > yDiff) {
                        isBeingDragged = true;
                        if (x - initialMotionX > 0) {
                            direction = DIRECTION_RIGHT;
                            if (DEBUG) Log.d(TAG, "onTouchEvent: RIGHT");
                        } else {
                            direction = DIRECTION_LEFT;
                            if (DEBUG) Log.d(TAG, "onTouchEvent: LEFT");
                        }
                    } else if (yDiff > touchSlop && yDiff > xDiff) {
                        isBeingDragged = true;
                        if (y - initialMotionY > 0) {
                            direction = DIRECTION_BOTTOM;
                            if (DEBUG) Log.d(TAG, "onTouchEvent: BOTTOM");
                        } else {
                            direction = DIRECTION_TOP;
                            if (DEBUG) Log.d(TAG, "onTouchEvent: TOP");
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                reset(x, y);
                break;
            case MotionEvent.ACTION_CANCEL:
                reset(x, y);
                break;
        }
        return true;
    }

    private void reset(float x, float y) {
        if (isBeingDragged) {
            listener.onFinish(direction, x - initialMotionX, y - initialMotionY);
        }
        isBeingDragged = false;
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    public interface OnSwipeGestureListener {
        void onSwipeLeft(float deltaX, float deltaY);

        void onSwipeRight(float deltaX, float deltaY);

        void onSwipeTop(float deltaX, float deltaY);

        void onSwipeBottom(float deltaX, float deltaY);

        void onFinish(@Direction int direction, float distanceX, float distanceY);
    }

}
