package com.example.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Activity.AllMyLesson;
import com.example.Activity.MainActivity;
import com.example.Object.Lesson;
import com.example.Object.MyRecyclerView;
import com.example.itemClickListener;
import com.example.itemLongClickListener;
import com.example.testsys.R;

import java.util.List;

/**
 * Created by rylene_li on 2019/10/16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static String TAG = "LRLHomeFragment";
    private Context mContext;
    private List<Lesson> myLessonList;
    private Activity activity;
    public int mposition = 0;
    private itemClickListener listener;
    private itemLongClickListener longClickListener;

    // 首先定义几个常量标记item的类型
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private int mBottomCount = 0;// 底部的数量
    private View VIEW_BOTTOM;

    // 判断当前item是否是底部
    public boolean isBottomView(int position) {
        return position == (getItemCount() - 1);
    }

    public void setmBottomCount(int count) {
        mBottomCount = count;
    }

    // 判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (isBottomView(position)) {
            Log.i(TAG, "LRLHomeFragment:isBottomView yes ");
            // 底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            Log.i(TAG, "LRLHomeFragment:isBottomView no ");
            // 内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    public MyRecyclerViewAdapter(Context mContext, List<Lesson> fruitList, Activity activity) {
        this.mContext = mContext;
        this.myLessonList = fruitList;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "LRLHome===========================");
        View view;
        if (viewType == ITEM_TYPE_BOTTOM) {
            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_recy, parent, false);
            return new ViewHolder(VIEW_BOTTOM);
        } else {
            //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylessons_recy, parent, false);
            return new ViewHolder(getLayout(R.layout.mylessons_recy));
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addFooterView(View footerView) {
        if (VIEW_BOTTOM != null) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_BOTTOM = footerView;
            //ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
            notifyItemRangeChanged(getItemCount() - 1, getItemCount());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.i(TAG, "LRLHome===========================onbind");
        if (!isBottomView(position)) {
            Log.i(TAG, "LRLHomeFragment:enter content view");
            TextView lessonName = (TextView) holder.itemView.findViewById(R.id.my_lesson_name_recyview);
            TextView textView = (TextView) holder.itemView.findViewById(R.id.my_lesson_image);
            final ImageButton imageButton = (ImageButton) holder.itemView.findViewById(R.id.lesson_delect);
            Lesson lesson = myLessonList.get(position);
            lessonName.setText(lesson.getName());
            textView.setText(lesson.getName().substring(0, 1));
            Log.i(TAG, "position ****" + position);
            int checkColor = ContextCompat.getColor(mContext, R.color.colorPurple);
            int unCheckColor = mContext.getResources().getColor(R.color.colorTab);//两种获取方式，学习context添加方法
            if (position == mposition) {
                //holder.imageButton.setBackgroundResource(R.drawable.rounded_edittext2);
                lessonName.setTextColor(checkColor);
            } else {
                //holder.imageButton.setBackgroundResource(R.drawable.rounded_edittext);
                lessonName.setTextColor(Color.GRAY);
            }
            //holder.imageView.setColorFilter(mposition==position? checkColor:unCheckColor);注意view setcolor的方法
            textView.setTextColor(mposition == position ? checkColor : unCheckColor);
            imageButton.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //将点击的位置传出去
                    mposition = holder.getAdapterPosition();
                    Log.i(TAG, "mposition ----" + mposition);
                    listener.itemClick();
                    /*在点击监听里最好写入setVisibility(View.VISIBLE);这样可以避免效果会闪
                    //holder.mListSelect.setVisibility(View.VISIBLE);
                    //刷新界面 notify 通知Data 数据set设置Changed变化
                    在这里运行notifyDataSetChanged 会导致下面的onBindViewHolder 重新加载一遍*/
                    notifyDataSetChanged();
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.i(TAG, "LRLHomeFragment:is  checkbox position is " + mposition);
                    //final int checkbox_position = mposition;
                    imageButton.setVisibility(View.VISIBLE);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i(TAG, "LRLHomeFragment:is  ppppppposition is ----" + position);
                            Log.i(TAG, "LRLHomeFragment:is  mmmmmmposition is ----" + mposition);
                            // TODO Auto-generated method stub
                            //if (isChecked) {
                            notifyItemRemoved(position);
                            Log.i(TAG, "LRLHomeFragment:is before getItemCount()is ----" + getItemCount());
                            myLessonList.remove(position);

                            notifyItemRangeChanged(position, getItemCount()-mBottomCount-position);
                            Log.i(TAG, "LRLHomeFragment:is after getItemCount()is ----" + getItemCount());
                            //notifyDataSetChanged();全部更新的话就没有动画显示

                        }
                    });

                    return true;
                }
            });
        } else {
            Log.i(TAG, "LRLHomeFragment:enter bottom view");
            RelativeLayout relativeLayout = (RelativeLayout) holder.itemView.findViewById(R.id.bottom_rela_layout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AllMyLesson.class);
                    mContext.startActivity(intent);
                    Log.i(TAG, "LRLHomeFragment:is  checkbox position is "+holder.getAdapterPosition());
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        if (mBottomCount != 0)
            return myLessonList.size()+mBottomCount;
        //Log.i(TAG, "LgetItemCount ----"+ (myLessonList.size()+1));
        else
            return myLessonList.size();
    }

    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
    public void setItemLongClickListener(itemLongClickListener listener){
        this.longClickListener = listener;
    }
}