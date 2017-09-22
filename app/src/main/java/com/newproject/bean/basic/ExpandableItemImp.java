package com.newproject.bean.basic;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;


public class ExpandableItemImp<T,E> extends AbstractExpandableItem<E> implements MultiItemEntity {
    public final T t;
    private final int level;
    private final int type;
    public ExpandableItemImp(T t,int type, int level) {
        this.t = t;
        this.level = level;
        this.type = type;
    }
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
