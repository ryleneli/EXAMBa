package com.example.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.AllMyLesson;
import com.example.Activity.ExamActivity;
import com.example.Activity.MainActivity;
import com.example.Adapter.MyExpandableAdapter;
import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.Object.Lesson;
import com.example.Adapter.MylessonAdapter;
import com.example.Object.MyListView;
import com.example.Object.MyRecyclerView;
import com.example.UI.TestView;
import com.example.itemClickListener;
import com.example.itemLongClickListener;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.VISIBLE;

/**
 * Created by rylene_li on 2019/9/30.
 */

public class HomeFragment extends Fragment {
    private static String TAG = "LRLHomeFragment";

    private List<Lesson> lessonList = new ArrayList<Lesson>();
    private Lesson lesson;
    private int test_mode;
    private MyRecyclerView recyclerView ;
    private MyRecyclerViewAdapter recyclerViewAdapter;
    private Button addLesson;
    private TestView chapterView,randomView,errorView,testView;
    private ExpandableListView expandableListView;
    private TextView hotLesson,learning_text;
    private Handler handler = new Handler();
    private final Timer timer = new Timer();
    private TimerTask task;
    int index;
    String[] groupNames = { "a", "b", "c" };
    String[][] childNames = new String[][] { { "a1", "a2", "a3" },
            { "b1", "b2", "b3", "b4", "b5" },
            { "c1", "c2", "c3", "c4" } };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        super.onCreate(savedInstanceState);
        initFruits();
        addLesson = (Button) view.findViewById(R.id.addlesson_button);
        recyclerView = view.findViewById(R.id.rv);
        hotLesson = (TextView) view.findViewById(R.id.hot_lesson);
        learning_text = (TextView)view.findViewById(R.id.learning_lesson);
        chapterView = (TestView) view.findViewById(R.id.home_chapbutton);
        randomView = (TestView) view.findViewById(R.id.home_randbutton);
        errorView = (TestView) view.findViewById(R.id.home_errorbutton);
        testView = (TestView) view.findViewById(R.id.home_testbutton);
        lesson = lessonList.get(0);
        learning_text.setText(lessonList.get(0).getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new MyRecyclerViewAdapter(getContext(),lessonList,getActivity());
        recyclerViewAdapter.setmBottomCount(1);
        recyclerViewAdapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.bottom_recy,null));
        recyclerView.setAdapter(recyclerViewAdapter);
        handler.post(mUpdate);
       /* //长按事件，
        recyclerViewAdapter.setItemLongClickListener (new itemLongClickListener() {
            @Override
            public boolean itemLongClick() {
                Log.i(TAG,"LRL temp  is =============================setItemLongClickListener"+recyclerViewAdapter.mposition);
                showLongClick(VISIBLE);
                return true;
            }
        });*/

        recyclerViewAdapter.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL temp  is =============================setItemClickListener");
                lesson = lessonList.get(recyclerViewAdapter.mposition);
                String text = lesson.getName();
                learning_text.setText(text);
                //learning_text.setTextColor(getContext().getResources().getColor(R.color.colorPurple));代码设置颜色
            }
        });
        addLesson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent((MainActivity)getActivity(), AllMyLesson.class);
                startActivity(intent);
            }
        });
        final Intent intentToTest = new Intent((MainActivity)getActivity(), ExamActivity.class);
        chapterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent2 = new Intent((MainActivity)getActivity(), ExamActivity.class);
                test_mode = 1;
                intentToTest.putExtra("lesson_name",lesson.getName());
                intentToTest.putExtra("test_mode",test_mode);
                startActivity(intentToTest);
            }
        });
        randomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent2 = new Intent((MainActivity)getActivity(), ExamActivity.class);
                test_mode = 2;
                intentToTest.putExtra("lesson_name",lesson.getName());
                intentToTest.putExtra("test_mode",test_mode);
                startActivity(intentToTest);
            }
        });
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent2 = new Intent((MainActivity)getActivity(), ExamActivity.class);
                test_mode = 3;
                intentToTest.putExtra("lesson_name",lesson.getName());
                intentToTest.putExtra("test_mode",test_mode);
                startActivity(intentToTest);
            }
        });
        testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent2 = new Intent((MainActivity)getActivity(), ExamActivity.class);
                test_mode = 4;
                intentToTest.putExtra("lesson_name",lesson.getName());
                intentToTest.putExtra("test_mode",test_mode);
                startActivity(intentToTest);
            }
        });

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
    private Runnable mUpdate = new Runnable() {
        public void run() {

            if (index < lessonList.size()){
                hotLesson.setText(lessonList.get(index).getName());
                index++;
            }else index = 0;
            handler.postDelayed(this, 1000);

        }
    };

    private void initFruits() {
        Lesson apple = new Lesson("通信原理");
        lessonList.add(apple);
        Lesson banana = new Lesson("C程序设计");
        lessonList.add(banana);
        Lesson orange = new Lesson("C++");
        lessonList.add(orange);
        Lesson watermelon = new Lesson("JAVA");
        lessonList.add(watermelon);
        Lesson pear = new Lesson("电路原理");
        lessonList.add(pear);
        Lesson grape = new Lesson("计算机基础");
        lessonList.add(grape);
    }
    /*
    private void showLongClick(int isvisibility) {
        Log.i(TAG,"LRL temp  is  checkbox position is "+recyclerViewAdapter.mposition);
        //recyclerViewAdapter.checkBox.setVisibility(isvisibility);
        recyclerViewAdapter.i.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    showDelete(recyclerViewAdapter.mposition);
                } else {
                    //editText1.setText(buttonView.getText()+"取消选中");
                }
            }
        });
    }*/
        /***
         * 删除
         */
        public void showDelete(int position)
        {
     /*       //删除缓存
            String home = "";
            for (int i = 0; i < listPosition.size() - 1; i++) {
                UserBean userBean = list.get(listPosition.get(i));
                if (!userBean.isCheck()) {
                    home += listPosition.get(i) + ",";
                }
            }
            aCache.remove("home");
            listPosition.clear();
            try {
                UtilFileDB.ADDFile(aCache, "home", home.substring(0, (home.length() - 1)));
                if (listPosition == null || listPosition.size() <= 1) {
                    listPosition = HomeData.POSITION(aCache);
                }
            } catch (Exception e) {
                listPosition.add((list.size()-1));//只留加号
            }
            showLongClick(false);
        }
        list.clear();
        for (int i = 0; i < 34; i++) {
            UserBean user = new UserBean(HomeData.IMG[i], HomeData.TITLE[i], false, isvisibility);
            list.add(user);
        }*/
        //HomeFragment.list.get(33).setIsvisibility(false);
        notifyData(recyclerViewAdapter.mposition);
    }
    /*****
     * 刷新数据
     */
    public void notifyData(int position) {
        recyclerViewAdapter.notifyItemRemoved(position);
        lessonList.remove(position);
        recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.getItemCount());
        recyclerViewAdapter.notifyDataSetChanged();
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
        lessonList.clear();
        handler.removeCallbacks(mUpdate);
        Log.i(TAG, "onDestroy:====Fragment1 ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach:====Fragment1 ");
    }

}


