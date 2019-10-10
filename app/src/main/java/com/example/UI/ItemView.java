package com.example.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.itemClickListener;
import com.example.testsys.R;

/**
 * Created by DELL on 2019/10/home5.
 */

public class ItemView extends RelativeLayout {

    private static String TAG = "ItemView";

    public ImageView leftIcon,rightArrow,newMessage;
    public TextView leftText;
    private itemClickListener listener;
    //向外暴漏接口
    public void setItemClickListener(itemClickListener listener){
        this.listener=listener;
    }

    public ItemView(Context context) {
        super(context);
        init(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        //Log.i(TAG,"LRL ENTER INIT");
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, this);
        leftIcon = findViewById(R.id.left_icon);
        leftText = findViewById(R.id.left_text);
        newMessage = findViewById(R.id.new_message);
        rightArrow = findViewById(R.id.right_arrow);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        //Log.i(TAG,"LRL ENTER INIT and obtain is ok");
        if (attributes != null) {
            //Log.i(TAG,"LRL ENTER INIT and enter if");
            //处理titleBar背景色

            int backGroundColor = attributes.getColor(R.styleable.ItemView_background_color, Color.WHITE);
            view.setBackgroundColor(backGroundColor);
            //Log.i(TAG,"LRL ENTER INIT and setBackgroundResource is ok");
            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean leftIconVisible = attributes.getBoolean(R.styleable.ItemView_left_icon_visible, true);
            if (leftIconVisible) {
                leftIcon.setVisibility(View.VISIBLE);
            } else {
                leftIcon.setVisibility(View.INVISIBLE);
            }
            //设置左边图片icon
            int leftIconDrawable = attributes.getResourceId(R.styleable.ItemView_left_icon_drawable, -1);
            if (leftIconDrawable != -1) {
                leftIcon.setBackgroundResource(leftIconDrawable);
            }


            //获取是否要显示左边按钮
            boolean newMessageVisible = attributes.getBoolean(R.styleable.ItemView_new_message_visible, false);
            if (newMessageVisible) {
                newMessage.setVisibility(View.INVISIBLE);
            } else {
                newMessage.setVisibility(View.VISIBLE);
            }
            //设置左边图片icon
            int newMessageDrawable = attributes.getResourceId(R.styleable.ItemView_new_message_drawable, -1);
            if (newMessageDrawable != -1) {
                newMessage.setBackgroundResource(newMessageDrawable);
            }


            //获取是否要显示左边按钮
            boolean rightArrowVisible = attributes.getBoolean(R.styleable.ItemView_right_arrow_visible, true);
            if (rightArrowVisible) {
                rightArrow.setVisibility(View.VISIBLE);
            } else {
                rightArrow.setVisibility(View.INVISIBLE);
            }
            //设置左边图片icon
            int rightArrowDrawable = attributes.getResourceId(R.styleable.ItemView_right_arrow_drawable, -1);
            if (rightArrowDrawable != -1) {
                rightArrow.setBackgroundResource(rightArrowDrawable);
            }

            //设置右边按钮的文字
            String text = attributes.getString(R.styleable.ItemView_left_text_text);
            leftText.setText(text);
            int textcolor = attributes.getColor(R.styleable.ItemView_left_text_text_color, Color.BLACK);
            leftText.setTextColor(textcolor);

            attributes.recycle();
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick();
                }
            });
        }
    }



    public void setBackgroundColor(View view,int bgcolor) {

        view.setBackgroundColor(bgcolor);
    }
    public ImageView getLeftIcon() {
        return leftIcon;
    }
    public ImageView getRightArrow() {
        return rightArrow;
    }
    public ImageView getNewMessage() {
        return newMessage;
    }
    public TextView getLeftText() {
        return leftText;
    }
}
