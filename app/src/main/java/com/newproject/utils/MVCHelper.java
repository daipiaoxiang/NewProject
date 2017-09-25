package com.newproject.utils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newproject.R;
import com.newproject.adapter.ZQuickAdapter;
import com.newproject.adapter.ZViewHolder;
import com.newproject.widget.Toast;

import java.util.List;

public class MVCHelper<T> {

    public interface LoadData {
        void loadData(int page);
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadData loadData;
    private RecyclerView recyclerView;
    private ZQuickAdapter<T> adapter;


    private final static int STATUS_NONE = 0;
    private final static int STATUS_REFRESH = 1;
    private final static int STATUS_LOADING_MORE = 2;
    private final static int STATUS_NO_MORE = 3;

    private int status = STATUS_NONE;
    private boolean hasMore = false;
//    private final static int STATUS_NONE = 0;
//    private final static int STATUS_NONE = 0;

    private int pageSize = 20;//设置默认每页请求的数据
    private ZViewHolder emptyView;

    private int currentPage = 1;

    public MVCHelper(SwipeRefreshLayout layout, final RecyclerView recyclerView, final ZQuickAdapter<T> adapter) {
        swipeRefreshLayout = layout;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (status == STATUS_REFRESH) {
                            return;
                        }
                        swipeRefreshLayout.setEnabled(false);
                        status = STATUS_LOADING_MORE;
//                        adapter.setEnableLoadMore(false);
                        loadData.loadData(currentPage + 1);
                    }
                });


            }
        });

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ZViewHolder getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(ZViewHolder emptyView) {
        this.emptyView = emptyView;
        emptyView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    public void setLoadData(LoadData l) {
        loadData = l;
    }

    public void refresh() {
        if (status == STATUS_LOADING_MORE) {
            return;
        }
        status = STATUS_REFRESH;//正在刷新中。。。
        if (adapter.getData().isEmpty()) {
            emptyView.setVisible(R.id.empty_loading, true);
            emptyView.setVisible(R.id.empty_no_data, false);
            emptyView.setVisible(R.id.empty_load_fail, false);
            adapter.setEmptyView(emptyView.itemView);
        }
        adapter.setEnableLoadMore(false);//刷新中不能加载更多
        loadData.loadData(1);
    }

    public void loadCompleted(List<T> data) {
        swipeRefreshLayout.setEnabled(true);
        if (status == STATUS_REFRESH) {
            swipeRefreshLayout.setRefreshing(false);
            //刷新结束
            status = STATUS_NONE;
            currentPage = 1;
            adapter.setNewData(data);
        } else if (status == STATUS_LOADING_MORE) {
            status = STATUS_NONE;
            adapter.addData(data);
            currentPage++;//页面数据需要 加1
        }

        if (adapter.getData().isEmpty()) {
            emptyView.setVisible(R.id.empty_loading, false);
            emptyView.setVisible(R.id.empty_no_data, true);
            emptyView.setVisible(R.id.empty_load_fail, false);
            adapter.setEmptyView(emptyView.itemView);
            hasMore = false;
            adapter.setEnableLoadMore(false);
            return;
        }


        if (data.size() < pageSize) {
            status = STATUS_NO_MORE;
            hasMore = false;
            adapter.loadMoreEnd();
        } else {
            hasMore = true;
            adapter.loadMoreComplete();
        }
        adapter.setEnableLoadMore(hasMore);

    }

    public void loadFailed() {
        adapter.setEnableLoadMore(hasMore);
        if (status == STATUS_REFRESH) {
            swipeRefreshLayout.setRefreshing(false);
            if (adapter.getData().isEmpty()) {
                emptyView.setVisible(R.id.empty_loading, false);
                emptyView.setVisible(R.id.empty_no_data, false);
                emptyView.setVisible(R.id.empty_load_fail, true);
                adapter.setEmptyView(emptyView.itemView);
            } else {
                Toast.show("加载失败请稍后重试");//TODO 中英双语
            }
        } else {
            adapter.loadMoreFail();
        }
    }


}
