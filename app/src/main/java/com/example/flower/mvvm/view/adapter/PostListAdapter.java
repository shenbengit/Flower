package com.example.flower.mvvm.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.jaeger.ninegridimageview.NineGridImageView;

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
public class PostListAdapter extends BaseQuickAdapter<PostBean, PostListAdapter.ViewHolder> {
    public static final String UPDATE_ITEM_LIKES_COMMENT = "UPDATE_ITEM_LIKES_COMMENT";
    /**
     * 当前登录的用户
     */
    private UserBean mCurrentUser;

    public PostListAdapter() {
        super(R.layout.item_post);
    }

    private boolean isShowMore = false;

    @Override
    public void setNewData(@Nullable List<PostBean> data) {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        super.setNewData(data);
    }

    public void setShowMore(boolean isShow) {
        isShowMore = isShow;
    }

    /**
     * 更新当前用户信息
     */
    public void updateCurrentUser() {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            PostBean item = getItem(position);
            if (item == null) {
                return;
            }
            Object o = payloads.get(0);
            if (o instanceof String) {
                if (TextUtils.equals((String) o, UPDATE_ITEM_LIKES_COMMENT)) {
                    //更新喜欢图标
                    holder.ivLikes.setBackgroundResource(R.drawable.community_item_collect);

                    if (mCurrentUser != null) {
                        for (String userId : item.getLikesUserIds()) {
                            if (TextUtils.equals(userId, mCurrentUser.getObjectId())) {
                                //如果当前用户喜欢了当前的帖子，则替换喜欢的图标
                                holder.ivLikes.setBackgroundResource(R.drawable.community_item_collect_focus);
                                break;
                            }
                        }
                    }
                    holder.tvLikes.setText(String.valueOf(item.getLikesUserIds().size()));

                    holder.tvComment.setText(String.valueOf(item.getCommentNumber()));
                }
            }
        }

    }

    @Override
    protected void convert(PostListAdapter.ViewHolder helper, PostBean item) {
        helper.setVisible(R.id.ivMore, isShowMore)
                .setBackgroundRes(R.id.ivLikes, R.drawable.community_item_collect)
                .addOnClickListener(R.id.ivLikes, R.id.ivMore);

        //帖子的作者
        UserBean author = item.getAuthor();

        if (mCurrentUser != null && TextUtils.equals(mCurrentUser.getObjectId(), author.getObjectId())) {
            helper.tvNick.setText("我");
            if (mCurrentUser.getHeadImg() != null) {
                //有头像
                GlideApp.with(helper.civHead)
                        .load(mCurrentUser.getHeadImg().getFileUrl())
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
            helper.tvSignature.setText(mCurrentUser.getSignature());
        } else {
            helper.tvNick.setText(author.getNickName());
            helper.tvSignature.setText(author.getSignature());
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
        }
        helper.tvTime.setText(item.getCreatedAt());
        helper.tvContent.setText(item.getContent());
        helper.bind(item.getPictures());

        List<String> likesUserIds = item.getLikesUserIds();
        if (mCurrentUser != null) {
            for (String userId : likesUserIds) {
                if (TextUtils.equals(userId, mCurrentUser.getObjectId())) {
                    //如果当前用户喜欢了当前的帖子，则替换喜欢的图标
                    helper.ivLikes.setBackgroundResource(R.drawable.community_item_collect_focus);
                    break;
                }
            }
        }

        helper.tvLikes.setText(String.valueOf(likesUserIds.size()));

        helper.tvComment.setText(String.valueOf(item.getCommentNumber()));
    }

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    static final class ViewHolder extends BaseViewHolder {

        CircleImageView civHead;
        TextView tvNick;
        TextView tvTime;
        TextView tvSignature;
        TextView tvContent;
        NineGridImageView<BmobFile> nglvImages;
        ImageView ivLikes;
        TextView tvLikes;
        TextView tvComment;
        NineGridAdapter mAdapter;

        public ViewHolder(View view) {
            super(view);
            civHead = view.findViewById(R.id.civAvatar);
            tvNick = view.findViewById(R.id.tvNickName);
            tvTime = view.findViewById(R.id.tvCreatedAt);
            tvSignature = view.findViewById(R.id.tvSignature);
            tvContent = view.findViewById(R.id.tvContent);
            nglvImages = view.findViewById(R.id.nglvImages);
            ivLikes = view.findViewById(R.id.ivLikes);
            tvLikes = view.findViewById(R.id.tvLikes);
            tvComment = view.findViewById(R.id.tvComment);

            mAdapter = new NineGridAdapter();
        }

        void bind(List<BmobFile> list) {
            nglvImages.setAdapter(mAdapter);
            nglvImages.setImagesData(list);
        }
    }
}
