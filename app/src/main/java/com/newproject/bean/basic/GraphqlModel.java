package com.newproject.bean.basic;

import com.google.gson.annotations.Expose;

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
