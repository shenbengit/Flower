package com.example.flower.mvvm.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.example.flower.R;

/**
 * 选择照片dialog
 * 拍照
 * 相册
 *
 * @author ShenBen
 * @date 2020/1/31 17:52
 * @email 714081644@qq.com
 */
public class ChooseImageDialog extends Dialog {

    private OnChooseImageListener mOnChooseImageListener;

    public ChooseImageDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public ChooseImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_choose_image);
        if (getWindow() != null) {
            getWindow().setGravity(Gravity.BOTTOM);
        }
        findViewById(R.id.tvCamera).setOnClickListener(v -> {
            dismiss();
            if (mOnChooseImageListener != null) {
                mOnChooseImageListener.fromCamera();
            }
        });
        findViewById(R.id.tvPhotoLibrary).setOnClickListener(v -> {
            dismiss();
            if (mOnChooseImageListener != null) {
                mOnChooseImageListener.fromPhotoLibrary();
            }
        });
        findViewById(R.id.tvCancel).setOnClickListener(v -> dismiss());
    }

    public void setOnChooseImageListener(OnChooseImageListener listener) {
        mOnChooseImageListener = listener;
    }

    public interface OnChooseImageListener {
        /**
         * 从相机
         */
        void fromCamera();

        /**
         * 从相册
         */
        void fromPhotoLibrary();
    }
}
