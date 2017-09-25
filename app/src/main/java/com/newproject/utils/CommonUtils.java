package com.newproject.utils;

/**
 * Created by Developer-X on 2016/5/23.
 *
 * 防止重复点击工具类
 */
public class CommonUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
