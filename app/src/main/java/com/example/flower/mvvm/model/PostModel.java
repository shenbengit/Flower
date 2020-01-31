package com.example.flower.mvvm.model;

import androidx.annotation.Nullable;

import com.example.flower.base.BaseModel;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.RxUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2020/1/31 14:23
 * @email 714081644@qq.com
 */
public class PostModel extends BaseModel {

    public void getPostType(@Nullable Consumer<List<PostTypeBean>> onNext, @Nullable Consumer<Throwable> onError) {
        BmobQuery<PostTypeBean> query = new BmobQuery<>();
        //筛选状态为启用状态的
        query.addWhereEqualTo("isEnable", true);
        query.findObjectsObservable(PostTypeBean.class)
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<List<PostTypeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostTypeBean> postTypeBeans) {
                        if (onNext != null) {
                            try {
                                onNext.accept(postTypeBeans);
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

    public void uploadBatch(String[] paths, @Nullable Consumer<List<BmobFile>> onNext, @Nullable Consumer<BmobException> onError) {
        if (paths == null) {
            return;
        }
        BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                if (onNext != null) {
                    if (list.size() == paths.length) {
                        //如果数量相等，则代表文件全部上传完成
                        try {
                            onNext.accept(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {

            }

            @Override
            public void onError(int statusCode, String errorMsg) {
                if (onError != null) {
                    try {
                        onError.accept(new BmobException(statusCode, errorMsg));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void post(String content, List<BmobFile> list, PostTypeBean typeBean,
                     @Nullable Consumer<String> onNext, @Nullable Consumer<Throwable> onError) {
        PostBean postBean = new PostBean();
        postBean.setAuthor(BmobUser.getCurrentUser(UserBean.class));
        postBean.setContent(content);
        postBean.setPictures(list);
        postBean.setPostType(typeBean);
        postBean.saveObservable()
                .compose(RxUtil.io_main())
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String objectId) {
                        if (onNext != null) {
                            try {
                                onNext.accept(objectId);
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

}
