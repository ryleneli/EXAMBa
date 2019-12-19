package com.example.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Constant;
import com.example.Presenter.SplashPresenter;
import com.example.testsys.R;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by DELL on 2019/10/home1.
 */

public class SplashActivity extends Activity {

    private static String TAG = "SplashActivity";
    private RelativeLayout relativeLayout;
    private SplashPresenter splashPresenter;
    private TextView textView1,textView2;
    public View view;
    public Animator animReveal;
    float startRadius;
    float endRadius;
    final int[] location = new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        doAnimator(textView1);
        doAnimator(textView2);
        relativeLayout = (RelativeLayout)findViewById(R.id.logolayout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//判断横竖屏状态
        splashPresenter = new SplashPresenter(this,this);
        sendRequestWithOkHttp();


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(timerTask,3000);
    }
    private void doAnimator(TextView textView)
    {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator[] items = new ObjectAnimator[]{
                ObjectAnimator.ofFloat(textView, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(textView, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(textView, "rotationX", 0f, 360f),
        };
        set.playTogether(items);
        set.setDuration(2000).start();
    }
    private void addViewAnimator()
    {
        view = this.getWindow().getDecorView();
        view.getLocationInWindow(location);
        startRadius = 0f;
        endRadius = view.getHeight();
        animReveal = ViewAnimationUtils.createCircularReveal(view,
                location[0] + view.getWidth()/2,
                location[1] + view.getHeight()/2,
                startRadius,
                endRadius
        );
        animReveal.setDuration(1000);
        animReveal.setInterpolator(new LinearInterpolator());
        animReveal.start();
    }


    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                splashPresenter.webTest();
            }
        }).start();
    }
    public void setSplashImage(final String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (responseData) {
                    case "1":
                    { relativeLayout.setBackgroundResource(R.drawable.splash_bg3);addViewAnimator();}
                    break;
                    case "2":
                    { relativeLayout.setBackgroundResource(R.drawable.splash_bg2);addViewAnimator();}
                    break;
                    case "3":
                    { relativeLayout.setBackgroundResource(R.drawable.splash_bg);addViewAnimator();}
                    break;
                    default:
                        break;
                }
            }
        });
    }


}


//其他方法，可以参考下
/**
 * public class WelcomeActivity extends AppCompatActivity {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//满屏显示
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_welcome);

          handler.sendEmptyMessageDelayed(home1,2000);
      }

      private Handler handler = new Handler(new Handler.Callback() {
          @Override
          public boolean handleMessage(Message message) {
              if (message.what == home1){
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

              }
              return false;
          }
      });

      @Override
      public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_BACK){
              return false;
          }
          return false;
      }
  }
 */
/**
 *      @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
         this.requestWindowFeature(Window.FEATURE_NO_TITLE);

         setContentView(R.layout.activity_splash);

         new Handler().postDelayed(new Runnable() {
             public void run() {
                 Intent intent = new Intent();
                 intent.setClass(SplashActivity.this, MainActivity.class);
                 startActivity(intent);
                 finish();
             }
         }, 1000 * 3);
     }

     @Override
     public void onBackPressed() {
         //super.onBackPressed();
     }
 */
