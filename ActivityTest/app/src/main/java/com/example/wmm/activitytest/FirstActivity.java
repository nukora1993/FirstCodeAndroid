package com.example.wmm.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {

    private static final String TAG="tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里很好奇，为什么定义了xml,R就可以找到对应的文件，这个R应该是android系统管理的？
        setContentView(R.layout.first_layout);
//        Log.d(TAG,"onCreate");
//        Log.d(TAG,this.toString());
//        Log.d(TAG,"Task id is "+getTaskId());
        //findViewById返回View对象，Button是View的子类，需要强制转换
        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //注意这里使用到了是FirstActivity.this而不是this，因为Listener是一个匿名内部类，所以必须使用前者才能获取toast的ctx
//                Toast.makeText(FirstActivity.this,"You clicked Button1",Toast.LENGTH_SHORT).show();
//                finish();
                //显式Intent
//                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
//                Intent intent=new Intent("com.example.activitytest.action.START");
//                intent.addCategory("com.example.activitytest.category.MY_CATEGORY");
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                Intent intent=new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel://10086"));
//                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
////                String extraData="Hello Second";
////                intent.putExtra("extra_data",extraData);

//                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
//                startActivityForResult(intent,1);

//                Intent intent=new Intent(FirstActivity.this,FirstActivity.class);
//                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
//                //执行Intent
//                startActivity(intent);
//                SecondActivity.actionStart(FirstActivity.this,"data1","data2");
                Person person=new Person();
                person.setName("Tom");
                person.setAge(20);
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("person_data",person);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnData=data.getStringExtra("data_return");
                    Log.d("FristActivity",returnData);
                }
                break;
            default:
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //根据指定资源文件填充菜单
        getMenuInflater().inflate(R.menu.main,menu);
        //return true表示显示菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"You clicked Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You cliked Remove",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }


}
