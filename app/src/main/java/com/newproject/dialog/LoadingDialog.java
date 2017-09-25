package com.newproject.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.newproject.R;


public class LoadingDialog extends Dialog {

    //	private Button btnShoot, btnFromPhoto, btnCancel;
    private View view;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_loading);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        setContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画
//		window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();

        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.WRAP_CONTENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 在底部显示
        wl.gravity = Gravity.CENTER;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        // 设置点击外围解散
        setCanceledOnTouchOutside(false);


    }

}
