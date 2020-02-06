package com.example.flower.mvvm.model;

import androidx.annotation.Nullable;

import com.example.flower.base.BaseModel;
import com.example.flower.http.bmob.IdentifyResultBean;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2019/9/22 22:22
 * @email 714081644@qq.com
 */
public class MineModel extends BaseModel {
    /**
     * 查询当前用户发表所有帖子的数量
     *
     * @param userBean
     */
    public void queryCurrentUserAllPostCount(UserBean userBean, @Nullable Consumer<Integer> onNext, @Nullable Consumer<Throwable> onError) {
        if (userBean == null) {
            return;
        }
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.addWhereEqualTo("author", userBean);
        query.order("-createdAt");
        query.countObservable(PostBean.class)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (onNext != null) {
                            try {
                                onNext.accept(integer);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
     * 查询当前用户发表所有收藏的数量
     *
     * @param userBean
     */
    public void queryCurrentUserAllCollectionCount(UserBean userBean, @Nullable Consumer<Integer> onNext, @Nullable Consumer<Throwable> onError) {
        if (userBean == null) {
            return;
        }
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.addWhereContainsAll("likesUserIds", Collections.singletonList(userBean.getObjectId()));
        query.order("-createdAt");
        query.countObservable(PostBean.class)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (onNext != null) {
                            try {
                                onNext.accept(integer);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
     * 查询当前用户发表所有识花记录数量
     *
     * @param userBean
     */
    public void queryCurrentUserKnowFlowerRecordCount(UserBean userBean, @Nullable Consumer<List<IdentifyResultBean>> onNext, @Nullable Consumer<Throwable> onError) {
        if (userBean == null) {
            return;
        }
        BmobQuery<IdentifyResultBean> query = new BmobQuery<>();
        query.addWhereEqualTo("user", userBean);
        query.include("user");
        query.order("-createdAt");
        query.findObjectsObservable(IdentifyResultBean.class)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<List<IdentifyResultBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<IdentifyResultBean> list) {
                        if (onNext != null) {
                            try {
                                onNext.accept(list);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
     * 删除识花结果
     *
     * @param objectId
     */
    public void deleteIdentifyResult(String objectId, @Nullable Consumer<BmobException> onResult) {
        IdentifyResultBean bean = new IdentifyResultBean();
        bean.deleteObservable(objectId)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<BmobException>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BmobException exception) {
                        if (onResult != null) {
                            try {
                                onResult.accept(exception);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable exception) {
                        if (onResult != null) {
                            try {
                                onResult.accept(new BmobException(exception));
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
