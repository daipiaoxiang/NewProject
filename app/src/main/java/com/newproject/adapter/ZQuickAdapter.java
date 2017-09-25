package com.newproject.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Developer-X on 2016/12/2.
 */
public abstract class ZQuickAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {
    public ZQuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public ZQuickAdapter(List<T> data) {
        super(data);
    }
}
