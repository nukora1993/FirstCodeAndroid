package com.example.wmm.activitytest;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//为了让intent能够直接传递对象，必须实现serializable接口
//public class Person implements Serializable {
//    private String name;
//    private int age;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//
//}

//不同于serializable接口，Parcelable使用分解成基本类型的方式，这样更加灵活，但是代码稍微复杂
public class Person implements Parcelable{
    private String name;
    private int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Parcelable.Creator<Person> CREATOR=new Parcelable.Creator<Person>(){
        @Override
        public Person createFromParcel(Parcel source) {
            Person person=new Person();
            //这里没有key的概念，所以读取顺序必须和写入顺序完全一致
            person.name=source.readString();
            person.age=source.readInt();
            return person;
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}