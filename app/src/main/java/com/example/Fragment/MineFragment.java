package com.example.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.UI.ItemView;
import com.example.Callback.itemClickListener;
import com.example.testsys.R;

/**
 * Created by DELL on 2019/10/home5.
 */

public class MineFragment extends Fragment {
    private static String TAG = "MineFragment";
    private ItemView message,test,passport,about;
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.mine, container, false);
            message = (ItemView) view.findViewById(R.id.mine_message);
            test = (ItemView) view.findViewById(R.id.mine_exam);
            passport = (ItemView) view.findViewById(R.id.change_password);
            about = (ItemView) view.findViewById(R.id.about);

            super.onCreate(savedInstanceState);


        message.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL setItemClickListener is OK");
                Toast.makeText(getContext(),TAG,Toast.LENGTH_SHORT).show();
            }

        });
        test.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL setItemClickListener is OK");
                Toast.makeText(getContext(),TAG,Toast.LENGTH_SHORT).show();
            }

        });
        passport.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL setItemClickListener is OK");
                Toast.makeText(getContext(),TAG,Toast.LENGTH_SHORT).show();
            }

        });
        about.setItemClickListener(new itemClickListener() {
            @Override
            public void itemClick() {
                Log.i(TAG,"LRL setItemClickListener is OK");
                Toast.makeText(getContext(),TAG,Toast.LENGTH_SHORT).show();
            }

        });
        /*测试用勿删，实验成功。通过gettext对象来修改内容，不必一定在xml中设置
        TextView lefttext = message.getleftText();
        lefttext.setText("sssssss");*/
        }
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
       /* if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }*/
        Log.i(TAG, "onDestroyView: ====Fragment1");
    }
}



