package com.aec188.budget.http.helper;

import android.os.Handler;
import android.os.Message;

/**
 * Created by zhangzhi on 2016/10/30.
 * 晓材
 */

public abstract class ProgressHandle extends Handler implements ProgressListener {



    @Override
    public final void handleMessage(Message msg) {
        if (msg.what == 0) {
            Obj obj = (Obj) msg.obj;
            onProgress(obj.progress, obj.total, obj.done);
        }
    }


    public void sendMessage(long progress, long total, boolean done) {
        Message message = new Message();
        message.what = 0;
        message.obj = new Obj(progress, total, done);
        sendMessage(message);
    }

    private static class Obj {
        long progress;
        long total;
        boolean done;

        public Obj(long progress, long total, boolean done) {
            this.progress = progress;
            this.total = total;
            this.done = done;
        }
    }
}
