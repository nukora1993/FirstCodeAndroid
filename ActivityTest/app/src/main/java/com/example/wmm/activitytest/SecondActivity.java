package com.example.wmm.activitytest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity{
    private static final String TAG="tag2";

    public static void actionStart(Context ctx,String data1,String data2){
        Intent intent=new Intent(ctx,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
//        Log.d(TAG,"Task id is "+getTaskId());
//        Intent intent=getIntent();
//        String extraData=intent.getStringExtra("extra_data");
//        Log.d("SecondActivity",extraData);
        //获取传递过来person
//        Person person=(Person)getIntent().getSerializableExtra("person_data");
        Person person=(Person)getIntent().getParcelableExtra("person_data");

        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.putExtra("data_return","Hello First");
//                setResult(RESULT_OK,intent);
//                finish();
//                Intent intent=new Intent(SecondActivity.this,FirstActivity.class);
                Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("data_return","Hello First");
        setResult(RESULT_OK,intent);
        finish();
    }


}
