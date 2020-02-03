package com.example.flower.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityCommentBinding;
import com.example.flower.http.bmob.CommentBean;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.rxbus.RxBus;
import com.example.flower.rxbus.bean.CommandBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 评论帖子页面
 */
@Route(path = ARouterPath.COMMENT_PATH)
public class CommentActivity extends BaseActivity<ActivityCommentBinding, BaseViewModel> {
    public static final String POST_OBJECT_ID = "POST_OBJECT_ID";
    /**
     * 帖子的ObjectId
     */
    private String mObjectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        mBinding.toolbar.setTitle("评论");
        initToolbar(mBinding.toolbar);
        //发送按钮点击事件
        RxView.clicks(mBinding.ibPost)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> sendComment());

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            mObjectId = getIntent().getStringExtra(POST_OBJECT_ID);
        }
    }

    /**
     * 提交评论
     */
    private void sendComment() {
        if (TextUtils.isEmpty(mObjectId)) {
            ToastUtil.warning(this, "出错啦");
            return;
        }
        String content = mBinding.etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.warning(this, "请输入评论内容");
            return;
        }
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean == null) {
            ToastUtil.warning(this, "请先登录！");
            return;
        }
        CommentBean commentBean = new CommentBean();
        PostBean postBean = new PostBean();
        postBean.setObjectId(mObjectId);
        commentBean.setContent(content);
        commentBean.setPost(postBean);
        commentBean.setUser(userBean);
        commentBean.saveObservable()
                .compose(bindToLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLoadingDialog.show();
                    }

                    @Override
                    public void onNext(String objectId) {
                        ToastUtil.success(CommentActivity.this.getApplication(), "添加评论成功！");
                        mLoadingDialog.dismiss();
                        RxBus.getDefault().postSticky(new CommandBean(CommandBean.COMMAND_UPDATE_COMMENT));
                        onBackPressedSupport();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingDialog.dismiss();
                        ToastUtil.warning(CommentActivity.this.getApplication(), "添加评论出错啦，请稍后重试！");
                        LogUtil.e("添加评论失败，" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
