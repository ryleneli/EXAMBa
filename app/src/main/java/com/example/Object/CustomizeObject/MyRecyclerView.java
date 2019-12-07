package com.example.Object.CustomizeObject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by 15151 on 2019/10/27.
 */

public class MyRecyclerView extends RecyclerView {
    int lastX = 0;
    int lastY = 0;
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    int move_x,move_y;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        /*--------解决垂直RecyclerView嵌套水平RecyclerView横向滑动不流畅问题 start --------*/
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                // Log.i("dispatchTouchEvent", "dealtX:=" + dealtX);
                // Log.i("dispatchTouchEvent", "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                ViewParent parent =this;
                while(!((parent = parent.getParent()) instanceof ViewPager));// 循环查找viewPager
                parent.requestDisallowInterceptTouchEvent(true);
                if (dealtX >= dealtY) {
                    parent.requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        /*--------解决垂直RecyclerView嵌套水平RecyclerView横向滑动不流畅问题 end --------*/

        return super.dispatchTouchEvent(ev);
    }
}
