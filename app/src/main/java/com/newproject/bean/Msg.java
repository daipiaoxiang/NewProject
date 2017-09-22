package com.aec188.budget.pojo;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

/**
 * Created by Developer-X on 2016/11/14.
 */
public class Msg {
    @Expose
    private int code;
    @Expose
    private String msg;
    private String error;
    private String err;

    public Msg(int code, String err) {
        this.code = code;
        this.msg = err;
    }

    public Msg() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? (TextUtils.isEmpty(error) ? err : error) : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return getMsg();
    }
}
