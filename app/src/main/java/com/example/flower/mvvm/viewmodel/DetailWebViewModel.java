package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.http.bean.ArticleInfoBean;
import com.example.flower.mvvm.model.DetailWebModel;
import com.example.flower.mvvm.view.activity.VideoActivity;

/**
 * @author ShenBen
 * @date 2019/10/13 14:16
 * @email 714081644@qq.com
 */
public class DetailWebViewModel extends BaseViewModel<DetailWebModel> {

    public final ObservableField<ArticleInfoBean.DataBean> articleInfo = new ObservableField<>();

    public BindingCommand playVideoCommand;

    public DetailWebViewModel(@NonNull Application application) {
        super(application, new DetailWebModel());
        playVideoCommand = new BindingCommand(() -> {
            ArticleInfoBean.DataBean bean = articleInfo.get();
            if (bean != null && bean.isVideo()) {
                ARouter.getInstance()
                        .build(ARouterPath.VIDEO_ACTIVITY_PATH)
                        .withString(VideoActivity.VIDEO_URL, bean.getVideoUrl())
                        .withString(VideoActivity.TITLE, bean.getTitle())
                        .withString(VideoActivity.THUMB_IMAGE_URL, bean.getArticleTitleImgUrl())
                        .navigation();
            }
        });
    }

    public void getArticleInfo(String uid, String aid) {
        mModel.getArticleInfo(uid, aid, articleInfoBean -> {
            if (articleInfoBean != null && articleInfoBean.getData() != null) {
                articleInfo.set(articleInfoBean.getData());
            }
        }, null);
    }
}
