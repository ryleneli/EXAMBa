package com.example.Service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import android.util.Log;
import android.os.Message;

import com.example.Manager.MyWindowManager;
/**
 * Created by rylene_li on 2019/1/21.
 */

/****
 #@Service
 #1. 启动时 create floating window
 #2. 收到 update message时，更新floating window的FPS数值
 #3. 退出时 remove floating window
 -----------------------------
 ****/

public class FloatWindowService extends Service {

    private static String TAG = "FloatWindowService";
    //private Handler handler = new Handler();
    private TimerThread mThread;
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
        //mThread = new TimerThread();
        start();
/*        new Thread(new Runnable() {
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
        }).start();匿名内部类实现方法*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "lrl ondestroy");
        super.onDestroy();
        //mhandler.sendEmptyMessage(0);
        stop();
        MyWindowManager.removefloatWindow(getApplicationContext());
        MyWindowManager.removeBigWindow(getApplicationContext());
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
    private class TimerThread extends Thread{
        boolean isRunning = false;
        @Override
        public void run() {
            super.run();
            while (isRunning) {
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
    }
    // 开启定时线程
    private void start(){
        if(mThread == null){
            mThread = new TimerThread();
            mThread.isRunning = true;
            mThread.start();
        }
    }
    // 停止定时线程
    private void stop() {
        if (mThread != null) {
            mThread.isRunning = false;
            mThread.interrupt();
            mThread = null;
        }
    }
}

