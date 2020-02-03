package com.example.flower.mvvm.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flower.base.BaseModel;
import com.example.flower.http.bmob.CommentBean;
import com.example.flower.http.bmob.PostBean;
import com.example.flower.http.bmob.UserBean;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author ShenBen
 * @date 2020/2/2 15:47
 * @email 714081644@qq.com
 */
public class PostDetailModel extends BaseModel {

    /**
     * 根据ObjectId 查询帖子
     *
     * @param objectId
     */
    public void queryPostByObjectId(@NonNull String objectId, @Nullable Consumer<PostBean> onNext, @Nullable Consumer<Throwable> onError) {
        BmobQuery<PostBean> query = new BmobQuery<>();
        query.include("author,postType");
        query.getObjectObservable(PostBean.class, objectId)
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
     * 查询某个帖子的所有评论
     *
     * @param objectId
     */
    public void queryCommentByPostObjectId(@NonNull String objectId, @Nullable Consumer<List<CommentBean>> onNext, @Nullable Consumer<Throwable> onError) {
        PostBean postBean = new PostBean();
        postBean.setObjectId(objectId);
        BmobQuery<CommentBean> query = new BmobQuery<>();
        query.addWhereEqualTo("post", new BmobPointer(postBean));
        //同时查询该评论的发布者的信息，以及该帖子的作者的信息
        query.include("user,post.author");
        query.order("createdAt");
        query.findObjectsObservable(CommentBean.class)
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<List<CommentBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CommentBean> commentBeans) {
                        if (onNext != null) {
                            try {
                                onNext.accept(commentBeans);
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
     * 更新评论的数量
     */
    public void updateCommentNumber(String objectId, int commentNumber, List<String> commentUserIds, @Nullable Consumer<BmobException> onResult) {
        PostBean postBean = new PostBean();
        postBean.setCommentNumber(commentNumber);
        postBean.setValue("commentUserIds", commentUserIds);
        postBean.updateObservable(objectId)
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
                    public void onError(Throwable throwable) {
                        if (onResult != null) {
                            try {
                                onResult.accept(new BmobException(throwable));
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

}
