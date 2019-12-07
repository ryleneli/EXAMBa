package com.example.Presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.Activity.SplashActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    public void webTest()
    {
        try{
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
        }
    }
}
