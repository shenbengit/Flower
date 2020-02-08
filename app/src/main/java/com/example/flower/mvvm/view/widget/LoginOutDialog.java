package com.example.flower.mvvm.view.widget;


import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.flower.R;

/**
 * @author ShenBen
 * @date 2020/2/8 14:42
 * @email 714081644@qq.com
 */
public class LoginOutDialog extends Dialog {

    private OnLoginOutListener mOnLoginOutListener;

    public LoginOutDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public LoginOutDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_login_out);
        findViewById(R.id.tvSure).setOnClickListener(v -> {
            dismiss();
            if (mOnLoginOutListener != null) {
                mOnLoginOutListener.loginOut();
            }
        });
        findViewById(R.id.tvCancel).setOnClickListener(v -> dismiss());
    }

    public void setOnLoginOutListener(OnLoginOutListener listener) {
        mOnLoginOutListener = listener;
    }

    public interface OnLoginOutListener {
        void loginOut();
    }
}
