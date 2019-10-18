package com.example.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Object.Lesson;
import com.example.UI.ItemView;
import com.example.UI.LessonItemView;
import com.example.testsys.R;

import java.util.List;

/**
 * Created by rylene_li on 2019/9/30.
 */

public class MylessonAdapter extends ArrayAdapter {
    private static String TAG = "MylessonAdapter";
        private final int resourceId;
        private TextView lessonName;
        private ImageView imageView;
        public MylessonAdapter(Context context, int textViewResourceId, List<Lesson> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Lesson lesson = (Lesson) getItem(position); // 获取当前项的Fruit实例
            Log.i(TAG,"LRL myLessonList size =======is "+position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
            LessonItemView lessonItemView = view.findViewById(R.id.mylesson_listview);
            imageView = lessonItemView.getLessonIcon();
            imageView.setImageResource(R.drawable.ic_pur);
            lessonName = lessonItemView.getLessonNameText();
            lessonName.setText(lesson.getName());
            return view;
        }
    }

