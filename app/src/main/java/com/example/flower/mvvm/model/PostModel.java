package com.example.flower.mvvm.model;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.flower.base.BaseModel;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.util.RxUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2020/2/1 16:23
 * @email 714081644@qq.com
 */
public class PostModel extends BaseModel {
    /**
     * 分页查询
     * 每次查询10条
     */
    public static final int PAGE_SIZE = 10;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    /**
     * 分页查询，查询到的当前页最后一个数据的createdAt的时间
     */
    private String lastTime;

    public PostModel() {
        lastTime = sdf.format(new Date());
    }

    /**
     * 根据帖子类型查询帖子
     *
     * @param objectId   为空，则查询全部
     * @param isLoadMore true: 上拉加载 ,false: 下拉刷新
     */
    public void queryPostByType(String objectId, boolean isLoadMore,
                                @Nullable Consumer<List<PostBean>> onNext,
                                @Nullable Consumer<Throwable> onError) {
        BmobQuery<PostBean> query = new BmobQuery<>();
        if (!TextUtils.isEmpty(objectId)) {
            //查询对应的帖子
            PostTypeBean typeBean = new PostTypeBean();
            typeBean.setObjectId(objectId);
            query.addWhereEqualTo("postType", new BmobPointer(typeBean));
        }
        if (isLoadMore) {
            //上拉加载
            Date date = null;
            try {
                date = sdf.parse(lastTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 只查询小于等于最后一个item发表时间的数据
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            //跳过之前页数并去掉重复数据
            query.setSkip(1);
        } else {
            //下拉刷新
            query.setSkip(0);
        }
        query.setLimit(PAGE_SIZE);
        //根据创建时间降序排序
        query.order("-createdAt");
        query.include("author,postType");
        query.findObjectsObservable(PostBean.class)
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<List<PostBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostBean> list) {
                        if (onNext != null) {
                            try {
                                onNext.accept(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (list != null && !list.isEmpty()) {
                            lastTime = list.get(list.size() - 1).getCreatedAt();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onError != null) {
                            try {
                                onError.accept(e);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
