package com.example.maicoolweather.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        final OkHttpClient client=new OkHttpClient();
        final Request request=new Request.Builder().url(address).build();
        //注意enqueu会自动开启线程，并执行完毕后回调callback
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    String res=client.newCall(request).execute().body().string();
//                    Log.d("fuck",res);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
        client.newCall(request).enqueue(callback);
    }
}
