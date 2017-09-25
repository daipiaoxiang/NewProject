package com.newproject.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newproject.R;
import com.newproject.base.BaseActivity;
import com.newproject.bean.User;
import com.newproject.view.ClearEditText;
import com.newproject.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Developer-D on 2017/9/25.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.user)
    ClearEditText edUser;
    @BindView(R.id.pwd)
    ClearEditText edPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView((R.id.title))
    protected TextView mTitle;
    private User user;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(null);
        mTitle.setText("登录");
        user = new User();
        textChange tc1 = new textChange();
        edUser.addTextChangedListener(tc1);
        edPwd.addTextChangedListener(tc1);
    }

    @Override
    public boolean hasActionBar() {
        return false;
    }

    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        if (edUser.getText2().equals("123456") && edPwd.getText2().equals("123456")) {
            user.setUserName(edUser.getText2());
            user.setPassword(edPwd.getText2());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.show("账号或密码错误,请重新输入");
        }
    }

    private boolean isCheckEmpty() {
        boolean bl = false;
        if (TextUtils.isEmpty(edUser.getText2())) {
            bl = false;
            Toast.show("用户名不为空");
        } else if (TextUtils.isEmpty(edPwd.getText2())) {
            bl = false;
            Toast.show("密码不为空");
        } else {
            bl = true;
        }
        return bl;
    }
    class textChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {

            boolean Sign1 = edUser.getText2().length() > 0;

            boolean Sign2 = edPwd.getText().length() > 0;
            if (Sign1 && Sign2) {
                btnLogin.setAlpha(1f);
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setAlpha(0.5f);
                btnLogin.setEnabled(false);
            }

        }

    }
}
