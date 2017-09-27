package com.newproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.newproject.bean.User;
import com.newproject.widget.TDevice;
import com.newproject.widget.TLog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Developer-D on 2017/9/22.
 */

public class MyApp extends Application{
    private static MyApp _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initImageLoader();
        TDevice.init(this);
    }

    private void initImageLoader() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_default_head)
                .showImageForEmptyUri(R.drawable.ic_default_head)
                .showImageOnLoading(R.drawable.ic_default_head)

                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisc(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                this).imageDownloader(new BaseImageDownloader(this) {

            @Override
            protected HttpURLConnection createConnection(String url,
                                                         Object extra) throws IOException {
                HttpURLConnection conn = super.createConnection(url, extra);
                conn.setRequestProperty("User-agent", "Mozilla/4.0");
                return conn;
            }

        }).defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(configuration);
    }

    public static DisplayImageOptions headImageOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_default_head)
                .showImageForEmptyUri(R.drawable.ic_default_head)
                .showImageOnLoading(R.drawable.ic_default_head)
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true).build();
        return options;
    }

    public static MyApp getApp() {
        return _instance;
    }

    private User _user;

    public User getUser() {
        if (_user != null) {
            return _user;
        }
        _user = new User();
        try {
            SharedPreferences sp = getSharedPreferences("login",
                    Context.MODE_PRIVATE);
//            _user.setUserPhone(sp.getString("userPhone", null));
//            _user.setToken(sp.getString("token", null));
        } catch (Exception e) {
            TLog.e(e.getMessage());
        }
        return _user;
    }


    public void saveUser(User u) {
        _user = u;
        SharedPreferences sp = getSharedPreferences("login",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("userPhone", u.getUserPhone());
//        editor.putString("token", u.getToken()).commit();
    }

    public boolean isLogin() {
        return _islogin;
    }

    public void login(User u) {
        _islogin = true;
        saveUser(u);
    }

    private boolean _islogin = false;

    public void logout() {
        // 这里删除cookie
        _islogin = false;
        SharedPreferences sp = getSharedPreferences("login",
                Context.MODE_PRIVATE);
        sp.edit().remove("token").commit();

        _user = null;
    }
}
