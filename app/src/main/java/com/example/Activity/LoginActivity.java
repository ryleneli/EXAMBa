package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Presenter.LoginPresenter;
import com.example.testsys.R;

/**
 * Created by rylene_li on 2019/9/27.
 */

public class LoginActivity extends FragmentActivity implements View.OnClickListener{

    private Button login_button;
    private LoginPresenter loginPresenter;
    public EditText account,password;
    public TextView register;
    public FragmentManager fm = getSupportFragmentManager();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = (Button) findViewById(R.id.login_button);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register_textView);
        loginPresenter = new LoginPresenter(this,this);

        login_button.setOnClickListener(this);
        register.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                //Log.i(TAG,"battle_computer");
                loginPresenter.login();
                break;
            case R.id.register_textView:
                //Log.i(TAG,"battle_computer");
                loginPresenter.toRegister();
                break;
            default:
                break;
        }
    }
    public void showToast(String info){
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
