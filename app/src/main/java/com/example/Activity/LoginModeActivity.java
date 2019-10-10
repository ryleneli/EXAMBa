package com.example.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testsys.R;


/**
 * Created by DELL on 2019/10/home1.
 */

public class LoginModeActivity extends Activity implements View.OnClickListener {

    private ImageButton imageButton_teacher;
    private ImageButton imageButton_student;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mode);
        imageButton_teacher=(ImageButton) findViewById(R.id.imageButton_teacher);
        imageButton_student=(ImageButton) findViewById(R.id.imageButton_student);
        imageButton_teacher.setOnClickListener(this);
        imageButton_student.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageButton_teacher:
                start ();
                //记录老师类型
                break;
            case R.id.imageButton_student :
                start ();
                //记录学生类型
                break;
        }

    }
    public void start ()
    {
        Intent intent = new Intent() ;
        intent=new Intent(LoginModeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
