package com.example.Service;


import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.util.Log;
import android.os.Build;

import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.Activity.ExamActivity;
import com.example.Activity.LoginActivity;
import com.example.UI.FloatWindowBigView;
import com.example.UI.FloatWindowView;
import com.example.dpTopx;

import java.lang.reflect.Field;

public class MyWindowManager {

    private static String TAG = "MyWindowManager";

    private static FloatWindowView floatWindow;                 //floatWindowView的实例
    private static FloatWindowBigView floatWindowBig;                 //floatWindowView的实例
    public static WindowManager.LayoutParams floatWindowParams;//floatWindowView的参数
    private static WindowManager mWindowManager;                //floatWindow管理
    public static WindowManager.LayoutParams floatWindowParamsBig;//floatWindowView的参数
    private static ImageView pangxie_iv;
    public static int smallWindow_w;    //小窗口宽高
    public static int smallWindow_h;
    public static int statusBarHeight;                 //记录系统状态栏的高度
    /**
     * 创建悬浮窗，初始位置为屏幕的下方居中。
     * 必须为应用程序的Context.
     */

    public static void createfloatWindow(Context context) {
        //calculation = calculation01;
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        //Log.d(TAG, " lrll screenWidth="+screenWidth);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (floatWindow == null) {
            floatWindow = new FloatWindowView(context);
            pangxie_iv = floatWindow.getPangxie();
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
                //Log.d(TAG, " lrll Gravity.LEFT ====="+dpTopx.dip2px(context,Gravity.LEFT ));
                //Log.d(TAG, " lrll Gravity.LEFT ====="+dpTopx.dip2px(context,Gravity.TOP ));
                floatWindowParams.width = FloatWindowView.viewWidth;//窗口大小
                smallWindow_w = floatWindowParams.width;
                floatWindowParams.height = FloatWindowView.viewHeight;
                smallWindow_h = floatWindowParams.height;
                floatWindowParams.x =screenWidth/ 2 - floatWindowParams.width/2;//窗口位置
                //bigWindow_w = floatWindowParams.x;
                Log.d(TAG, " lrll floatWindowParams.x====="+floatWindowParams.x+"view width"+floatWindowParams.width);
                floatWindowParams.y =screenHeight * 3 / 5 - floatWindowParams.height/2;
                //bigWindow_h = floatWindowParams.y;
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
            floatWindowParams = null;
        }
    }
    /**
     * 更新悬浮窗内TextView信息
     *
     */

    public static void update(int time) {
        floatWindow.setText(time);
        //valueofFPS.setText("FPS : "+fps);
    }
    public static boolean isWindowShowing() {
        return floatWindow != null;
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static FloatWindowBigView createWindow(Context context, float bigWindow_x, float bigWindow_y, float xInScreen_1) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (floatWindowBig == null) {
            floatWindowBig = new FloatWindowBigView(context);
            if (floatWindowParamsBig == null) {
                floatWindowParamsBig = new WindowManager.LayoutParams();
                floatWindowParamsBig.width = floatWindowBig.viewWidth;
                floatWindowParamsBig.height = floatWindowBig.viewHeight;
                //创建悬浮窗1时设置初始位置计算参考FloatWindowView中注释，左侧计算，右侧计算
                boolean inRight = false;
                if (windowManager.getDefaultDisplay().getWidth()-floatWindowParams.width-floatWindowParams.x>floatWindowParamsBig.width) {
                    floatWindowParamsBig.x = (int)bigWindow_x + smallWindow_w;
                }
                else
                {
                    floatWindowParamsBig.x = (int)bigWindow_x - floatWindowParamsBig.width;
                }
                floatWindowParamsBig.y = (int)bigWindow_y +smallWindow_h-floatWindowParamsBig.height;
                floatWindowParamsBig.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                floatWindowParamsBig.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                floatWindowParamsBig.format = PixelFormat.RGBA_8888;
                floatWindowParamsBig.gravity = Gravity.LEFT | Gravity.TOP;

            }
            floatWindowBig.setBigParams(floatWindowParamsBig);
            windowManager.addView(floatWindowBig, floatWindowParamsBig);
        }
        return floatWindowBig;
    }
    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (floatWindowBig != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(floatWindowBig);
            floatWindowBig = null;
            floatWindowParamsBig = null;
        }
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



    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
