package com.example.wmm.recyclerviewtest;

public class Fruit {
    private String name;
    private int imageId;
    public Fruit(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }


}
