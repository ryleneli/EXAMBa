package com.example.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapter.MyExpandableAdapter;
import com.example.Object.Lesson;
import com.example.Adapter.MylessonAdapter;
import com.example.Object.MyListView;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rylene_li on 2019/9/30.
 */

public class HomeFragment extends Fragment {
    private static String TAG = "HomeFragment";

    private List<Lesson> lessonList = new ArrayList<Lesson>();

    private ExpandableListView expandableListView;
    String[] groupNames = { "a", "b", "c" };
    String[][] childNames = new String[][] { { "a1", "a2", "a3" },
            { "b1", "b2", "b3", "b4", "b5" },
            { "c1", "c2", "c3", "c4" } };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        super.onCreate(savedInstanceState);
        Log.i(TAG, "LRL CREAT HOME FRAGMENT");
        initFruits(); // 初始化水果数据
        MylessonAdapter adapter = new MylessonAdapter(this.getContext(), R.layout.mylessons, lessonList);
        MyListView listView = (MyListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        expandableListView = (ExpandableListView) view.findViewById(R.id.elv);

        // 设置数据适配器
        expandableListView.setAdapter(new MyExpandableAdapter(getContext(), groupNames, childNames));
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        // 当打开的时候，设置小图标为反向
                        // expandableListView.setGroupIndicator(getResources()
                        // .getDrawable(R.drawable.ic_launcher2));

                        // 当打开自己的时候，关闭别人
                        for (int i = 0; i < groupNames.length; i++) {
                            if (groupPosition != i) {
                                // 关闭
                                expandableListView.collapseGroup(i);
                            }
                        }
                    }
                });
        /**
         * 监听点击了哪个孩子
         */
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String string = childNames[groupPosition][childPosition];
                Toast.makeText(getContext()
                        , string, Toast.LENGTH_SHORT).show();
                // 是否消费（处理）该点击事件
                return true;
            }
        });
        /**
         * 组的点击事件
         */
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Toast.makeText(getContext(),
                        "第" + groupPosition + "组被点击了", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // expandableListView
        // .setOnGroupCollapseListener(new OnGroupCollapseListener() {
        // @Override
        // public void onGroupCollapse(int groupPosition) {
        // // 当打开的时候，设置小图标为反向
        // expandableListView.setGroupIndicator(getResources()
        // .getDrawable(R.drawable.ic_launcher));
        // }
        // });
        return view;

    }

    private void initFruits() {
        Lesson apple = new Lesson("Apple");
        lessonList.add(apple);
        Lesson banana = new Lesson("Banana");
        lessonList.add(banana);
        Lesson orange = new Lesson("Orange");
        lessonList.add(orange);
        Lesson watermelon = new Lesson("Watermelon");
        lessonList.add(watermelon);
        Lesson pear = new Lesson("Pear");
        lessonList.add(pear);
        Lesson grape = new Lesson("Grape");
        lessonList.add(grape);
        Lesson pineapple = new Lesson("Pineapple");
        lessonList.add(pineapple);
        Lesson strawberry = new Lesson("Strawberry");
        lessonList.add(strawberry);
        Lesson cherry = new Lesson("Cherry");
        lessonList.add(cherry);
        Lesson mango = new Lesson("Mango");
        lessonList.add(mango);
        Lesson adv = new Lesson("adv");
        lessonList.add(adv);
        Lesson vvv = new Lesson("Grape");
        lessonList.add(vvv);
        Lesson adgf = new Lesson("Pineapple");
        lessonList.add(adgf);
        Lesson deeeee = new Lesson("Strawberry");
        lessonList.add(deeeee);
        Lesson hh = new Lesson("Cherry");
        lessonList.add(hh);
        Lesson hhhh = new Lesson("Mango");
        lessonList.add(hhhh);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated:====Fragment1 ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ====Fragment1");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume:====Fragment1 ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause:====Fragment1 ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ====Fragment1");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ====Fragment1");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy:====Fragment1 ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach:====Fragment1 ");
    }

}


