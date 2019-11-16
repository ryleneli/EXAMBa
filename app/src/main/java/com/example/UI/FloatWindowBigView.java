package com.example.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

public class FloatWindowBigView extends RelativeLayout {
    private static String TAG = "FloatWindowViewBig";
/**	 * 记录大悬浮窗的宽度	 */
    public static int viewWidth;
    /**	 * 记录大悬浮窗的高度	 */
    public static int viewHeight;
    private ImageButton enter_btn,exit_btn;

    private List<ImageButton> buttons = new ArrayList<>();

    public FloatWindowBigView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.floatwindow_big, this);
        View view = findViewById(R.id.view_floatwindowbig);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        enter_btn = (ImageButton) findViewById(R.id.enter_test);
        exit_btn = (ImageButton) findViewById(R.id.exit_test);
        buttons.add(enter_btn);
        buttons.add(exit_btn);
        //if (!isMenuOpen) {
            showOpenAnim(20);
            //imgPublish.setImageResource(R.mipmap.publish_select);
       // }else {
      //      showCloseAnim(20);
            //imgPublish.setImageResource(R.mipmap.fabu);
       // }
        exit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
            /*FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.removeSmallWindow(context);
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);*/
            }
        });
        enter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                /*FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);*/
            }
        });
    }


    //打开扇形菜单的属性动画， dp为半径长度

    private void showOpenAnim(int dp) {
        enter_btn.setVisibility(View.VISIBLE);
        exit_btn.setVisibility(View.VISIBLE);
        //for循环来开始小图标的出现动画
        //float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < buttons.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            int dp_tv1_X = 30;
            int px_tv1_X = dip2px(dp_tv1_X);
            int dp_tv2_X = 70;
            int px_tv2_X = dip2px(dp_tv2_X);
            int pxTemp[] = {px_tv1_X,px_tv2_X};
            set.playTogether(
                    ObjectAnimator.ofFloat(buttons.get(i),"translationY",(float)0,pxTemp[i])
                    //, ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)0,28)
                    , ObjectAnimator.ofFloat(buttons.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100);
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    //菜单状态置打开
                   // isMenuOpen = true;
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        //转动加号大图标本身45°
        //ObjectAnimator rotate = ObjectAnimator.ofFloat(menu, "rotation", 0, 90).setDuration(300);
        //rotate.setInterpolator(new BounceInterpolator());
        //rotate.start();
    }
    //关闭扇形菜单的属性动画，参数与打开时相反

    private void showCloseAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < buttons.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            int dp_tv1_X = 40;
            int px_tv1_X = dip2px(dp_tv1_X);
            int dp_tv2_X = 70;
            int px_tv2_X = dip2px(dp_tv2_X);
            int pxTemp[] = {px_tv1_X,px_tv2_X};
            set.playTogether(
                    //ObjectAnimator.ofFloat(imageViews.get(i),"translationY",pxTemp[i],(float)0),
                    //, ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)0,28)
                    ObjectAnimator.ofFloat(buttons.get(i), "alpha", 1, 0)
            );

//      set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(500);
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    enter_btn.setVisibility(View.GONE);
                    exit_btn.setVisibility(View.GONE);
                    //菜单状态置关闭
                    //isMenuOpen = false;
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        //转动加号大图标本身45°
        //ObjectAnimator rotate = ObjectAnimator.ofFloat(menu, "rotation", 0, 90).setDuration(300);
        //rotate.setInterpolator(new BounceInterpolator());
        //rotate.start();
    }
    private int dip2px(int value) {
        float density = getResources().getDisplayMetrics().density;
        Log.i(TAG,"==========="+density);

        float abs = density * value+0.5f;
        Log.i(TAG,"==========="+abs);
        return (int) (density * value + 0.5f);
    }

}
