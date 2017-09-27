package com.newproject.fragment;

import android.view.View;

import com.newproject.R;
import com.newproject.base.BaseFragment;

/**
 * Created by Developer-D on 2017/9/26.
 */

public class OrderFragment extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView(View v) {
        mToolbar.setNavigationIcon(null);
        mTitle.setText("订单");
    }
}
