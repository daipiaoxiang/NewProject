package com.newproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhangzhi on 2016/11/2.
 * 晓材
 */

public class GsonUtils {
    private static Gson _gson;

    public static Gson defaultGson() {
        if (_gson != null) return _gson;
        _gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .registerTypeAdapter(Date.class, new _Date())
                .registerTypeAdapter(double.class, new _Double())
                .excludeFieldsWithoutExposeAnnotation()
//                .serializeNulls() //智能null
//                .setPrettyPrinting()// 调教格式
                .create();
        return _gson;
    }

    private static class _Double implements JsonDeserializer<Double> {

        @Override
        public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return json.getAsDouble();
            } catch (Exception e) {
                return 0.0;
            }
        }

    }

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static {
        sf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    private static class _Date implements JsonSerializer<Date>, JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                long time = json.getAsLong();
                return new Date(time * 1000);
            } catch (Exception e) {
                String time = json.getAsString();
                try {
                    return sf.parse(time);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                    throw new RuntimeException("time parse error");
                }
//                return new Date(Long.parseLong(time) * 1000);
            }
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            JsonElement jsonElement = new JsonPrimitive(src.getTime() / 1000);
            return jsonElement;
        }
    }
}
