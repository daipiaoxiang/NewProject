package com.aec188.budget.http;

import com.aec188.budget.pojo.basic.CommonModel;
import com.aec188.budget.pojo.Msg;
import com.aec188.budget.utils.TDevice;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer-X on 2016/11/17.
 */
public abstract class CommonCB<T> implements Callback<CommonModel<T>> {

    @Override
    public void onResponse(Call<CommonModel<T>> call, Response<CommonModel<T>> response) {
        try {
            if (response.isSuccessful()) {
                CommonModel<T> tCommonModel = response.body();
                if (tCommonModel.isSuccess()) {
                    success(tCommonModel.getData());
                } else {
                    error(tCommonModel.getMsg());
                }
            } else {
                Msg msg = null;
                try {
                    msg = new Gson().fromJson(response.errorBody().string(), Msg.class);
                    error(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = new Msg();
                    msg.setCode(-1);
                    msg.setMsg(e.getMessage());
                    error(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error(new Msg(-1, "网络错误请重试"));
        }

    }

    @Override
    public void onFailure(Call<CommonModel<T>> call, Throwable t) {
        Msg msg = new Msg();
        msg.setCode(-2);
        msg.setMsg(t.getMessage());
        if (t instanceof TimeoutException) {
            msg.setMsg("网络连接超时");
        } else if (!TDevice.hasInternet()) {
            msg.setMsg("网络错误");
        } else {
            msg.setMsg("网络连接错误，请重试");
        }
        error(msg);
    }

    public abstract void success(T t);

    public abstract void error(Msg msg);
}
