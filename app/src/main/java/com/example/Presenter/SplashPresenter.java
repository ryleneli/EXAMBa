package com.example.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.example.Activity.MainActivity;
import com.example.Activity.SplashActivity;
import com.example.Object.Bean.User;
import com.example.webConnect.NetWorks;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observer;

/**
 * com.example.Presenter
 * Created by Ryleneli 15151
 * 2019/12/7
 */
public class SplashPresenter {
    private static String TAG = "SplashPresenter";
    private Context context;
    private SplashActivity splashActivity;
    public SplashPresenter(Context context, SplashActivity splashActivity){
        this.splashActivity=splashActivity;
        this.context=context;
    }
//okhttp实现练习
/*        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://122.51.93.183:8080/webService/WebService?action=connectedTest")
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            if (responseData.equals("connected"))
            {
                request = new Request.Builder()
                        .url("http://122.51.93.183:8080/webService/WebService?action=getTitle")
                        .build();
                response = client.newCall(request).execute();
                responseData = response.body().string();
                splashActivity.setSplashImage(responseData);
            }
            else
                Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //登录用户信息
    public void webTest() {
        ConnectivityManager cwjManager=(ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cwjManager.getActiveNetworkInfo()!=null){
            if(cwjManager.getActiveNetworkInfo().isAvailable()){
                serviceConnectTest ();
            }else{
                Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
        }


    }
    public void serviceConnectTest () {
        NetWorks.connectTest("connectedTest", new Observer<String>() {
            @Override
            public void onCompleted() {
            }
         @Override
            public void onError(Throwable e) {
                Log.i(TAG, "connected error :" + e);
                Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
            }
         @Override
            public void onNext(String info) {
                Log.i(TAG, "connected info :" + info);
                if (info.equals("connected")) {
                    getServiceTitle ();
                } else {
                    Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getServiceTitle () {
        NetWorks.getTitle("getTitle", new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "connected error :" + e);
                Toast.makeText(splashActivity, "server is unconnected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String info) {
                Log.i(TAG, "random_title info :" + info);
                splashActivity.setSplashImage(info);
            }
        });
    }

}
