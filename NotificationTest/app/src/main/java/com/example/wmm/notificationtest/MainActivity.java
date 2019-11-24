package com.example.wmm.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotification=(Button)findViewById(R.id.send_notification);
        sendNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notification:
                Intent intent=new Intent(this,NotificationActivity.class);
                //通知点击的intent
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
                NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                //高版本的android需要指定NotificationChannel
                NotificationChannel channel=new NotificationChannel("1","my_channel",NotificationManager.IMPORTANCE_HIGH);
//                channel.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Asteroid.ogg")),null);
//                channel.setVibrationPattern(new long[]{0,1000,1000,1000});
                channel.enableVibration(true);
                manager.createNotificationChannel(channel);
                Notification notification=new NotificationCompat.Builder(this,"1")
                        .setContentTitle("This is content title")
//                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setAutoCancel(true)
//                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Asteroid.ogg")))
//                        .setSound(Uri.parse("content://settings/system/notification_sound"))
                        //不起作用
                        .setSound(MediaStore.Audio.Media.INTERNAL_CONTENT_URI)
                        .setVibrate(new long[]{0,1000,1000,1})
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp"))
                        //不起作用
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(
                                BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round)
                        ))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();

                //显示通知，id是通知编号
                manager.notify(1,notification);
                break;
            default:
                break;
        }
    }
}
