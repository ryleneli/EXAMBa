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


    private float mDownPosX = 0;
    private float mDownPosY = 0;


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
// 这里是否拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截

                if (deltaX > deltaY) {// 左右滑动不拦截
                    return true;
                }

        }
        return super.onInterceptTouchEvent(ev);
    }
}
