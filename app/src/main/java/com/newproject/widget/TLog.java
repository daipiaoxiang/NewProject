package com.aec188.minicad.widget;

import android.util.Log;

import com.aec188.minicad.AppConfig;


public class TLog {
	public static final String LOG_TAG = "tag";
	public static final boolean Debug = AppConfig.DEBUG;

	public static final void d(Object log) {
		if (Debug)
			Log.d(LOG_TAG, log + "");
	}

	public static final void e(Object log) {
		if (Debug)
			Log.e(LOG_TAG, "" + log);
	}

	public static final void e(String tag, Object log) {
		if (Debug)
			Log.e(tag, "" + log);
	}

	public static final void i(Object log) {
		if (Debug)
			Log.i(LOG_TAG, log + "");
	}

	public static final void i(String tag, Object log) {
		if (Debug)
			Log.i(tag, log + "");
	}

	public static final void v(Object log) {
		if (Debug)
			Log.v(LOG_TAG, log + "");
	}

	public static final void w(Object log) {
		if (Debug)
			Log.w(LOG_TAG, log + "");
	}
}
