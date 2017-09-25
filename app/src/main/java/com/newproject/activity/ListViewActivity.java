package com.newproject.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.R;
import com.newproject.adapter.ZQuickAdapter;
import com.newproject.adapter.ZViewHolder;
import com.newproject.base.BaseActivity;
import com.newproject.utils.MVCHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Developer-D on 2017/9/25.
 */

public class ListViewActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    private MVCHelper<String> mvcHelper;
    @BindView((R.id.swipe_refresh))
    SwipeRefreshLayout swipeRefreshLayout;
    ZQuickAdapter<String> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new ZQuickAdapter<String>(R.layout.item_list, getData()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.title, item);
            }
        };
        mRecyclerView.setAdapter(adapter);
        mvcHelper = new MVCHelper<>(swipeRefreshLayout, mRecyclerView, adapter);
        ZViewHolder emptyHolder = new ZViewHolder(View.inflate(this, R.layout.view_empty, null));
        mvcHelper.setEmptyView(emptyHolder);
        mvcHelper.setLoadData(new MVCHelper.LoadData() {
            @Override
            public void loadData(int page) {
//                mvcHelper.loadCompleted(List<String>);
//                mvcHelper.loadFailed();
            }

        });
        mvcHelper.refresh();
    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item" + i);
        }
        return list;
    }

}
