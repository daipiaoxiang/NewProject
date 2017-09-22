package com.newproject.http.helper;

public interface ProgressListener {
    void onProgress(long progress, long total, boolean done);
}
