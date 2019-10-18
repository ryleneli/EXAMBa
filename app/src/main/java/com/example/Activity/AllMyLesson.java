package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.Adapter.MylessonAdapter;
import com.example.Object.Lesson;
import com.example.Object.MyListView;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 15151 on 2019/10/18.
 */

public class AllMyLesson extends Activity {
    private static String TAG = "AllMyLesson";
    private Button addLesson;
    private MyListView listView;
    private List<Lesson> myLessonList = new ArrayList<Lesson>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmylessons);
        listView = (MyListView) findViewById(R.id.list_view_mylessons );
        addLesson = (Button) findViewById(R.id.addlesson_mylessons);
        initFruits();
        MylessonAdapter mylessonAdapter = new MylessonAdapter(this,R.layout.mylesson_list,myLessonList);
        listView.setAdapter(mylessonAdapter);
        Log.i(TAG,"LRL myLessonList size is "+myLessonList.size());
        addLesson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AllMyLesson.this, AllLessons.class);
                startActivity(intent);
            }
        });
    }
    private void initFruits()
    {
        Lesson apple = new Lesson("Apple");
        myLessonList.add(apple);
        Lesson banana = new Lesson("Banana");
        myLessonList.add(banana);
        Lesson orange = new Lesson("Orange");
        myLessonList.add(orange);
        Lesson watermelon = new Lesson("Watermelon");
        myLessonList.add(watermelon);
        Lesson pear = new Lesson("Pear");
        myLessonList.add(pear);
        Lesson grape = new Lesson("Grape");
        myLessonList.add(grape);
        Lesson watermelon1 = new Lesson("Watermelon");
        myLessonList.add(watermelon1);
        Lesson pear1 = new Lesson("Pear");
        myLessonList.add(pear1);
        Lesson grape1 = new Lesson("Grape");
        myLessonList.add(grape1);
    }
}
