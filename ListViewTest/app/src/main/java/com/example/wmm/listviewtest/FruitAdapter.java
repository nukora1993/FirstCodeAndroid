package com.example.wmm.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;

    public FruitAdapter(Context ctx, int textViewResourceId, List<Fruit> objects){
        super(ctx,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获得列表元素
        Fruit fruit=getItem(position);
        //获得单项的view，第三个参数为false，暂时含义可以理解为不设置单项view的父布局，因为一旦一个view设置有父布局，那么无法被添加到listview
        //这种方法每次加载都需要重新绘制效率较低
        //        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        //convertView是缓存的view，可以利用该view提升效率
        View view;
        ViewHolder viewHolder;


        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
            viewHolder.fruitName=(TextView)view.findViewById(R.id.fruit_name);
            //使用Holder的关键是使用setTag将Holder保存在view内部，setTagk可以接受任何一个object
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        //获得单项中的imageView和TextView引用并且设置其中的属性
//        ImageView fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
//        TextView fruitName=(TextView)view.findViewById(R.id.fruit_name);

        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText(fruit.getName());
        return view;
    }

    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
