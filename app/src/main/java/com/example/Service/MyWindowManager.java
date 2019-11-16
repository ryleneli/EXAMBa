package com.example.Service;


import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.util.Log;
import android.os.Build;

import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Activity.ExamActivity;
import com.example.Activity.LoginActivity;
import com.example.UI.FloatWindowView;

public class MyWindowManager {

    private static String TAG = "MyWindowManager";
    private static Context context;
    private static ExamActivity examActivity;
    private static FloatWindowView floatWindow;                 //floatWindowView的实例
    private static WindowManager.LayoutParams floatWindowParams;//floatWindowView的参数
    private static WindowManager mWindowManager;                //floatWindow管理
    private static Chronometer chronometer;

    /**
     * 创建悬浮窗，初始位置为屏幕的下方居中。
     * 必须为应用程序的Context.
     */
    public static void initWindowManager(Context context1,ExamActivity examActivity1)
    {
       context = context1;
       examActivity = examActivity1;
    }
    public static void createfloatWindow(Context context) {
        //calculation = calculation01;
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        //Log.d(TAG, " lrll screenWidth="+screenWidth);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (floatWindow == null) {
            floatWindow = new FloatWindowView(context);
            Log.d(TAG, "lrl new floatWindow");
            // 设置windowManager.LayoutParam
            if (floatWindowParams == null) {
                Log.d(TAG, "lrl set floatWindowparams");
                floatWindowParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= 26) { //windowManager的悬浮窗口的不同Android版本适配
                    floatWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }
                else {
                    floatWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                floatWindowParams.format = PixelFormat.RGBA_8888;
                floatWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                floatWindowParams.width = FloatWindowView.viewWidth;//窗口大小
                floatWindowParams.height = FloatWindowView.viewHeight;
                floatWindowParams.x = screenWidth/ 2 - floatWindowParams.width/2;//窗口位置
                Log.d(TAG, " lrll floatWindowParams.x====="+floatWindowParams.x+"view width"+floatWindowParams.width);
                floatWindowParams.y =screenHeight * 4 / 5;
                Log.d(TAG, " lrll floatWindowParams.y====="+floatWindowParams.y+"view height"+floatWindowParams.height);
            }
            /**将悬浮窗参数floatWindowParams传到FloatWindowView以使用（计算移动位置）*/
            floatWindow.setParams(floatWindowParams);

            // 将悬浮窗控件添加到WindowManager
            windowManager.addView(floatWindow, floatWindowParams);//WindowManager的addView方法有两个参数，一个是需要加入的控件对象，另一个参数是WindowManager.LayoutParam对象
            Log.d(TAG, "lrl add windowview");
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context
     * 必须为应用程序的Context.
     */

    public static void removefloatWindow(Context context) {
        Log.d(TAG, "lrl enter remove window");
        if (floatWindow != null) {
            Log.d(TAG, "lrl ready to remove window");
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(floatWindow);
            floatWindow = null;
        }
    }
    /**
     * 更新悬浮窗内TextView信息
     *
     */
/*
    public static void setTimer() {
        floatWindow.timerStart(examActivity.chronometer);
        Log.d(TAG, " enter set timer ===========");
    }*/

    /**
     * 是否有悬浮窗显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static void update(int time) {
        floatWindow.setText(time);
        //valueofFPS.setText("FPS : "+fps);
    }
    public static boolean isWindowShowing() {
        return floatWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     * 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
