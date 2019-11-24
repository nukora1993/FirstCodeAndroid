package com.example.wmm.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //执行具体逻辑
            }
        }).start();

        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        //一个小时的毫秒数
        int anHour=60*60*1000;
        long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
        //自己启动自己
        Intent i=new Intent(this,LongRunningService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        //alarm时间可能不是特别准，因为系统可能进入doze模式，这时候需要使用setExact
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

        return super.onStartCommand(intent, flags, startId);
    }
}
