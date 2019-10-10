package com.example.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.UI.ItemView;
import com.example.itemClickListener;
import com.example.testsys.R;

/**
 * Created by DELL on 2019/10/home5.
 */

public class MineFragment extends Fragment {
    private static String TAG = "MineFragment";
    ItemView message;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mine, container, false);
        message = (ItemView) view.findViewById(R.id.mine_message);
        super.onCreate(savedInstanceState);
        Log.i(TAG,"LRL CREAT MINE FRAGMENT");
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //message.setBackgroundColor(view.findViewById(R.id.mine_message),006600);
                Toast.makeText(getContext(), "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }
        });
/*
        message.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL setItemClickListener is OK");
                Toast.makeText(getContext(),TAG,Toast.LENGTH_SHORT).show();
            }

        });*/
        /*测试用勿删，实验成功。通过gettext对象来修改内容，不必一定在xml中设置
        TextView lefttext = message.getleftText();
        lefttext.setText("sssssss");*/
        return view;
    }
}



