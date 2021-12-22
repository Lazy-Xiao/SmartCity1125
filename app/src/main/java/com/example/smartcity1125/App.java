package com.example.smartcity1125;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.smartcity1125.bean.xinxibean;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class App extends Application {
    public static OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    public static String token="";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
