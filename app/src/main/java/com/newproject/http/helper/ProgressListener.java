package com.aec188.budget.http.helper;

/**
 * Created by zhangzhi on 2016/10/30.
 * 晓材
 */

public interface ProgressListener {
    void onProgress(long progress, long total, boolean done);
}
