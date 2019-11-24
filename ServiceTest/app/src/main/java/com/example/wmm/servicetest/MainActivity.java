package com.example.wmm.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="ServiceTest";
    private MyService.DownloadBinder downloadBinder;


    private ServiceConnection connection=new ServiceConnection() {
        //当与Service成功bind时被调用，这里的IBind就是在Service中创建的Bind
        //所以逻辑就是：Activity bind->得到service的binder->connection通过binder执行逻辑

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            downloadBinder=(MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgess();
            Log.d(TAG,""+Thread.currentThread().getId());
        }
        //当与Service成功unBind时被调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService=(Button)findViewById(R.id.start_service);
        Button stopService=(Button)findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        Button bindService=(Button)findViewById(R.id.bind_service);
        Button unbindService=(Button)findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        Button startIntentService=(Button)findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_service:
                //启动service的方式和启动activity很相似
                //当启动时，如果service还没有被创建出来，那么调用onCreate->OnStartCommand
                //如果Service之前已经被创建出来，那么只会执行onStartCommand
                //注意，无论activity与service有没有童工binder和connection进行绑定，service和activity的线程都是同一个，service不会自动创建子线程
                Intent startIntent=new Intent(this,MyService.class);
                Log.d(TAG,Thread.currentThread().getId()+"");
                startService(startIntent);

                break;
            case R.id.stop_service:
                //除了在外界调用stopService，还可以在Service本身里调用stopSelf以停止
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent=new Intent(this,MyService.class);
                //第三个参数表示在bind之后如果service不存在则自动创建service，即Service的onCreate会被调用但是startCommand不会被调用
                Log.d(TAG,""+Thread.currentThread().getId());
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:
                //如果进行过startService又进行了bindService，那么只有stopService同时unbindService，Service才会完全onDestroy
                unbindService(connection);
                break;

            case R.id.start_intent_service:
                //启动IntentService的方式和启动Service一致
                Log.d(TAG,""+Thread.currentThread().getId());
                Intent intentService=new Intent(this,MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
