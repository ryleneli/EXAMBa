package com.example.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Object.Lesson;
import com.example.itemClickListener;
import com.example.testsys.R;

import java.util.List;

/**
 * Created by rylene_li on 2019/10/16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Lesson> myLessonList;
    public int mposition = 0;
    private itemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView lessonName;

        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            lessonName = (TextView) view.findViewById(R.id.my_lesson_name_recyview);
            imageView = (ImageView) view.findViewById(R.id.my_lesson_image);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.my_lesson_rela_layout);
        }
    }

    public MyRecyclerViewAdapter(Context mContext,List<Lesson> fruitList) {
        this.mContext = mContext;
        this.myLessonList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylessons, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将点击的位置传出去
                mposition = holder.getAdapterPosition();
                listener.itemClick();
                /*在点击监听里最好写入setVisibility(View.VISIBLE);这样可以避免效果会闪
                //holder.mListSelect.setVisibility(View.VISIBLE);
                //刷新界面 notify 通知Data 数据set设置Changed变化
                在这里运行notifyDataSetChanged 会导致下面的onBindViewHolder 重新加载一遍*/
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = myLessonList.get(position);
        holder.lessonName.setText(lesson.getName());
        int checkColor = ContextCompat.getColor(mContext, R.color.colorPurple);
        int unCheckColor = mContext.getResources().getColor(R.color.colorTab);//两种获取方式，学习context添加方法
        if (position == mposition) {
            holder.relativeLayout.setBackgroundResource(R.drawable.rounded_edittext2);
            holder.lessonName.setTextColor(checkColor);
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.rounded_edittext);
            holder.lessonName.setTextColor(Color.GRAY);
        }
        holder.imageView.setColorFilter(mposition==position? checkColor:unCheckColor);
    }

    @Override
    public int getItemCount() {
        return myLessonList.size();
    }
    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
}