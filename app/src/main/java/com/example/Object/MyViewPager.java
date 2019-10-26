package com.example.Object;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DELL on 2019/10/5.
 */

public class MyViewPager extends ViewPager {

    private static final String TAG = "xujun";
    public boolean isSlide = true;
    public void setSlide(boolean slide) {
        isSlide = slide;
    }
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSlide;
    }
}
