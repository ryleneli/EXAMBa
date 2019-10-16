package com.example.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapter.MyExpandableAdapter;
import com.example.Adapter.MyRecyclerViewAdapter;
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
    private RecyclerView recyclerView ;
    private MyRecyclerViewAdapter recyclerViewAdapter;
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

        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),lessonList);
        recyclerView.setAdapter(recyclerViewAdapter);
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


