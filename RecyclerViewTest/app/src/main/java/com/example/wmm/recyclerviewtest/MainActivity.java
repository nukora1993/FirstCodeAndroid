package com.example.wmm.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Fruit> fruitList=new ArrayList<>();

//    private void initFruits(){
//        for (int i = 0; i <2 ; i++) {
//            Fruit apple=new Fruit("Apple",R.drawable.ic_launcher_background);
//            fruitList.add(apple);
//            Fruit banana=new Fruit("Banana",R.drawable.ic_launcher_background);
//            fruitList.add(banana);
//            Fruit orange=new Fruit("Orange",R.drawable.ic_launcher_background);
//            fruitList.add(orange);
//            Fruit watermelon=new Fruit("WaterMelon",R.drawable.ic_launcher_background);
//            fruitList.add(watermelon);
//            Fruit pear=new Fruit("Pear",R.drawable.ic_launcher_background);
//            fruitList.add(pear);
//            Fruit grape=new Fruit("grape",R.drawable.ic_launcher_background);
//            fruitList.add(grape);
//        }
//    }
    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

    private void initFruits(){
        for (int i = 0; i <2 ; i++) {
            Fruit apple=new Fruit(getRandomLengthName("Apple"),R.drawable.ic_launcher_background);
            fruitList.add(apple);
            Fruit banana=new Fruit(getRandomLengthName("Banana"),R.drawable.ic_launcher_background);
            fruitList.add(banana);
            Fruit orange=new Fruit(getRandomLengthName("Orange"),R.drawable.ic_launcher_background);
            fruitList.add(orange);
            Fruit watermelon=new Fruit(getRandomLengthName("WaterMelon"),R.drawable.ic_launcher_background);
            fruitList.add(watermelon);
            Fruit pear=new Fruit(getRandomLengthName("Pear"),R.drawable.ic_launcher_background);
            fruitList.add(pear);
            Fruit grape=new Fruit(getRandomLengthName("grape"),R.drawable.ic_launcher_background);
            fruitList.add(grape);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        //相较于ListView，RecyclerView多了一个LayoutManager，用于设置其布局方式，因为他不仅能够像ListView实现上下，也能够横向等
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        //设置水平展示
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager layoutManager=new
                StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


    }
}
