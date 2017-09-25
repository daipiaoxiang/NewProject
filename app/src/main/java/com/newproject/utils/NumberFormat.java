package com.newproject.utils;

import android.text.Html;

import java.text.DecimalFormat;

public class NumberFormat {
    // public static String formatRound(double data) {
    // DecimalFormat df = new DecimalFormat("#0.00");
    // return df.format(data);
    // }
    public static double parseDouble(String s) {
        if (s == null) {
            return 0;
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static CharSequence formatSpecialPrice(double f, String end) {
        return formatSpecialPrice(f, "￥", end);
    }

    public static CharSequence formatSpecialPrice(double f, String start,
                                                  String end) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String s = df.format(f);
        String[] ss = s.split("\\.");
        if (ss.length != 2) {
            return "";
        }
        if (end == null) {
            end = "";
        }
        if (start == null) {
            start = "";
        }
        return Html.fromHtml("<small>" + start + "</small>" + ss[0]
                + ".<small>" + ss[1] + end + "</small>");
    }

    public static String formatPrice(float f) {
        return String.format("%.02f", f);
    }
    public static String formatPrice(double d)
    {
        return String.format("%.02f",d);
    }

}
