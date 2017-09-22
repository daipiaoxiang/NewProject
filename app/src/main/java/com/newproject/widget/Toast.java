package com.newproject.widget;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newproject.MyApp;
import com.newproject.R;


public class Toast {

//	public static void alert(Context c, String error) {
//		new AlertDialog.Builder(c).setTitle("警告").setMessage(error)
//				.setPositiveButton(android.R.string.ok, null).create().show();
//	}

	public static void show(int message) {
		show(message, android.widget.Toast.LENGTH_SHORT, 0);
	}

	public static void show(Object message) {
		if (message != null)
		show(message.toString());
	}

	public static void show(String message) {
		show(message, android.widget.Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
	}

	public static void show(int message, int icon) {
		show(message, android.widget.Toast.LENGTH_SHORT, icon);
	}

	public static void show(String message, int icon) {
		show(message, android.widget.Toast.LENGTH_SHORT, icon, Gravity.BOTTOM);
	}

	public static void showShort(int message) {
		show(message, android.widget.Toast.LENGTH_SHORT, 0);
	}

	public static void showShort(String message) {
		show(message, android.widget.Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
	}

	public static void showShort(int message, Object... args) {
		show(message, android.widget.Toast.LENGTH_SHORT, 0, Gravity.BOTTOM,
				args);
	}

	public static void show(int message, int duration, int icon) {
		show(message, duration, icon, Gravity.BOTTOM);
	}

	public static void show(int message, int duration, int icon, int gravity) {
		show(MyApp.getApp().getString(message), duration, icon, gravity);
	}

	public static void show(int message, int duration, int icon, int gravity,
			Object... args) {
		show(MyApp.getApp().getString(message, args), duration, icon, gravity);
	}

	private static String lastToast = "";
	private static long lastToastTime;

	@SuppressLint("InflateParams")
	public static void show(String message, int duration, int icon, int gravity) {
		if (message != null && !message.equalsIgnoreCase("")) {
			long time = System.currentTimeMillis();
			if (!message.equalsIgnoreCase(lastToast)
					|| Math.abs(time - lastToastTime) > 2000) {
				View view = LayoutInflater.from(MyApp.getApp()).inflate(
						R.layout.view_toast, null);
				((TextView) view.findViewById(R.id.title_tv)).setText(message);
				if (icon != 0) {
					((ImageView) view.findViewById(R.id.icon_iv))
							.setImageResource(icon);
					((ImageView) view.findViewById(R.id.icon_iv))
							.setVisibility(View.VISIBLE);
				}
				android.widget.Toast toast = new android.widget.Toast(
						MyApp.getApp());
				toast.setView(view);
				if (gravity == Gravity.CENTER) {
					toast.setGravity(gravity, 0, 0);
				} else {
					toast.setGravity(gravity, 0, 35);
				}

				toast.setDuration(duration);
				toast.show();
				lastToast = message;
				lastToastTime = System.currentTimeMillis();
			}
		}
	}

//	public static void alert(Context context,String msg) {
//		new AlertDialog.Builder(context).setTitle("提示").setMessage(msg).setPositiveButton("确认",null).show();
//	}

//	public static void alert(Context context, AppError error) {
//		if (error == null) return;
//		if (error.getCode() == 0) {
//			show(error);
//		} else {
//			alert(context,error.toString());
//		}
//	}
}
