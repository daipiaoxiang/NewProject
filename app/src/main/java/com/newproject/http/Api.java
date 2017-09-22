package com.aec188.budget.http;

import android.util.Log;

import com.aec188.budget.AppConfig;
import com.aec188.budget.BuildConfig;
import com.aec188.budget.MyApp;
import com.aec188.budget.http.helper.ProgressHandle;
import com.aec188.budget.utils.GsonUtils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangzhi on 2016/10/30.
 * 晓材
 */

public class Api {
    static ApiService _instance = null;

    public static ApiService service() {
        if (_instance == null) {

            OkHttpClient client = new OkHttpClient.Builder()
//                    .cookieJar(new PersistentCookieJar())
                    .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getApp())))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new LoggingInterceptro())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
//                                    .addHeader("authorization", "Bearer "+ DataManager.get(DataManager.TOKEN))
//                                    .addHeader("Accept","*/*")
                                    .addHeader("device", "android")
                                    .addHeader("app", "budget_lite")
                                    .addHeader("version", BuildConfig.VERSION_NAME)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .cache(new Cache(new File(MyApp.getApp().getCacheDir(), "httpCache"), 1024 * 1024 * 100))
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(AppConfig.BASE_URL)
                    .baseUrl(AppConfig.BASE_URLS + "api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(GsonUtils.defaultGson()))
//                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            _instance = retrofit.create(ApiService.class);
        }
        return _instance;
    }

    private static ProgressHandle handle;

    public static void setHandle(ProgressHandle h) {
        handle = h;
    }

    public static class LoggingInterceptro implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            //请求链接，请求
            Request request = chain.request();
            long t1 = System.nanoTime();
            Response response = chain.proceed(request);
            double t2 = (System.nanoTime() - t1) / 1e6d;

            if (request.url().encodedPath().contains("files")) {
                return response;//不解析文件流
            }

            String responseBodyStr = response.body().string();
            MediaType contentType = response.body().contentType();
            ResponseBody body = ResponseBody.create(contentType, responseBodyStr);

            String requestBodyStr = "";
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                if (copy.body() != null) {
                    copy.body().writeTo(buffer);
                    requestBodyStr = buffer.readUtf8();
                }
            } catch (final IOException e) {
//                return "did not work";
            }

            Log.i("response:", String.format(
                    "\nrequest:\n\turi:%s\n\t\tmethod:%s\n\t\tbody:%s\nresponse:\n\t\tcode:%s\n\t\ttime:%s ms\n\t\tbody:\n%s\n",
                    request.url(), request.method(), requestBodyStr,
                    response.code(), t2, responseBodyStr));
//            TLog.d(request.body());
            return response.newBuilder().body(body).build();
        }
    }

}
