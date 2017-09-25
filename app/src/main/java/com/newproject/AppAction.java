package com.newproject;

/**
 * Created by Developer-X on 2017/2/28.
 */
public class AppAction {
    public static final String ACTION = "com.new.project.action";
    public static final String PAY_SUCCESS = "pay_success";
    public static boolean equ(String action, String... strings) {
        for (String s : strings) {
            if (s.equals(action)) {
                return true;
            }
        }

        return false;
    }
}
