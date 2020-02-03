package com.example.flower.mvvm.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.mvvm.view.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author ShenBen
 * @date 2020/2/2 16:27
 * @email 714081644@qq.com
 */
public final class NineGridAdapter extends NineGridImageViewAdapter<BmobFile> {
    private EnlargePictureDialog mDialog;

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, BmobFile bmobFile) {
        GlideApp.with(imageView)
                .load(bmobFile.getFileUrl())
                .placeholder(R.drawable.grey_bg)
                .error(R.drawable.grey_bg)
                .into(imageView);
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<BmobFile> list) {
        if (mDialog == null) {
            mDialog = new EnlargePictureDialog(context);
            mDialog.isShowDeleteButton(false);
        }
        mDialog.setImageList(list, index);
        mDialog.show();
    }

    @Override
    protected ImageView generateImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
