package com.example;

import android.content.Context;
import android.util.Log;

public class dpTopx {
    public static int dip2px(Context context,int value) {
        float density = context.getResources().getDisplayMetrics().density;
        //Log.i(TAG,"==========="+density);

        float abs = density * value+0.5f;
        //Log.i(TAG,"==========="+abs);
        return (int) (density * value + 0.5f);
    }
}
