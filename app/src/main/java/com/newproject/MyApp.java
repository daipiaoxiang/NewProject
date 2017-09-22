package com.newproject;

import android.app.Application;

/**
 * Created by Developer-D on 2017/9/22.
 */

public class MyApp extends Application{
    private static MyApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
//        initImageLoader();
    }


    public static MyApp getApp() {
        return _instance;
    }

}
