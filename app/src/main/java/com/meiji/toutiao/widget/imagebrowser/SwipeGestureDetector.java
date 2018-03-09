package com.meiji.toutiao.widget.imagebrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by huang on 2/14/17.
 */

public class SwipeGestureDetector {

    public static final int DIRECTION_TOP_BOTTOM = 1;
    public static final int DIRECTION_LEFT_RIGHT = 4;
    private static final String TAG = "SwipeGestureDetector";
    private OnSwipeGestureListener listener;
    private int direction;
    private int touchSlop;
    private float initialMotionX, initialMotionY;
    private float lastMotionX, lastMotionY;
    private boolean isBeingDragged;

    public SwipeGestureDetector(Context context, @NonNull OnSwipeGestureListener listener) {
        this.listener = listener;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        float x = event.getRawX();
        float y = event.getRawY();

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
                    //direction horizon
                    direction = DIRECTION_LEFT_RIGHT;
                } else if (yDiff > touchSlop && yDiff > xDiff) {
                    isBeingDragged = true;
                    //direction vertical
                    direction = DIRECTION_TOP_BOTTOM;
                }
                break;
        }
        return isBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        float x = event.getRawX();
        float y = event.getRawY();

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
                    if (direction == DIRECTION_LEFT_RIGHT) {
                        listener.onSwipeLeftRight(deltaX, deltaY);
                    } else if (direction == DIRECTION_TOP_BOTTOM) {
                        listener.onSwipeTopBottom(deltaX, deltaY);
                    }
                } else {
                    final float xDiff = Math.abs(x - initialMotionX);
                    final float yDiff = Math.abs(y - initialMotionY);
                    if (xDiff > touchSlop && xDiff > yDiff) {
                        isBeingDragged = true;
                        //direction horizon
                        direction = DIRECTION_LEFT_RIGHT;
                    } else if (yDiff > touchSlop && yDiff > xDiff) {
                        isBeingDragged = true;
                        //direction vertical
                        direction = DIRECTION_TOP_BOTTOM;
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

    public interface OnSwipeGestureListener {
        void onSwipeTopBottom(float deltaX, float deltaY);

        void onSwipeLeftRight(float deltaX, float deltaY);

        void onFinish(int direction, float distanceX, float distanceY);
    }

}
