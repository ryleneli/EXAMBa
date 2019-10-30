package com.example.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.itemClickListener;
import com.example.testsys.R;

/**
 * Created by DELL on 2019/10/3.
 */

public class TitleBarView extends RelativeLayout {

    private static String TAG = "TitleBarView";

    public ImageButton titleBack,titleAnswer;
    public TextView titleText;
    public Chronometer chronometer;
    private ImageView timer;


    public TitleBarView(Context context) {
        super(context);
        init(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        //Log.i(TAG,"LRL ENTER INIT");
        View view = LayoutInflater.from(context).inflate(R.layout.titlebar_view, this);//获取布局文件

        titleText = findViewById(R.id.title_text);//获取布局文件控件
        titleBack = findViewById(R.id.title_back_icon);
        titleAnswer = findViewById(R.id.title_answer);
        chronometer = findViewById(R.id.title_exam_chronometer);
        timer = findViewById(R.id.timer);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        //Log.i(TAG,"LRL ENTER INIT and obtain is ok");
        if (attributes != null) {
            //Log.i(TAG,"LRL ENTER INIT and enter if");
            //返回键
            boolean titleBackVisible = attributes.getBoolean(R.styleable.TitleBarView_title_back_icon_visible, false);
            if (titleBackVisible) {
                titleBack.setVisibility(View.VISIBLE);
            } else {
                titleBack.setVisibility(View.INVISIBLE);
            }
            //text
            String text = attributes.getString(R.styleable.TitleBarView_title_text_text);
            titleText.setText(text);
            //int textcolor = attributes.getColor(R.styleable.TitleBarView_title_text_text, Color.WHITE);
            //titleText.setTextColor(textcolor);


            //answer图标
            boolean titleAnswerVisible = attributes.getBoolean(R.styleable.TitleBarView_title_answer_visible, false);
            if (titleAnswerVisible) {
                titleAnswer.setVisibility(View.VISIBLE);
            } else {
                titleAnswer.setVisibility(View.INVISIBLE);
            }
            //时间图标
            boolean chronometerVisible = attributes.getBoolean(R.styleable.TitleBarView_title_exam_chronometer_visible, false);
            if (chronometerVisible) {
                chronometer.setVisibility(View.VISIBLE);
            } else {
                chronometer.setVisibility(View.INVISIBLE);
            }
            attributes.recycle();
        }
    }


    public ImageView getTitleBack() {
        return titleBack;
    }
    public ImageButton getTitleAnswer() {
        return titleAnswer;
    }
    public TextView getTitleText() {
        return titleText;
    }
    public Chronometer getChronometer() {
        return chronometer;
    }

    //方法控制
    public void setTitleText(String text) {
        titleText.setText(text);
    }
    public void setTitleTextColor(int textcolor) {
        titleText.setText(textcolor);
    }
    public void setTitleAnswer(boolean visible) {
        if (visible) {
            titleAnswer.setVisibility(View.VISIBLE);
        } else {
            titleAnswer.setVisibility(View.INVISIBLE);
        }
    }
    public void setTitleBack(boolean visible) {
        if (visible) {
            titleBack.setVisibility(View.VISIBLE);
        } else {
            titleBack.setVisibility(View.INVISIBLE);
        }
    }
    public void setChronometer(boolean visible) {
        if (visible) {
            chronometer.setVisibility(View.VISIBLE);
        } else {
            chronometer.setVisibility(View.INVISIBLE);
        }
    }
    public void setTimer(boolean visible) {
        if (visible) {
            timer.setVisibility(View.VISIBLE);
        } else {
            timer.setVisibility(View.INVISIBLE);
        }
    }

}
