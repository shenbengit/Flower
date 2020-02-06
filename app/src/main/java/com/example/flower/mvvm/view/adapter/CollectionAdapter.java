package com.example.flower.mvvm.view.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bmob.PostBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author ShenBen
 * @date 2020/2/6 16:02
 * @email 714081644@qq.com
 */
public class CollectionAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {

    public CollectionAdapter() {
        super(R.layout.item_collection);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {
        helper.setText(R.id.tvName, TextUtils.isEmpty(item.getAuthor().getNickName()) ? item.getAuthor().getUsername() : item.getAuthor().getNickName())
                .setText(R.id.tvTime, item.getCreatedAt())
                .setText(R.id.tvContent, item.getContent());
        CircleImageView civAvatar = helper.getView(R.id.civAvatar);
        GlideApp.with(civAvatar)
                .load(item.getAuthor().getHeadImg() == null ? R.drawable.icon_profile_default_portrait : item.getAuthor().getHeadImg().getFileUrl())
                .error(R.drawable.icon_profile_default_portrait)
                .placeholder(R.drawable.icon_profile_default_portrait)
                .into(civAvatar);
        ImageView ivPicture = helper.getView(R.id.ivPicture);
        if (item.getPictures() != null && !item.getPictures().isEmpty()) {
            GlideApp.with(ivPicture)
                    .load(item.getPictures().get(0).getFileUrl())
                    .error(R.drawable.grey_bg)
                    .placeholder(R.drawable.grey_bg)
                    .into(ivPicture);
        } else {
            GlideApp.with(ivPicture)
                    .load(R.drawable.grey_bg)
                    .error(R.drawable.grey_bg)
                    .placeholder(R.drawable.grey_bg)
                    .into(ivPicture);
        }
    }
}
