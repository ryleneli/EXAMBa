package com.example.webConnect;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2016/12/6.
 * 完成对retrofit的封装，并在这里使用到Rxjava，封装使用即可.......
 */

public class NetWorks extends RetrofitUtils {

    //创建实现接口调用
    protected static final NetService service = getRetrofit().create(NetService.class);

    //设缓存有效期为1天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private interface NetService {

        //定义web接口的地方
        @FormUrlEncoded
        @POST("WebService")//通过@GET, @POST, @PUT, @DELETE和@HEAD指定对应的请求方式，参数是请求路径
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> connectedTest(@Field("action") String action);
/*
        @FormUrlEncoded
        @POST("WebService")//通过@GET, @POST, @PUT, @DELETE和@HEAD指定对应的请求方式，参数是请求路径
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> getTitle(@Field("action") String action);*/

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> getUserInfo(@Field("action") String action,@Field("userId") int userId);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> addNewUser(@Field("action") String action,@Field("userInfo") String userInfo);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> userLogin(@Field("action") String action,@Field("userInfo") String userInfo);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> getRoomList(@Field("action") String action,@Field("state") int state);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> addNewRoom(@Field("action") String action,@Field("roomInfo") String roomInfo);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> updateRoomInfo(@Field("action") String action,@Field("roomInfo") String roomInfo);

        @FormUrlEncoded
        @POST("WebService")
        @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        Observable<String> getRoomInfo(@Field("action") String action,@Field("roomId") String roomId);
    }

    //测试连接状态
    public static void connectTest(String action,Observer<String> observer){
        setSubscribe(service.connectedTest(action),observer);
    }

    //获取单个用户信息
    public static void getUserInfo(String action,int userId,Observer<String> observer){
        setSubscribe(service.getUserInfo(action,userId),observer);
    }

    //用户登录
    public static void userLogin(String action, String userInfo,Observer<String> observer){
        setSubscribe(service.userLogin(action,userInfo),observer);
    }

    //用户注册
    public static void addNewUser(String action, String userInfo,Observer<String> observer){
        setSubscribe(service.addNewUser(action,userInfo),observer);
    }

    //获取所有房间信息
    public static void getRoomList(String action, int state,Observer<String> observer){
        setSubscribe(service.getRoomList(action,state),observer);
    }

    //创建一个新的房间
    public static void addNewRoom(String action,String roomInfo,Observer<String> observer){
        setSubscribe(service.addNewRoom(action,roomInfo),observer);
    }

    //获取单个房间信息
    public static void getRoomInfo(String action,String roomId,Observer<String> observer){
        setSubscribe(service.getRoomInfo(action,roomId),observer);
    }

    //更新房间信息
    public static void updateRoomInfo(String action,  String roomInfo,Observer<String> observer){
        setSubscribe(service.updateRoomInfo(action,roomInfo),observer);
    }




    //以后讨论一下这里定义为静态的是否可行.....这里就暂时不处理了.....
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

}
