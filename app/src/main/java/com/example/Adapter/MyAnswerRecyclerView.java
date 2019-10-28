package com.example.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Activity.ExamActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Object.Lesson;
import com.example.itemClickListener;
import com.example.testsys.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 15151 on 2019/10/27.
 */

public class MyAnswerRecyclerView extends RecyclerView.Adapter<MyAnswerRecyclerView.ViewHolder> {
private Context mContext;
private Activity activity;
private int myAnswer[];
public int mposition = 0;
private itemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView answerNum;
        RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);
            answerNum = (TextView) view.findViewById(R.id.answerItem_num);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.answerItem_layout);
        }
    }
    public MyAnswerRecyclerView(Context mContext, Activity activity,int myAnswer[]) {
        this.mContext = mContext;
        this.activity = activity;
        this.myAnswer = myAnswer;
    }

    @NonNull
    @Override
    public MyAnswerRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        final MyAnswerRecyclerView.ViewHolder holder = new MyAnswerRecyclerView.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将点击的位置传出去
                mposition = holder.getAdapterPosition();
                notifyDataSetChanged();
                Intent intent = new Intent(activity, ExamActivity.class);
                intent.putExtra("select_index",mposition);
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnswerRecyclerView.ViewHolder holder, int position) {
        int temp = myAnswer[position];
        holder.answerNum.setText(position+1+"");
        int unSelectColor = ContextCompat.getColor(mContext, R.color.colorTitleBar);
        int selectColor = mContext.getResources().getColor(R.color.colorTab);//两种获取方式，学习context添加方法
        if (temp == 0) {
            holder.relativeLayout.setBackgroundResource(R.drawable.rounded_imagebutton2);
            holder.answerNum.setTextColor(unSelectColor);
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.rounded_imagebutton);
            holder.answerNum.setTextColor(selectColor);
        }
    }

    @Override
    public int getItemCount() {
        return myAnswer.length;
    }
    public void setItemClickListener(itemClickListener listener){
        this.listener = listener;
    }
}
