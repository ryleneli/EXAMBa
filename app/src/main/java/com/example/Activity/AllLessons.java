package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.Adapter.MylessonAdapter;
import com.example.Object.Bean.Lesson;
import com.example.Object.CustomizeObject.MyListView;
import com.example.UI.TitleBarView;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15151 on 2019/10/18.
 */

public class AllLessons extends Activity {
    private static String TAG = "AllLessons";
    private MyListView listView;
    private List<Lesson> allLessonList = new ArrayList<Lesson>();
    private ImageView back_img;
    private TitleBarView titleBarView;
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"LRL ready to enter AllLesson ===========and set view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_lessons);
        listView = (MyListView) findViewById(R.id.list_view_mylessons );
        titleBarView = (TitleBarView)findViewById(R.id.alllessons_title) ;
        back_img = (ImageView)titleBarView.getTitleBack();
        initLessons();
        MylessonAdapter allLessonAdapter = new MylessonAdapter(this,R.layout.mylesson_list,allLessonList);
        listView.setAdapter(allLessonAdapter);
        back_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllLessons.this, AllMyLesson.class);
                startActivity(intent);
            }
        });

    }
    private void initLessons()
    {
        Lesson apple = new Lesson("test_lesson_2");
        allLessonList.add(apple);
        Lesson banana = new Lesson("test_lesson_3");
        allLessonList.add(banana);
        Lesson orange = new Lesson("test_lesson_4");
        allLessonList.add(orange);
        Lesson watermelon = new Lesson("test_lesson_5");
        allLessonList.add(watermelon);
        Lesson pear = new Lesson("test_lesson_6");
        allLessonList.add(pear);
        Lesson grape = new Lesson("test_lesson_7");
        allLessonList.add(grape);
    }
}
