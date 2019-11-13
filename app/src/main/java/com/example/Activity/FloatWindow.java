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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.internal.Utils;

public class FloatWindow extends Activity implements View.OnClickListener {

    private static String TAG = "FloatWindow";
    private RelativeLayout menu;
    private ImageView purple_1,purple_2;
    private TextView enterTest;
    private TextView exitTest;
    private boolean isMenuOpen = false;
    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_window);
        menu = (RelativeLayout) findViewById(R.id.menu_floatWindow);
        purple_1 = (ImageView) findViewById(R.id.purple1);
        purple_2 = (ImageView) findViewById(R.id.purple2);
        enterTest = (TextView) findViewById(R.id.enter_test);
        exitTest = (TextView) findViewById(R.id.exit_test);
        animOfPurple();
        textViews.add(enterTest);
        textViews.add(exitTest);
        menu.setOnClickListener(this);
        purple_1.setVisibility(View.VISIBLE);
        purple_2.setVisibility(View.VISIBLE);
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
    private void animOfPurple()
    {
        Log.i(TAG,"puple");

        AnimatorSet set = new AnimatorSet();
        AnimatorSet set_puple1 = new AnimatorSet();
        AnimatorSet set_puple2 = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(purple_1, "alpha",0f,1.0f,0.8f,0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(purple_1, "scaleX", 0f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(purple_1, "scaleY", 0f, 1.0f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(purple_1, "translationY", 0, -dip2px(70));
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(purple_2, "scaleX", 0f, 1.0f);
        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(purple_2, "scaleY", 0f, 1.0f);
        ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(purple_2, "translationY", 0, -dip2px(70));
        ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(purple_2, "alpha",0f,1.0f,0.8f,0f);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator2.setRepeatCount(-1);
        objectAnimator3.setRepeatCount(-1);
        objectAnimator4.setRepeatCount(-1);

        objectAnimator5.setRepeatCount(-1);
        objectAnimator6.setRepeatCount(-1);
        objectAnimator7.setRepeatCount(-1);
        objectAnimator8.setRepeatCount(-1);
        set_puple1.playTogether(objectAnimator1,objectAnimator2,objectAnimator3,objectAnimator4);
        set_puple1.setStartDelay(500);
        set_puple2.playTogether(objectAnimator5,objectAnimator6,objectAnimator7,objectAnimator8);
        set.play(set_puple1).with(set_puple2);
        set.setDuration(4000);
        set.start();

        Log.i(TAG,"puple==========");
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