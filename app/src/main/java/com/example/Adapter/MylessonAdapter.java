package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.Object.Lesson;
import com.example.UI.ItemView;
import com.example.testsys.R;

import java.util.List;

/**
 * Created by rylene_li on 2019/9/30.
 */

public class MylessonAdapter extends ArrayAdapter {
        private final int resourceId;

        public MylessonAdapter(Context context, int textViewResourceId, List<Lesson> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Lesson lesson = (Lesson) getItem(position); // 获取当前项的Fruit实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
            ItemView LessonName = (ItemView) view.findViewById(R.id.lesson_name);//获取该布局内的文本视图
            TextView textView = LessonName.getLeftText();
            textView.setText(lesson.getName());//为文本视图设置文本内容
            return view;
        }
    }

