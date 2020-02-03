package com.example.flower.mvvm.model;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.example.flower.base.BaseModel;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.RxUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2020/2/1 16:23
 * @email 714081644@qq.com
 */
public class PostListModel extends BaseModel {
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

    public PostListModel() {
        lastTime = sdf.format(new Date());
    }

    /**
     * 根据帖子类型查询帖子
     *
     * @param objectId   帖子类型的ObjectId，为空，则查询全部
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

    /**
     * 更改喜欢状态
     *
     * @param item
     */
    public void updateLikes(PostBean item, @Nullable Consumer<BmobException> onResult) {
        PostBean postBean = new PostBean();
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        BmobRelation relation = new BmobRelation();
        boolean isLiked = item.getLikesUserIds().contains(currentUser.getObjectId());
        if (isLiked) {
            //删除多对多关联
            relation.remove(currentUser);
            //喜欢列表存在当前用户，则置为不喜欢状态
            postBean.removeAll("likesUserIds", Collections.singletonList(currentUser.getObjectId()));
        } else {
            //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
            //将当前用户添加到多对多关联中
            relation.add(currentUser);
            //置为喜欢状态
            //使用addUnique、addAllUnique添加,只有在这些数据之前未添加过的情况下才会被添加。
            postBean.addUnique("likesUserIds", currentUser.getObjectId());
        }
        //多对多关联指向`post`的`likes`字段
        postBean.setLikes(relation);
        postBean.updateObservable(item.getObjectId())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<BmobException>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BmobException exception) {
                        if (isLiked) {
                            item.getLikesUserIds().remove(currentUser.getObjectId());
                        } else {
                            item.getLikesUserIds().add(currentUser.getObjectId());
                        }
                        if (onResult != null) {
                            try {
                                onResult.accept(exception);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onResult != null) {
                            try {
                                onResult.accept(new BmobException(e));
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

    /**
     * 查询某个帖子
     *
     * @param objectId
     */
    public void queryPostByObjectId(String objectId,
                                    @Nullable Consumer<PostBean> onNext,
                                    @Nullable Consumer<Throwable> onError) {
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("author,postType");
        query.getObjectObservable(PostBean.class, objectId)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<PostBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostBean bean) {
                        if (onNext != null) {
                            try {
                                onNext.accept(bean);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (onError != null) {
                            try {
                                onError.accept(throwable);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
