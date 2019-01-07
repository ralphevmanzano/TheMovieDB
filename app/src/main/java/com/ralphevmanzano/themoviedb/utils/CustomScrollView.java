package com.ralphevmanzano.themoviedb.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;
import timber.log.Timber;

public class CustomScrollView extends NestedScrollView {

    private boolean hasReachedBottom;
    private GestureDetector mGestureDetector;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY()));

        if (view instanceof ViewPager) {
            Timber.d("Oy Viewpager");
        }
        hasReachedBottom = (diff == 0);

        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Timber.d("CustomScrollView: %s", (super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev)));

        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            return (Math.abs(distanceY) > Math.abs(distanceX)) || !hasReachedBottom;
        }
    }
}
