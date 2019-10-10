package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testsys.R;

/**
 * Created by rylene_li on 2019/9/27.
 */

public class LoginActivity extends Activity {

    private Button login_button;

    private EditText account,password;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = (Button) findViewById(R.id.login_button);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent() ;
                intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
