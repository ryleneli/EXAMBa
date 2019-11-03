package com.example.Activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Presenter.RegisterPresenter;
import com.example.testsys.R;

public class RegisterActivity extends Activity implements View.OnClickListener{
    private static String TAG = "RegisterActivity";
    private RegisterPresenter registerPresenter;
    private Button registerButton;
    public TextView accountText;
    public TextView passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        registerPresenter = new RegisterPresenter(this,this);
        setContentView(R.layout.register_layout);
        registerButton = (Button)findViewById(R.id.register);
        registerButton.setOnClickListener(this);
        accountText=(TextView)findViewById(R.id.register_account);
        passwordText=(TextView)findViewById(R.id.register_password);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                registerPresenter.register();
                break;
            default:
                break;
        }
    }
    public void showToast(String info){
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
