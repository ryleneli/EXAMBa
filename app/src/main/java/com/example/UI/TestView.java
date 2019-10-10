package com.example.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testsys.R;

/**
 * Created by DELL on 2019/10/4.
 */

public class TestView extends RelativeLayout {
    public ImageView testImage;
    public TextView testText;
    public TestView(Context context) {
        super(context);
        init(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    public void init(Context context, AttributeSet attrs) {
        //Log.i(TAG,"LRL ENTER INIT");
        View view = LayoutInflater.from(context).inflate(R.layout.test_view, this);
        testImage = findViewById(R.id.test_image);
        testText = findViewById(R.id.test_name);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        //Log.i(TAG,"LRL ENTER INIT and obtain is ok");
        if (attributes != null) {
            //Log.i(TAG,"LRL ENTER INIT and enter if");

            int testImageDrawable = attributes.getResourceId(R.styleable.TestView_imageview_drawable, -1);
            if (testImageDrawable != -1) {
                testImage.setBackgroundResource(testImageDrawable);
            }
            //设置右边按钮的文字
            String text = attributes.getString(R.styleable.TestView_textview_text);
            testText.setText(text);


            attributes.recycle();

        }
    }


}
