package com.newproject.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Developer-X on 2016/12/2.
 */
public class ZViewHolder extends BaseViewHolder {
    public ZViewHolder(View view) {
        super(view);
    }

    public BaseViewHolder setTextColorRes(int viewId, int textColor) {
        TextView tv = getView(viewId);
        tv.setTextColor(tv.getResources().getColor(textColor));
        return this;
    }

    public CharSequence getText(int viewId) {
        TextView tv = getView(viewId);
        return tv.getText();
    }

    public void setImageUrl(int viewId, String smallpicUrl) {
        ImageView tv = getView(viewId);
        ImageLoader.getInstance().displayImage(smallpicUrl,tv);
    }
    public void setImageUrl(int viewId, String smallpicUrl, DisplayImageOptions options) {
        ImageView tv = getView(viewId);
        ImageLoader.getInstance().displayImage(smallpicUrl,tv,options);
    }
}
