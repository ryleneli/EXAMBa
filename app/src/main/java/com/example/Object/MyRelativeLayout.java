package com.example.Object;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.example.DisPlayUtils;

/**
 * 作者：willkong on 2017/5/8.
 * QQ号：547860818
 * 作用：解决控件触摸事件和点击事件的冲突
 */

public class MyRelativeLayout extends RelativeLayout {
    private static String TAG = "MyRelativeLayout";
    public MyRelativeLayout(Context context) {
        this(context, null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initView() {

    }

    private boolean mScrolling;
    private float touchDownX;
    private float touchDownY;

    //拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " lrl enter ACTION_DOWN");
                touchDownX = event.getX();
                touchDownY = event.getY();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(touchDownX - event.getX()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()||Math.abs(touchDownY - event.getY()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }
/*
    float x2 = 0;
    float y2 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;//消费触摸事件
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //左滑
                if (touchDownX - x2 > DisPlayUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onRightToLeftSlide();
                    }
                }
                //右滑
                if (touchDownX - x2 < -DisPlayUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onLeftToRightSlide();
                    }
                }
                //上滑
                if (touchDownY - y2 > DisPlayUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onDownToUpSlide();
                    }
                }
                //下滑
                if (touchDownY - y2 < -DisPlayUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onUpToDownSlide();
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }
*/
    private OnSlideListener mOnSlideListener;

    public OnSlideListener getOnSlideListener() {
        return mOnSlideListener;
    }

    public void setmSetOnSlideListener(OnSlideListener mOnSlideListener) {
        this.mOnSlideListener = mOnSlideListener;
    }

    public interface OnSlideListener{
        void onRightToLeftSlide();
        void onLeftToRightSlide();
        void onUpToDownSlide();
        void onDownToUpSlide();
    }

}
