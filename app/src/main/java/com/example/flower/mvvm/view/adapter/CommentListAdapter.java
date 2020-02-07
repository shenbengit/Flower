package com.example.flower.mvvm.view.adapter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.databinding.ItemCommentBinding;
import com.example.flower.http.bmob.CommentBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * @author ShenBen
 * @date 2020/2/2 16:54
 * @email 714081644@qq.com
 */
public class CommentListAdapter extends BaseBindingAdapter<CommentBean> {
    /**
     * 当前登录的用户
     */
    private UserBean mCurrentUser;

    @Override
    public void setNewData(@Nullable List<CommentBean> data) {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        super.setNewData(data);
    }

    /**
     * 更新当前用户信息
     */
    public void updateCurrentUser() {
        mCurrentUser = BmobUser.getCurrentUser(UserBean.class);
        notifyDataSetChanged();
    }

    public CommentListAdapter() {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, CommentBean item) {
        ItemCommentBinding binding = helper.getBinding();
        binding.setItem(item);
        UserBean user = item.getUser();
        if (mCurrentUser != null && TextUtils.equals(mCurrentUser.getObjectId(), user.getObjectId())) {
            binding.tvNickName.setText("我");
        } else {
            binding.tvNickName.setText(user.getNickName());
        }
    }
}
