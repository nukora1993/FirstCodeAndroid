package com.example.wmm.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] data={"Apple","Banana","Orange","Watermelon",
    "Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
            "Apple","Banana","Orange","Watermelon",
            "Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};

    private List<Fruit> fruitList=new ArrayList<>();

    private void initFruits(){
        for (int i = 0; i <2 ; i++) {
            Fruit apple=new Fruit("Apple",R.drawable.ic_launcher_background);
            fruitList.add(apple);
            Fruit banana=new Fruit("Banana",R.drawable.ic_launcher_background);
            fruitList.add(banana);
            Fruit orange=new Fruit("Orange",R.drawable.ic_launcher_background);
            fruitList.add(orange);
            Fruit watermelon=new Fruit("WaterMelon",R.drawable.ic_launcher_background);
            fruitList.add(watermelon);
            Fruit pear=new Fruit("Pear",R.drawable.ic_launcher_background);
            fruitList.add(pear);
            Fruit grape=new Fruit("grape",R.drawable.ic_launcher_background);
            fruitList.add(grape);




        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,data);
        initFruits();
        FruitAdapter adapter=new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit=fruitList.get(position);
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
