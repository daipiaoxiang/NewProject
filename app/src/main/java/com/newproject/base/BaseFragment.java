package com.newproject.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newproject.R;
import com.newproject.utils.GsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer-D on 2017/9/22.
 */

public abstract class BaseFragment extends Fragment{
    protected abstract int getLayoutId();

    protected void initView(View v) {
    }

    protected Context mContext;
    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @Nullable
    @BindView((R.id.title))
    protected TextView mTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
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
}
