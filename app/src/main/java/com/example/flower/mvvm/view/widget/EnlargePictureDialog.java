package com.example.flower.mvvm.view.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bean.AddPictureBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 查看大图Dialog
 *
 * @author ShenBen
 * @date 2020/2/1 20:03
 * @email 714081644@qq.com
 */
@SuppressLint("SetTextI18n")
public class EnlargePictureDialog extends Dialog {

    private ImageButton ibDelete;
    private TextView tvCurrentIndex;
    private ViewPager2 vpImages;
    private ImagesAdapter mAdapter;
    private List<?> mList;

    private int mCurrentIndex = 0;
    private OnDeletePictureListener mOnDeletePicutreListener;

    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            mCurrentIndex = position;
            tvCurrentIndex.setText((position + 1) + "/" + mList.size());
        }
    };

    public EnlargePictureDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public EnlargePictureDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
        setContentView(R.layout.dialog_enlarge_picture);
        findViewById(R.id.ibBack).setOnClickListener(v -> dismiss());
        ibDelete = findViewById(R.id.ibDelete);
        ibDelete.setOnClickListener(v -> {
            dismiss();
            if (mOnDeletePicutreListener != null) {
                mOnDeletePicutreListener.delete(mCurrentIndex);
            }
        });
        tvCurrentIndex = findViewById(R.id.tvCurrentIndex);
        vpImages = findViewById(R.id.vpImages);
        mAdapter = new ImagesAdapter();
        vpImages.setAdapter(mAdapter);
        setOnDismissListener(dialog -> vpImages.unregisterOnPageChangeCallback(mOnPageChangeCallback));
    }

    @Override
    public void show() {
        vpImages.registerOnPageChangeCallback(mOnPageChangeCallback);
        super.show();
    }

    public void isShowDeleteButton(boolean isShow) {
        ibDelete.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    public void setOnDeletePictureListener(OnDeletePictureListener listener) {
        mOnDeletePicutreListener = listener;
    }

    public void setImageList(List<?> list, int index) {
        mList = list == null ? new ArrayList<>() : list;
        mAdapter.notifyDataSetChanged();
        if (!mList.isEmpty() && index >= 0 && index < mList.size()) {
            vpImages.setCurrentItem(index, false);
            tvCurrentIndex.setText((index + 1) + "/" + mList.size());
            mCurrentIndex = index;
        }
    }

    final class ImagesAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enlarge_picture, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Object item = mList.get(position);
            if (item instanceof BmobFile) {
                GlideApp.with(holder.iv)
                        .load(((BmobFile) item).getFileUrl())
                        .error(R.drawable.legacy_icon_mediagallery_placeholder)
                        .placeholder(R.drawable.legacy_icon_mediagallery_placeholder)
                        .into(holder.iv);
            } else if (item instanceof AddPictureBean) {
                GlideApp.with(holder.iv)
                        .load(((AddPictureBean) item).getImageFile())
                        .error(R.drawable.legacy_icon_mediagallery_placeholder)
                        .placeholder(R.drawable.legacy_icon_mediagallery_placeholder)
                        .into(holder.iv);
            }
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }
    }

    final class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }

    public interface OnDeletePictureListener {
        void delete(int position);
    }
}
