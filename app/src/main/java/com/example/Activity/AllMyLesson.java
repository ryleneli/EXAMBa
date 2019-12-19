package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.Adapter.MylessonAdapter;
import com.example.Fragment.HomeFragment;
import com.example.Object.Bean.Lesson;
import com.example.Object.CustomizeObject.MyListView;
import com.example.UI.TitleBarView;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15151 on 2019/10/18.
 */

public class AllMyLesson extends Activity {
    private static String TAG = "AllMyLesson";
    private Button addLesson;
    private MyListView listView;
    private ImageView back_img;
    private TitleBarView titleBarView;
    private List<Lesson> myLessonList = new ArrayList<Lesson>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmylessons);
        listView = (MyListView) findViewById(R.id.list_view_mylessons );
        addLesson = (Button) findViewById(R.id.addlesson_mylessons);
        titleBarView = (TitleBarView)findViewById(R.id.allmylessons_title) ;
        back_img = (ImageView)titleBarView.getTitleBack();
        initLessons();
        MylessonAdapter mylessonAdapter = new MylessonAdapter(this,R.layout.mylesson_list,myLessonList);
        listView.setAdapter(mylessonAdapter);


        addLesson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.i(TAG,"LRL ready to enter AllLesson ");
                Intent intent_1 = new Intent(AllMyLesson.this, AllLessons.class);
                startActivity(intent_1);
            }
        });
        back_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent_2 = new Intent(AllMyLesson.this, MainActivity.class);
                startActivity(intent_2);
            }
        });
    }
    private void initLessons()
    {
        Lesson apple = new Lesson("test_lesson_1");
        myLessonList.add(apple);
        Lesson banana = new Lesson("test_lesson_2");
        myLessonList.add(banana);
        Lesson orange = new Lesson("test_lesson_3");
        myLessonList.add(orange);
        Lesson watermelon = new Lesson("test_lesson_4");
        myLessonList.add(watermelon);
        Lesson pear = new Lesson("test_lesson_5");
        myLessonList.add(pear);
        Lesson grape = new Lesson("test_lesson_6");
        myLessonList.add(grape);
        Lesson watermelon1 = new Lesson("test_lesson_7");
        myLessonList.add(watermelon1);
        Lesson pear1 = new Lesson("test_lesson_8");
        myLessonList.add(pear1);
        Lesson grape1 = new Lesson("test_lesson_9");
        myLessonList.add(grape1);
    }
}
