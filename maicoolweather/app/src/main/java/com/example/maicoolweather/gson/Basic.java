package com.example.maicoolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    //使用注解建立json和java bean属性映射关系
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
