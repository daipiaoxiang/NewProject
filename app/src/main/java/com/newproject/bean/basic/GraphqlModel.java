package com.aec188.budget.pojo.basic;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Tye on 2016/12/21.
 */

public class GraphqlModel<T> {

    @Expose
    public D<T> data;

    private static class D <T> {
        @Expose
        private T data;
    }

    public T get() {
        return data==null?null:data.data;
    }
}
