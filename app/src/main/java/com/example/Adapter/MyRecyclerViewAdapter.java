package com.example.Adapter;

import android.app.Activity;
import android.content.Context;
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

import com.example.Object.Lesson;
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
    public ImageButton imageButton;
    public RelativeLayout relativeLayout;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView lessonName;
        ImageButton imageButton;

        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            lessonName = (TextView) view.findViewById(R.id.my_lesson_name_recyview);
            textView = (TextView) view.findViewById(R.id.my_lesson_image);
            imageButton = (ImageButton) view.findViewById(R.id.lesson_delect);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.my_lesson_rela_layout);
        }
    }

    public MyRecyclerViewAdapter(Context mContext, List<Lesson> fruitList, Activity activity) {
        this.mContext = mContext;
        this.myLessonList = fruitList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylessons_recy, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        this.imageButton = holder.imageButton;
        this.relativeLayout = holder.relativeLayout;

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

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Lesson lesson = myLessonList.get(position);
        holder.lessonName.setText(lesson.getName());
        holder.textView.setText(lesson.getName().substring(0,1));
        Log.i(TAG, "position ****" + position);
        if (position < myLessonList.size())
            holder.relativeLayout.setBackgroundResource(R.drawable.ic_attach_file_black_24dp);
        int checkColor = ContextCompat.getColor(mContext, R.color.colorPurple);
        int unCheckColor = mContext.getResources().getColor(R.color.colorTab);//两种获取方式，学习context添加方法
        if (position == mposition) {
            //holder.imageButton.setBackgroundResource(R.drawable.rounded_edittext2);
            holder.lessonName.setTextColor(checkColor);
        } else {
            //holder.imageButton.setBackgroundResource(R.drawable.rounded_edittext);
            holder.lessonName.setTextColor(Color.GRAY);
        }
        //holder.imageView.setColorFilter(mposition==position? checkColor:unCheckColor);注意view setcolor的方法
        holder.textView.setTextColor(mposition==position? checkColor:unCheckColor);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG, "LRLHomeFragment:is  checkbox position is " + mposition);
                //final int checkbox_position = mposition;
                holder.imageButton.setVisibility(View.VISIBLE);
                holder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "LRLHomeFragment:is  checkbox position is ----" + mposition);
                        // TODO Auto-generated method stub
                        //if (isChecked) {
                        notifyItemRemoved(position);
                        myLessonList.remove(position);
                        notifyItemRangeChanged(position, getItemCount());
                        holder.imageButton.setVisibility(View.GONE);
                    }
                });

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {

        //Log.i(TAG, "LgetItemCount ----"+ (myLessonList.size()+1));
        return myLessonList.size()+1;

    }

    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
    public void setItemLongClickListener(itemLongClickListener listener){
        this.longClickListener = listener;
    }
}