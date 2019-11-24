package com.example.wmm.uilayouttest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TitleLayout extends LinearLayout {
    public TitleLayout(Context ctx, AttributeSet atts){
        super(ctx,atts);
        LayoutInflater.from(ctx).inflate(R.layout.title,this);
        Button titleBack=(Button)findViewById(R.id.title_back);
        Button titleEdit=(Button)findViewById(R.id.title_eidt);

        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You clicked Edit Button",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
