package com.example.wmm.activitytest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG="tag_base";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,getClass().getSimpleName()+" onCreate");
        Log.d(TAG,"Task id is "+getTaskId());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,getClass().getSimpleName()+" onDestory");
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,getClass().getSimpleName()+" onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,getClass().getSimpleName()+" onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,getClass().getSimpleName()+" onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,getClass().getSimpleName()+" onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,getClass().getSimpleName()+" onRestart");
    }


}
