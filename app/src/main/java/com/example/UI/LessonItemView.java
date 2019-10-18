package com.example.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
 * Created by 15151 on 2019/10/17.
 */

public class LessonItemView extends RelativeLayout {

    private static String TAG = "LessonItemView";

    public ImageView lessonIcon,checkIcon;
    public TextView lessonNameText, chapterText,studentText;
    private itemClickListener listener;

    public LessonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LessonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public LessonItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        Log.i(TAG,"LRL ENTER INIT");
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_item_view, this);
        lessonIcon = findViewById(R.id.lessonimage_lessonlist);
        checkIcon = findViewById(R.id.lesson_check);
        lessonNameText = findViewById(R.id.lessonname_lessonlist);
        chapterText = findViewById(R.id.chapter_number);
        studentText = findViewById(R.id.student_number);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LessonItemView);
        //Log.i(TAG,"LRL ENTER INIT and obtain is ok");
        if (attributes != null) {
            //Log.i(TAG,"LRL ENTER INIT and enter if");
            //处理背景色
            int backGroundColor = attributes.getResourceId(R.styleable.LessonItemView_lesson_background, -1);
            view.setBackgroundResource(backGroundColor);

            //设置左边图片icon
            int lessonIconDrawable = attributes.getResourceId(R.styleable.LessonItemView_lessonimage_lessonlist_drawable, -1);
            if (lessonIconDrawable != -1) {
                lessonIcon.setBackgroundResource(lessonIconDrawable);
            }
            //获取是否要显示左边按钮
            boolean checkIconVisible = attributes.getBoolean(R.styleable.LessonItemView_lesson_check_visible, false);
            if (checkIconVisible) {
                checkIcon.setVisibility(View.VISIBLE);
            } else {
                checkIcon.setVisibility(View.INVISIBLE);
            }
            //设置右边按钮的文字
            String lesson_name_text = attributes.getString(R.styleable.LessonItemView_lessonname_lessonlist);
            lessonNameText.setText(lesson_name_text);
            String chapter_text = attributes.getString(R.styleable.LessonItemView_chapter_number);
            chapterText.setText(chapter_text);
            String student_text = attributes.getString(R.styleable.LessonItemView_student_number);
            studentText.setText(student_text);
            attributes.recycle();
        }
    }



    public void setBackgroundColor(View view,int bgcolor) {

        view.setBackgroundColor(bgcolor);
    }
    public void setLessonIcon(Drawable drawable) {
        lessonIcon.setImageDrawable(drawable);
        Log.i(TAG,"LRL ENTER setImageDrawable");
    }
    public void setCheckIcon(boolean visible) {
        if (visible) {
            checkIcon.setVisibility(View.VISIBLE);
        } else {
            checkIcon.setVisibility(View.INVISIBLE);
        }
    }
    public void setLessonNameText(String text) {
        lessonNameText.setText(text);
        Log.i(TAG,"LRL ENTER setImageDrawable");
    }
    public void setChapterText(String text) {
        chapterText.setText(text);
    }
    public void setStudentText(String text) {
        studentText.setText(text);
    }
    public ImageView getLessonIcon() {
        return lessonIcon;
    }
    public ImageView getCheckIcon() {
        return checkIcon;
    }
    public TextView getLessonNameText() {
        return lessonNameText;
    }
    public TextView getChapterText() {
        return chapterText;
    }
    public TextView getStudentText() {
        return studentText;
    }

}
