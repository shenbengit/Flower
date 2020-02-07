package com.example.flower.mvvm.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flower.R;
import com.example.flower.util.ToastUtil;

/**
 * @author ShenBen
 * @date 2020/2/7 17:03
 * @email 714081644@qq.com
 */
public class InputDialog extends Dialog {
    private TextView tvTitle;
    private EditText etContent;
    private ContentCallback mContentCallback;

    public InputDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public InputDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_input);
        tvTitle = findViewById(R.id.tvTitle);
        etContent = findViewById(R.id.etContent);
        findViewById(R.id.tvSure).setOnClickListener(v -> {
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.warning(context, "请输入内容");
                return;
            }
            dismiss();
            if (mContentCallback != null) {
                mContentCallback.callback(content);
            }
        });
        findViewById(R.id.tvCancel).setOnClickListener(v -> dismiss());
        setOnDismissListener(dialog -> {
            tvTitle.setText("");
            etContent.setText("");
            etContent.setHint("");
        });
    }

    public void setTitle(CharSequence text) {
        tvTitle.setText(text);
    }

    public void setContent(CharSequence text,CharSequence hintText) {
        etContent.setText(text);
        etContent.setHint(hintText);
    }

    public void setContentCallback(ContentCallback callback) {
        mContentCallback = callback;
    }

    public interface ContentCallback {
        void callback(String content);
    }
}
