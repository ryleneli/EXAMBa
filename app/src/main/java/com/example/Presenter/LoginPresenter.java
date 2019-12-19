package com.example.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.Activity.MainActivity;
//import com.example.Activity.RegisterActivity;
import com.example.Fragment.RegisterDialogFragment;
import com.google.gson.Gson;

import rx.Observer;
import com.example.webConnect.NetWorks;
import com.example.LocalRecord.LocalRecord;
import com.example.Activity.LoginActivity;
//import yong.tank.Title_Activity.View.RegisterActivity;
//import yong.tank.Title_Activity.View.WebInfoActivity;
import com.example.Object.Bean.User;

/**
 * 处理登陆相关的方法
 */

public class LoginPresenter{
    private static String TAG = "LoginPresenter";
    private Context context;
    private LoginActivity loginActivity;
    private Gson gson =new Gson();
    private LocalRecord<User> localUser = new LocalRecord<User>();
    public LoginPresenter(Context context, LoginActivity loginActivity){
        this.loginActivity=loginActivity;
        this.context=context;
    }

    public void login() {
        ConnectivityManager cwjManager=(ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cwjManager.getActiveNetworkInfo()!=null){
            if(cwjManager.getActiveNetworkInfo().isAvailable()){

                //获取填写的注册信息
                String username =loginActivity.account.getText().toString();
                String password =loginActivity.password.getText().toString();
                if(username.trim().length()==0||password.trim().length()==0){
                    loginActivity.showToast("用户名和密码为空");
                }else{
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    //设置状态为上线
                    user.setState(1);
                    login(user);

                }

          }else{
                loginActivity.showToast("您的设备未联网啊，请检查设备网络状况...");
            }
        }else {
            loginActivity.showToast("您的设备未联网啊，请检查设备网络状况...");
        }


    }

    public void toRegister() {
        loginActivity.showToast("跳转到注册界面");
        showEditDialog();
    }
    private void showEditDialog() {
        RegisterDialogFragment fragment = new RegisterDialogFragment();
        fragment.show(loginActivity.fm, "fragment_edit_name");
    }

    public void login(User user) {
        //登录用户信息

        NetWorks.userLogin("userLogin",gson.toJson(user),new Observer<String>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                loginActivity.showToast("连接服务器出错-->1");
                Log.i(TAG, "startservice    =====");
            }

            @Override
            public void onNext(String info) {
                Log.i(TAG,info);
                if(info.equals("0")){
                    loginActivity.showToast("用户名或者密码出错");
                }
                else{
                    //获取登录相关的信息，并更新本地的信息 ,主要更新最后登录时间
                    User loginUser= gson.fromJson(info,User.class);

                    //StaticVariable.LOCAL_USER_INFO.setLastLoginDate(loginUser.getLastLoginDate());
                    //localUser.saveInfoLocal(StaticVariable.LOCAL_USER_INFO, StaticVariable.USER_FILE);
                    //赋值个人信息到全局变量中
                    loginActivity.showToast("登录成功，跳转到个人信息界面");
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    //TODO 对比一下user ID和info是否一致.....
                    //toWebInfo();
                }

            }
        });
    }
}
