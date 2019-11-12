package com.example.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.internal.Utils;

public class FloatWindow extends Activity implements View.OnClickListener {

    private static String TAG = "FloatWindow";
    private LinearLayout menu;
    private TextView enterTest;
    private TextView exitTest;
    private boolean isMenuOpen = false;
    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_window);
        menu = (LinearLayout) findViewById(R.id.menu_floatWindow);
        enterTest = (TextView) findViewById(R.id.enter_test);
        exitTest = (TextView) findViewById(R.id.exit_test);
        textViews.add(enterTest);
        textViews.add(exitTest);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_floatWindow:
                if (!isMenuOpen) {
                    showOpenAnim(20);
                    //imgPublish.setImageResource(R.mipmap.publish_select);
                }else {
                    showCloseAnim(20);
                    //imgPublish.setImageResource(R.mipmap.fabu);
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        textViews.clear();
        super.onDestroy();
    }


    //打开扇形菜单的属性动画， dp为半径长度

    private void showOpenAnim(int dp) {
        enterTest.setVisibility(View.VISIBLE);
        exitTest.setVisibility(View.VISIBLE);
        //for循环来开始小图标的出现动画
        //float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < textViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            int dp_tv1_X = 43;
            int px_tv1_X = dip2px(dp_tv1_X);
            int dp_tv2_X = 76;
            int px_tv2_X = dip2px(dp_tv2_X);
            int pxTemp[] = {px_tv1_X,px_tv2_X};
            set.playTogether(
                    ObjectAnimator.ofFloat(textViews.get(i),"translationX",(float)0,pxTemp[i])
                   // , ObjectAnimator.ofFloat(textViews.get(i),"translationY",(float)0,-140+i*1*100)
                    , ObjectAnimator.ofFloat(textViews.get(i), "alpha", 0, 1).setDuration(2000)
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
                    isMenuOpen = true;
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
        for (int i = 0; i < textViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(textViews.get(i), "translationX", (float) -40-i*1*100, (float)0),
                    ObjectAnimator.ofFloat(textViews.get(i), "translationY", (float) -140+i*1*100, (float) 0),
                    ObjectAnimator.ofFloat(textViews.get(i), "alpha", 1, 0).setDuration(2000)
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
                    enterTest.setVisibility(View.GONE);
                    exitTest.setVisibility(View.GONE);
                    //菜单状态置关闭
                    isMenuOpen = false;
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