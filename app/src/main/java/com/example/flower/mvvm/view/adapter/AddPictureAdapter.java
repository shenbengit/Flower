package com.example.flower.mvvm.view.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bean.AddPictureBean;

/**
 * 添加照片Adapter
 *
 * @author ShenBen
 * @date 2020/1/31 15:53
 * @email 714081644@qq.com
 */
public class AddPictureAdapter extends BaseMultiItemQuickAdapter<AddPictureBean, BaseViewHolder> {
    /**
     * 添加照片的布局
     */
    public static final int ADD_PICTURE = 1;
    /**
     * 已经添加的布局
     */
    public static final int NORMAL_PICTURE = 2;

    public AddPictureAdapter() {
        super(null);
        addItemType(ADD_PICTURE, R.layout.item_add_picture);
        addItemType(NORMAL_PICTURE, R.layout.item_picture);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddPictureBean item) {
        switch (helper.getItemViewType()) {
            case ADD_PICTURE:
                helper.addOnClickListener(R.id.ibAddPicture);
                break;
            case NORMAL_PICTURE:
                helper.addOnClickListener(R.id.ivPicture, R.id.ibDelete);
                ImageView iv = helper.getView(R.id.ivPicture);
                GlideApp.with(iv)
                        .load(item.getImageFile())
                        .error(R.drawable.grey_bg)
                        .placeholder(R.drawable.grey_bg)
                        .into(iv);
                break;
            default:
                break;
        }
    }
}
