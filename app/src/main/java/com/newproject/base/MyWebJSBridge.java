package com.newproject.base;

import android.webkit.JavascriptInterface;


/**
 * Created by Tye on 2016/10/21.
 */

public class MyWebJSBridge implements WebJSBridge {
    final BaseActivity activity;

    MyWebJSBridge(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    @JavascriptInterface
    public String getToken() {
//        return MyApp.getApp().getUser().getToken();
    return "";
    }
}
