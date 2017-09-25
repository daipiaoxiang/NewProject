package com.newproject.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newproject.AppAction;
import com.newproject.R;
import com.newproject.utils.GsonUtils;
import com.newproject.widget.TLog;

import java.lang.reflect.Type;

import butterknife.ButterKnife;

/**
 * Created by Developer-D on 2017/9/22.
 */

public abstract class BaseActivity extends AppCompatActivity{

    public TextView title;
    public Button mLeftButton;
    public Button mRightButton;
    public ImageView mRightImg;
    public TextView mRightTxt;
    public TextView mTip;

    protected Context mContext;
    private BroadcastReceiver mCommonReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        View contentView = View.inflate(this, getLayoutId(), null);
        if (hasActionBar()) {
            if (contentView instanceof LinearLayout) {
                View actionBar = View.inflate(this, R.layout.base_title, null);
                ((LinearLayout) contentView).addView(actionBar, 0);
                title = ButterKnife.findById(actionBar, R.id.action_title);
                mLeftButton = ButterKnife.findById(actionBar, R.id.left_button);
                mRightButton = ButterKnife.findById(actionBar, R.id.right_button);
                mRightImg = ButterKnife.findById(actionBar, R.id.right_img);
                mRightTxt = ButterKnife.findById(actionBar, R.id.right_txt);
                title.setText(getTitle());
                mLeftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        finish();
                        onBackPressed();
                    }
                });
            } else {
                throw new RuntimeException("根布局必须是 线性布局");
            }
        }
        setContentView(contentView);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        registerEvents();
        if (needBroadcastReciver()) {
            mCommonReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getStringExtra("action");
                    onBroadcastReceive(action, context, intent);
                    TLog.e("broadcast", action);
                }
            };
            registerReceiver(mCommonReceiver,
                    new IntentFilter(AppAction.ACTION));
        }
    }
    protected boolean initView(Bundle savedInstanceState) {
        return false;
    }

    protected void initView() {
    }
    public boolean hasActionBar() {
        return true;
    }
    protected abstract int getLayoutId();

    protected boolean needBroadcastReciver() {
        return true;
    }

    protected void onBroadcastReceive(String action, Context context,
                                      Intent intent) {
        // 如果没有被父类重写，第一次接收到广播就把自己解除注册
        cancelBroadcastReceiver();
    }

    public void cancelBroadcastReceiver() {
        if (mCommonReceiver != null) {
            unregisterReceiver(mCommonReceiver);
            mCommonReceiver = null;
        }
    }

    protected void registerEvents() {
    }
    public void startActivity(Class clazz, Object... objects) {

        startActivity(get(clazz, objects));
    }

    private Intent get(Class clazz, Object... objects) {
        Intent intent = new Intent(mContext, clazz);

        for (int i = 0; i < objects.length; i++) {
            intent.putExtra("data" + i, GsonUtils.defaultGson().toJson(objects[i]));
        }
        return intent;
    }

    public void startActivity(int result, Class clazz, Object... objects) {
        startActivityForResult(get(clazz, objects), result);
    }

    public <T> T getObject(Class<T> clazz, int index) {
        String str = getIntent().getStringExtra("data" + index);
        return GsonUtils.defaultGson().fromJson(str, clazz);
    }
    public <T> T getObject(Class<T> clazz) {
        return getObject(clazz, 0);
    }
    public <T> T getObject(Type type, int index) {
        String str = getIntent().getStringExtra("data" + index);
        return GsonUtils.defaultGson().fromJson(str, type);
    }

    public <T> T getObject(Type clazz) {
        return getObject(clazz, 0);
    }
    @Override
    protected void onDestroy() {
        if (mCommonReceiver != null) {
            unregisterReceiver(mCommonReceiver);
            mCommonReceiver = null;
        }
        super.onDestroy();
    }
}
