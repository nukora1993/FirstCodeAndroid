package com.example.wmm.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;

    public static final int UPDATE_TEXT=1;



    //Handler类的代码将在主线程运行
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case UPDATE_TEXT:
                    textView.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler myHandler=new MyHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);
        Button changeText=(Button)findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.change_text:
                //子线程中无法更改ui,强行调用会导致程序崩溃
                //报exception： Only the original thread that created a view hierarchy can touch its views
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText("Nice to meet you");
                        //既然子线程无法直接更改ui，那么使用变通的方法，通知主线程取更改ui，android中引入了一个中介Handler类来允许在子线程发送消息给主线程之间通信
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        myHandler.sendMessage(message);

                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
