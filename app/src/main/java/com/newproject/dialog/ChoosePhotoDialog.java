package com.newproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.newproject.R;

/**
 * Created by Developer-D on 2017/9/26.
 */

public class ChoosePhotoDialog extends Dialog {

    private Button btnShoot, btnFromPhoto, btnCancel;
    private View view;

    public ChoosePhotoDialog(Context context) {
        super(context, R.style.dialog_default);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_user_photo_change, null);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();

        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 在底部显示
        wl.gravity = Gravity.BOTTOM;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        // 设置点击外围解散
        setCanceledOnTouchOutside(true);

        // 设置按钮的点击
        btnShoot = (Button) view.findViewById(R.id.shoot);
        btnFromPhoto = (Button) view.findViewById(R.id.from_photo);
        btnCancel = (Button) view.findViewById(R.id.cancel);

        // 取消
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public ChoosePhotoDialog(Context context, DialogInterface.OnClickListener firstbtnListener, DialogInterface.OnClickListener secondbtnListener) {
        this(context);
        setOnTakePhotoListener(firstbtnListener);
        setOnChooseFromGalleryListener(secondbtnListener);
    }

    public ChoosePhotoDialog setOnTakePhotoListener(final DialogInterface.OnClickListener l) {
        btnShoot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onClick(ChoosePhotoDialog.this, 1);
                }
                dismiss();
            }
        });

        return this;
    }

    public ChoosePhotoDialog setOnChooseFromGalleryListener(final DialogInterface.OnClickListener l) {
        btnFromPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onClick(ChoosePhotoDialog.this, 1);
                }
                dismiss();

            }
        });
        return this;
    }

    public ChoosePhotoDialog setOnCancelLisener(final DialogInterface.OnClickListener l) {
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (l != null) {
                    l.onClick(ChoosePhotoDialog.this, 1);
                }
                dismiss();

            }
        });
        return this;
    }
}
