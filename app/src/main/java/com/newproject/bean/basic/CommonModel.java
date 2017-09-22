package com.aec188.budget.pojo.basic;

import com.aec188.budget.pojo.Msg;
import com.google.gson.annotations.Expose;

/**
 * Created by Developer-X on 2016/11/16.
 */
public class CommonModel <T> {
    @Expose
    private boolean success;
    @Expose
    private Error error;
    @Expose
    private Msg msg;
    @Expose
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Msg getMsg() {
        if (msg == null) {
            msg= new Msg();
            if (error!= null) {
                msg.setCode(error.code);
                msg.setMsg(error.msg);
            }
        }
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    static class Error {
        public String msg;
        public int code;

    }

    @Override
    public String toString() {
        return "CommonModel{" +
                "success=" + success +
//                ", error=" + error +
                "data," + data+
                ", msg=" + msg +
                '}';
    }
}
