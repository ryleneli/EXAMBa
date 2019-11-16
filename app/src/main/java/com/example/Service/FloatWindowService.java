package com.example.Service;


import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.util.Log;
import android.os.Message;

import android.os.Bundle;

import com.example.Activity.ExamActivity;
/**
 * Created by rylene_li on 2019/1/21.
 */

/****
 #@Service
 #1. 启动时 create floating window
 #2. 收到 update message时，更新floating window的FPS数值
 #3. 退出时 remove floating window
 -----------------------------
 #@TimerTask
 #1. 定时计算FPS
 #2. post update message

 ****/

public class FloatWindowService extends Service {

    private static String TAG = "FloatWindowService";
    //private Handler handler = new Handler();
    private int time;
    private static final int COMPLETED = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "lrl onStartCommand");
        Log.d(TAG, "lrl ready to create float window");
        MyWindowManager.createfloatWindow(getApplicationContext());
        time = intent.getIntExtra("TIME",0);

        new Thread(new Runnable() {
            // TODO Auto-generated method stub

            @Override
            public void run() {
                while (true) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    mhandler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "lrl ondestroy");
        super.onDestroy();
        MyWindowManager.removefloatWindow(getApplicationContext());
        Log.d(TAG, "lrl done remove float window");
    }

    /***************定时器中更新fps数据**********************/

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                MyWindowManager.update((time++));
            }
        }
    };

}

