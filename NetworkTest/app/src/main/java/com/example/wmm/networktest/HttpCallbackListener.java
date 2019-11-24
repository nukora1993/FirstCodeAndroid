package com.example.wmm.networktest;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
