package com.example.wmm.networktest;

import android.app.Application;
import android.content.Context;

//Application会当app被启动时自动初始化，调用其onCreate，可以利用这一点以存储全局信息
//一个app只能有一个application，如果要使用另一个application，可以在自己的application中去初始化别的application，以传递相同的context
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
