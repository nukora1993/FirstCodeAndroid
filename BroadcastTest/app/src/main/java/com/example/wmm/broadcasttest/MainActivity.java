package com.example.wmm.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //在代码中动态注册接收器，也可以在manifest中静态注册，注意receiver和activity是独立的
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取实例
        localBroadcastManager=LocalBroadcastManager.getInstance(this);

        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        networkChangeReceiver=new NetworkChangeReceiver();
        //注册广播接收器，并指定该接收器所处理的intent
        registerReceiver(networkChangeReceiver,intentFilter);

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");
                //这里，对于高版本的android，必须要指定接受的包名，否则无法正常接收
                intent.setPackage("com.example.wmm.broadcasttest");
                //关键函数sendBroadcast
                sendBroadcast(intent);
                //跨应用也必须指定包名,这里的包名实际上应该就是applicationid，因为在eclipse中是没有application id这个概念的
                //在as中区分applicationid和packagename是为了编译的方便，比如区分多个版本，可以命名为多个packagename，但是最终打包为apk还是以applicationid作为最后的packagename
//                intent.setPackage("com.example.wmm.broadcasttest2");
                //即使设置了null，也不能发送给所有的应用，只能本应用接收到
//                intent.setPackage(null);
//                sendBroadcast(intent);
                //发送有序广播
//                sendOrderedBroadcast(intent,null);
//                intent=new Intent("com.example.broadcast.LOCAL_BROADCAST");
                //发送本地广播
//                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcast.LOCAL_BROADCAST");
        localReceiver=new LocalReceiver();

        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();
            //ConnectivityManager是系统服务类，用于管理网络连接，getSystemService返回的是Object
            ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            //这是一个敏感操作，需要加入相应权限,该权限是普通权限，无需向用户申请
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }


        }
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
