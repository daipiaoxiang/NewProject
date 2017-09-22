package com.newproject.bean.basic;

import com.chad.library.adapter.base.entity.MultiItemEntity;


public class MultiItemEntityImp<T,P> implements MultiItemEntity {
    private final int type;
    public final T t;
    public final P p;
    public MultiItemEntityImp(T t,int type) {
        this(t,type,null);
    }
    public MultiItemEntityImp(T t,int type,P p) {
        this.type = type;
        this.t = t;
        this.p = p;
    }


    @Override
    public int getItemType() {
        return type;
    }
}
