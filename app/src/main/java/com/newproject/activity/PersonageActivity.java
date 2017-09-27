package com.newproject.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.newproject.MyApp;
import com.newproject.R;
import com.newproject.base.BaseActivity;
import com.newproject.dialog.ChoosePhotoDialog;
import com.newproject.utils.ImageUtils;
import com.newproject.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Developer-D on 2017/9/26.
 */

public class PersonageActivity extends BaseActivity {

    @BindView(R.id.img)
    CircleImageView img;

    private Uri origUri = null;
    private Uri cropUri = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personage;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.photo_layout)
    public void onClick(View v) {
        ChoosePhotoDialog dialog = new ChoosePhotoDialog(PersonageActivity.this,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        origUri = ImageUtils.startTakePhoto(PersonageActivity.this);
                    }
                }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageUtils.startImagePick(PersonageActivity.this);
            }
        });
        dialog.show();
        //        final File file;
//        if (cropUri == null) {
//            file = null;
//        } else {
//            String path = ImageUtils.getRealFilePath(UserSettingActivity.this, cropUri);
//            file = new File(path);
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                cropUri = ImageUtils.startActionCrop(this, origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:// REQUEST_CODE_GETIMAGE_BYCROP
                // REQUEST_CODE_GETIMAGE_BYSDCARD
                cropUri = ImageUtils.startActionCrop(this,
                        data.getData());// 选图后裁剪
                // TLog.i(cropUri);
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                ImageLoader.getInstance().displayImage(cropUri.toString(), img, MyApp.headImageOption());
                break;
        }
    }
}
