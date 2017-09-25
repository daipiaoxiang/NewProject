package com.newproject;

/**
 * Created by Developer-D on 2017/9/22.
 */

public interface AppConfig {
    boolean DEBUG = BuildConfig.DEBUG;

    public static final String BASE_URLS = "https://m.pcw365.com/budget/";  //正式服务器

    public static String ALIAPY_NOTIFY_URL = BASE_URLS + "AskPriceApi/pay_mob/notify_url.php";


    public static class WeiXinPayResult {
        public final static int success = 0;
        public final static int fail = -1;
        public final static int cancel = -2;
    }
}
