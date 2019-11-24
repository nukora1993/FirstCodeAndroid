package com.example.wmm.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final String TAG="receiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
//        for (int i = 0; i <1000000 ; i++) {
//            System.out.println(i);
//        }
        //注意Toast的弹出顺序和程序的逻辑不一定一致，这里我用了两个接收器，每个接收器再接收到广播后就弹出Toast的，
        // 但是实测只能显示最后一个Toast，即使在第一个接收到的接收器中进行耗时操作，仍然只能显示最后一个Toast
        //这里还不是太理解，可能和UI逻辑有关
        Toast.makeText(context,"received in "+getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
//        for (int i = 0; i <10 ; i++) {
//            Log.d(TAG,"running");
//            System.out.println(i);
//        }
        Log.d(TAG,getClass().getSimpleName());

    }
}
