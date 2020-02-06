package com.example.flower.mvvm.view.widget;

import android.content.Context;
import android.view.View;

import com.example.flower.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author ShenBen
 * @date 2020/2/5 16:38
 * @email 714081644@qq.com
 */
public class DeleteIdentifyPopupWindow extends BasePopupWindow {

    private OnDeleteListener mListener;

    public DeleteIdentifyPopupWindow(Context context) {
        super(context);
        setBackgroundColor(0);
        findViewById(R.id.flDelete)
                .setOnClickListener(v -> {
                    dismiss();
                    if (mListener != null) {
                        mListener.onDelete();
                    }
                });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_delete_identify);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        mListener = listener;
    }

    public interface OnDeleteListener {
        void onDelete();
    }

}
