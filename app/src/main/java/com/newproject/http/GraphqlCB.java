package com.aec188.budget.http;

import com.aec188.budget.pojo.Msg;
import com.aec188.budget.pojo.basic.GraphqlModel;
import com.aec188.budget.utils.TDevice;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Developer-X on 2016/11/17.
 */
public abstract class GraphqlCB<T> implements Callback<GraphqlModel<T>> {

    @Override
    public void onResponse(Call<GraphqlModel<T>> call, Response<GraphqlModel<T>> response) {
        try {
            if (response.isSuccessful()) {
                T t = response.body().get();
                if (t == null) {
                    Msg msg = new Msg();
                    msg.setMsg("请求失败");
                    error(msg);
                } else {
                    success(t);
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

    public abstract void success(T t);

    public abstract void error(Msg msg);

    @Override
    public void onFailure(Call<GraphqlModel<T>> call, Throwable t) {
        t.printStackTrace();
        Msg msg = new Msg();
        msg.setCode(-2);
        msg.setMsg(t.getMessage());
        if (t instanceof SocketTimeoutException) {
            msg.setMsg("网络连接超时");
        } else if (!TDevice.hasInternet()) {
            msg.setMsg("网络错误");
        }
//        if(TextUtils.isEmpty(msg.getMsg())){
//            msg.setMsg("服务器错误");
//        }
        error(msg);
    }
}
