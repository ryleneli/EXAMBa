package com.example.Fragment;

import android.support.v4.app.DialogFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.Presenter.RegisterPresenter;
import com.example.testsys.R;

public class RegisterDialogFragment extends DialogFragment implements View.OnClickListener
   {
       private static String TAG = "RegisterDialogFragment";
       public EditText mUsername;
       public EditText mPassword;
       public Button btn;
       public ImageView iv;
       private RegisterPresenter registerPresenter = new RegisterPresenter(this);
       @Nullable
       @Override
       public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
       {        //设置背景透明
           getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           return super.onCreateView(inflater, container, savedInstanceState);

       }
       @NonNull
       @Override
       public Dialog onCreateDialog(Bundle savedInstanceState)
       {
           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           View view= LayoutInflater.from(getActivity()).inflate(R.layout.register_layout, null);
           mUsername= view.findViewById(R.id.register_et1);
           btn= view.findViewById(R.id.register_btn);
           mPassword= view.findViewById(R.id.register_et2);
           btn.setOnClickListener(this);
           builder.setView(view);
           return builder.create();
       }
       @Override
       public void onClick(View v)
       {
           switch (v.getId())
           {
               case R.id.register_btn:
                   Log.i(TAG,"battle_computer");
                   registerPresenter.register();
                   break;
           }
       }
   }
