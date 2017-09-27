package com.newproject.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.R;
import com.newproject.adapter.ZQuickAdapter;
import com.newproject.adapter.ZViewHolder;
import com.newproject.base.BaseFragment;
import com.newproject.utils.MVCHelper;
import com.newproject.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Developer-D on 2017/9/26.
 */

public class ManageFragment extends BaseFragment{

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    private MVCHelper<String> mvcHelper;
    @BindView((R.id.swipe_refresh))
    SwipeRefreshLayout swipeRefreshLayout;
    ZQuickAdapter<String> adapter;

//    @BindView(R.id.action_bar)
//    View actionBar;
//    @BindView(R.id._btn_name)
//    View btnSearch;
//    @BindView(R.id.ptr_layout)
//    PtrFrameLayout ptrFrameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @Override
    protected void initView(View v) {
        mToolbar.setNavigationIcon(null);
        mTitle.setText("客户");
//        swipeRefreshLayout.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));
        adapter = new ZQuickAdapter<String>(R.layout.item_list, getData()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.title, item);
            }
        };
        mRecyclerView.setAdapter(adapter);
        mvcHelper = new MVCHelper<>(swipeRefreshLayout, mRecyclerView, adapter);
        ZViewHolder emptyHolder = new ZViewHolder(View.inflate(getActivity(), R.layout.view_empty, null));
        mvcHelper.setEmptyView(emptyHolder);
        mvcHelper.setLoadData(new MVCHelper.LoadData() {
            @Override
            public void loadData(int page) {
//                mvcHelper.loadCompleted(List<String>);
//                mvcHelper.loadFailed();
            }

        });
        mvcHelper.refresh();
//        ptrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
////                frame.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        ptrFrameLayout.refreshComplete();
////                    }
////                }, 1800);
//                ptrFrameLayout.refreshComplete();
//            }
//
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
//            }
//        });
//        handleActionBar();
    }
//    private void handleActionBar() {
//
//        ptrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
//            @Override
//            public void onUIReset(PtrFrameLayout frame) {
//
//            }
//
//            @Override
//            public void onUIRefreshPrepare(PtrFrameLayout frame) {
//
//            }
//
//            @Override
//            public void onUIRefreshBegin(PtrFrameLayout frame) {
//
//            }
//
//            @Override
//            public void onUIRefreshComplete(PtrFrameLayout frame) {
//
//            }
//
//            @Override
//            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
//                TLog.e(ptrIndicator.getCurrentPercent());
//                if (ptrIndicator.getCurrentPercent() > 0.0000001) {
//                    actionBar.setVisibility(View.GONE);
//                } else {
//                    actionBar.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                View view = recyclerView.getLayoutManager().findViewByPosition(0);
//                if (view != null) {
//
////                    Toast.show(view.getTop()+"");
//                    TLog.e(view.getTop() + " " + ptrFrameLayout.getHeaderView().getBottom());
//                    int alpha = 0;
//                    alpha = -view.getTop() * 512 / view.getHeight();
//                    alpha = Math.min(255, alpha);
//                    alpha = Math.max(0, alpha);
//                    float al = alpha * 0.2f / 255 + 0.8f;
//
//                    btnSearch.setAlpha(al);
//                    actionBar.setBackgroundResource(R.color.colorPrimaryDark);
////                    actionBar.setBackgroundColor(Color.argb(alpha, 0, 172, 255));
//                } else {
//                    actionBar.setBackgroundResource(R.color.colorPrimaryDark);
//                    btnSearch.setAlpha(1);
//                }
//
//
//            }
//        });
//    }

    private List<String> getData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item" + i);
        }
        return list;
    }
}
