package com.newproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer-X on 2016/11/7.
 */
public class DeleteModifyDialog extends Dialog {
    private Window window;

    @BindView(R.id.msg)
    TextView msg;

    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.ok)
    TextView ok;

    public interface ModifyListener {
        void modifyProject();

        void deleteProject();
    }

    public DeleteModifyDialog(Context context) {
        super(context, R.style.dialog_default);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setWindowAttribute();
    }

    @Override
    public void setTitle(CharSequence title) {
        msg.setText(title);
    }

    private DialogInterface.OnClickListener postiveListener;
    private DialogInterface.OnClickListener negListener;
    public void setOnPositiveListener(String title, DialogInterface.OnClickListener l) {
        postiveListener = l;
        ok.setText(title);
    }
    public void setOnNegativeListener(String title, DialogInterface.OnClickListener l) {
        negListener = l;
        cancel.setText(title);
    }

    protected int getLayoutId() {
        return R.layout.dialog_style;
    }

    protected void setWindowAttribute() {
        window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }

    @OnClick(R.id.cancel)
    public void delete(View v) {
        dismiss();
        if (negListener!=null) {
            negListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
        }
    }

    @OnClick(R.id.ok)
    public void modify(View v) {
        dismiss();
        if (postiveListener != null) {
            postiveListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
        }

    }
}
