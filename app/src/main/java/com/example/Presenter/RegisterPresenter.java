
package com.example.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.Activity.MainActivity;
import com.example.Fragment.RegisterDialogFragment;
import com.example.LocalRecord.LocalRecord;
import com.example.Object.Bean.User;
import com.example.webConnect.NetWorks;
import com.google.gson.Gson;

import rx.Observer;

/**
 * Created by hasee on 2016/10/27.
 * 处理注册相关的方法
 */

public class RegisterPresenter {
    private static String TAG = "RegisterDialogFragment";
    //private Context context;
    private RegisterDialogFragment registerDialogFragment;
    private Gson gson =new Gson();
    private LocalRecord<User> localUser = new LocalRecord<User>();
    public RegisterPresenter(RegisterDialogFragment registerDialogFragment){
        this.registerDialogFragment=registerDialogFragment;
    }

    public void register() {
        ConnectivityManager cwjManager=(ConnectivityManager)this.registerDialogFragment.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cwjManager.getActiveNetworkInfo().isAvailable()){
            //获取填写的注册信息
            String username =registerDialogFragment.mUsername.getText().toString();
            String password =registerDialogFragment.mPassword.getText().toString();
            if(username.trim().length()==0||password.trim().length()==0){
                //registerActivity.showToast("用户名和密码为空");
            }else{
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                registerToWeb(user);
            }
       }else{
            //registerActivity.showToast("您的设备未联网啊，请检查设备网络状况...");
        }
    }

    private void registerToWeb(final User user) {
        //注册用户信息
        NetWorks.addNewUser("addNewUser",gson.toJson(user),new Observer<String>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"getUserInfo error :"+e);
                //registerActivity.showToast("连接服务器出错-->1");
            }

            @Override
            public void onNext(String info) {
                Log.i(TAG,info);
                if(info.equals("1"))
                    Log.i(TAG,"getUserInfo error :");
                    //registerActivity.showToast("连接服务器出错-->2");
                else if(info.equals("#")){
                    Log.i(TAG,"getUserInfo error :");
                    //registerActivity.showToast("服务器已被注册相同的用户名");
                }else{
                    //registerActivity.showToast("注册用户信息成功");
                    toWebInfo(user);
                }

            }
        });
    }
    public void toWebInfo(final User user) {
        //获取登录相关的信息，并更新本地的信息
        NetWorks.userLogin("userLogin",gson.toJson(user),new Observer<String>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"getUserInfo error :"+e);
                //registerActivity.showToast("连接服务器出错-->1");
            }

            @Override
            public void onNext(String info) {
                Log.i(TAG,info);
                if(info.equals("0")){
                    Log.i(TAG,"getUserInfo error :");
                    // registerActivity.showToast("用户名和密码错误.....");
                }
                else{
                    //获取登录相关的信息，并更新本地的信息
                    //User loginUser= gson.fromJson(info,User.class);
                    //从注册信息中获取相关的资料
                    //StaticVariable.LOCAL_USER_INFO.setId(loginUser.getId());
                    //StaticVariable.LOCAL_USER_INFO.setUsername(loginUser.getUsername());
                    //StaticVariable.LOCAL_USER_INFO.setPassword(loginUser.getPassword());
                    //localUser.saveInfoLocal(StaticVariable.LOCAL_USER_INFO, StaticVariable.USER_FILE);
                    //registerActivity.showToast("登录成功，跳转到个人信息界面");
                    Log.i(TAG,"getUserInfo ***************error :");
                    Intent intent = new Intent(registerDialogFragment.getContext(), MainActivity.class);
                    registerDialogFragment.getContext().startActivity(intent);
                    Log.i(TAG,"getUserInfo error================ :");
                    registerDialogFragment.dismiss();
                }
            }
        });



    }
}
