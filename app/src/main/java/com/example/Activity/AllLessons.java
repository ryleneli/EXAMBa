package com.example.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Adapter.MylessonAdapter;
import com.example.Object.Lesson;
import com.example.Object.MyListView;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15151 on 2019/10/18.
 */

public class AllLessons extends Activity {

    private MyListView listView;
    private List<Lesson> allLessonList = new ArrayList<Lesson>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_lessons);
        listView = (MyListView) findViewById(R.id.list_view_mylessons );

        initFruits();
        MylessonAdapter allLessonAdapter = new MylessonAdapter(this,R.layout.mylesson_list,allLessonList);
        listView.setAdapter(allLessonAdapter);

    }
    private void initFruits()
    {
        Lesson apple = new Lesson("Apple");
        allLessonList.add(apple);
        Lesson banana = new Lesson("Banana");
        allLessonList.add(banana);
        Lesson orange = new Lesson("Orange");
        allLessonList.add(orange);
        Lesson watermelon = new Lesson("Watermelon");
        allLessonList.add(watermelon);
        Lesson pear = new Lesson("Pear");
        allLessonList.add(pear);
        Lesson grape = new Lesson("Grape");
        allLessonList.add(grape);
    }
}
