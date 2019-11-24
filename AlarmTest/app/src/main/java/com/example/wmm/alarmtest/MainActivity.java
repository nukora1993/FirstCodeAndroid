package com.example.wmm.alarmtest;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        //触发的时间
//        long triggerAtTime=SystemClock.elapsedRealtime()+10*10000;
//        //设置在10s后执行pendingIntent所设置的逻辑,注意第一个参数指的是设定时间的类型
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
    }
}
