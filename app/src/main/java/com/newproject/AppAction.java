package com.aec188.minicad;

/**
 * Created by Developer-X on 2017/2/28.
 */
public class AppAction {
    public static final String ACTION = "com.aec188.pcw_store.commone_action";
    public static final String PAY_SUCCESS = "pay_success";
    public static final String ORDER_STATUS_UPDATE = "orderStatusUpdate";
    public static class WeiXinPayResult {
        public final static int success = 0;
        public final static int fail = -1;
        public final static int cancel = -2;
    }
    public static boolean equ(String action, String... strings) {
        for (String s : strings) {
            if (s.equals(action)) {
                return true;
            }
        }

        return false;
    }
}
