package com.example.flower.mvvm.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.view.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 展示帖子Adapter
 *
 * @author ShenBen
 * @date 2020/2/1 17:36
 * @email 714081644@qq.com
 */
public class PostAdapter extends BaseQuickAdapter<PostBean, PostAdapter.ViewHolder> {
    /**
     * 当前登录的用户
     */
    private UserBean mCurrentUser;

    public PostAdapter() {
        super(R.layout.item_post);
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
    }

    @Override
    protected void convert(PostAdapter.ViewHolder helper, PostBean item) {
        //帖子的作者
        UserBean author = item.getAuthor();
        if (author != null) {
            if (author.getHeadImg() != null) {
                //有头像
                GlideApp.with(helper.civHead)
                        .load(author.getHeadImg().getFileUrl())
                        .placeholder(R.drawable.icon_profile_default_portrait)
                        .error(R.drawable.icon_profile_default_portrait)
                        .into(helper.civHead);
            } else {
                //无头像
                GlideApp.with(helper.civHead)
                        .load(R.drawable.icon_profile_default_portrait)
                        .placeholder(R.drawable.icon_profile_default_portrait)
                        .error(R.drawable.icon_profile_default_portrait)
                        .into(helper.civHead);
            }
            if (mCurrentUser != null) {
                if (TextUtils.equals(mCurrentUser.getObjectId(), author.getObjectId())) {
                    helper.tvNick.setText("我");
                } else {
                    helper.tvNick.setText(author.getNickName());
                }
            }
            helper.tvSignature.setText(author.getSignature());
        }
        helper.tvTime.setText(item.getCreatedAt());
        helper.tvContent.setText(item.getContent());
        helper.bind(item.getPictures());
        helper.tvLikes.setText(String.valueOf(item.getLikesNumber()));
        helper.tvComment.setText(String.valueOf(item.getCommentNumber()));
    }

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    final class ViewHolder extends BaseViewHolder {

        CircleImageView civHead;
        TextView tvNick;
        TextView tvTime;
        TextView tvSignature;
        TextView tvContent;
        NineGridImageView<BmobFile> nglvImages;
        TextView tvLikes;
        TextView tvComment;

        private NineGridImageViewAdapter<BmobFile> mAdapter;

        public ViewHolder(View view) {
            super(view);
            civHead = view.findViewById(R.id.civAvatar);
            tvNick = view.findViewById(R.id.tvNickName);
            tvTime = view.findViewById(R.id.tvCreatedAt);
            tvSignature = view.findViewById(R.id.tvSignature);
            tvContent = view.findViewById(R.id.tvContent);
            nglvImages = view.findViewById(R.id.nglvImages);
            tvLikes = view.findViewById(R.id.tvLikes);
            tvComment = view.findViewById(R.id.tvComment);

            mAdapter = new NineGridImageViewAdapter<BmobFile>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, BmobFile s) {
                    GlideApp.with(context)
                            .load(s.getFileUrl())
                            .placeholder(R.drawable.grey_bg)
                            .error(R.drawable.grey_bg)
                            .into(imageView);
                }

                @Override
                protected void onItemImageClick(Context context, ImageView imageView, int index, List<BmobFile> list) {
                    EnlargePictureDialog mDialog = new EnlargePictureDialog(context);
                    mDialog.isShowDeleteButton(false);
                    mDialog.setImageList(list, index);
                    mDialog.show();
                }
            };
        }

        void bind(List<BmobFile> list) {
            nglvImages.setAdapter(mAdapter);
            nglvImages.setImagesData(list);
        }
    }
}
