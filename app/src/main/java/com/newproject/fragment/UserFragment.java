package com.newproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.newproject.R;
import com.newproject.activity.ListViewActivity;
import com.newproject.activity.PersonageActivity;
import com.newproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Developer-D on 2017/9/22.
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.txt)
    TextView txt;
    private int num;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    public static UserFragment getInstance(int num) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        num=bundle.getInt("num");

    }

    @Override
    protected void initView(View v) {
        mToolbar.setNavigationIcon(null);
        mTitle.setText("我的");
        txt.setText(""+num);
    }
    @OnClick({R.id.btn_submit,R.id.btn_photo})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_submit:
                startActivity(new Intent(getActivity(), ListViewActivity.class));
                break;
            case R.id.btn_photo:
                startActivity(new Intent(getActivity(), PersonageActivity.class));
                break;
        }
    }
}
