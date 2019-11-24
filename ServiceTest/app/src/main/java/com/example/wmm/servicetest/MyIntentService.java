package com.example.wmm.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

//IntentService的作用基本上和Service差不多，只不过Service不会默认开启子线程，不会自动在Service完成后调用stopSelf，而是需要手动完成
//IntentService则是自动开启子线程，并且完成之后会自动关闭service
public class MyIntentService extends IntentService {
    public static final String TAG="IntentService";
    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        Log.d(TAG,"Thread is "+Thread.currentThread().getId());
    }

    //该方法自动在子线程中执行,且只有该方法会在子线程执行，其他方法都是主线程执行
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onHandleIntent");
        Log.d(TAG,"Thread is "+Thread.currentThread().getId());
    }

    //IntentService会在onHandleIntent执行完毕后自动销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        Log.d(TAG,"Thread is "+Thread.currentThread().getId());
    }
}
