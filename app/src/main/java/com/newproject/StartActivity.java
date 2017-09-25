package com.newproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.WindowManager;

import com.newproject.activity.GuideActivity;
import com.newproject.activity.LoginActivity;
import com.newproject.base.BaseActivity;
import com.newproject.widget.TDevice;


public class StartActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        TDevice.init(getApplication());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(
                        "pcw_store", MODE_PRIVATE);

                int version = preferences.getInt("version", 0);
//				version = 0;
                Intent intent = null;
                if (version != TDevice.getVersionCode()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("version", TDevice.getVersionCode());
                    editor.commit();
                    intent = new Intent(StartActivity.this, GuideActivity.class);
                } else {
                    intent = new Intent(StartActivity.this, LoginActivity.class);
//                    intent = new Intent(StartActivity.this, MainActivity.class);
                }
                if (intent != null) {
                    startActivity(intent); // 处理跳转动画，和关闭动画
                    finish();
                }
            }

        }, 1000);

//        MyApp.getApp().autoLogin();
    }

    //    public void login() {
//        if (TextUtils.isEmpty(MyApp.getApp().getUser().getPassword()) && TextUtils.isEmpty(MyApp.getApp().getUser().getMobile())) {
//            return;
//        }
//        Api.service().login(MyApp.getApp().getUser().getMobile(), MyApp.getApp().getUser().getPassword()).enqueue(new CB<User>() {
//
//            @Override
//            public void success(User user) {
//                user.setPassword(MyApp.getApp().getUser().getPassword());
//                SharedPreferencesManager.setToken(getApplicationContext(), user.getToken());
//                MyApp.getApp().login(user);
//            }
//
//            @Override
//            public void error(AppError error) {
//
//            }
//        });
//    }
    @Override
    public boolean hasActionBar() {
        return false;
    }

}
