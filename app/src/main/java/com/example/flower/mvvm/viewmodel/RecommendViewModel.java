package com.example.flower.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.flower.base.BaseViewModel;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author ShenBen
 * @date 2020/2/3 16:27
 * @email 714081644@qq.com
 */
public class RecommendViewModel extends BaseViewModel {

    public RecommendViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        if (currentUser == null) {
            return;
        }
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.order("-likesUserIds");
        query.include("author,postType");

        query.findObjects(new FindListener<PostBean>() {
            @Override
            public void done(List<PostBean> list, BmobException e) {
                if (e == null) {

                }
            }
        });
    }
}
