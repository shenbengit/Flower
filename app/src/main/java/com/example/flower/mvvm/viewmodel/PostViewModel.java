package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.Constant;
import com.example.flower.http.bean.AddPictureBean;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.mvvm.model.PostModel;
import com.example.flower.mvvm.view.adapter.AddPictureAdapter;
import com.example.flower.mvvm.view.adapter.PostTypeAdapter;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author ShenBen
 * @date 2020/1/30 18:20
 * @email 714081644@qq.com
 */
public class PostViewModel extends BaseViewModel<PostModel> {
    /**
     * 选择照片
     */
    public static final String CHOOSE_IMAGE = "CHOOSE_IMAGE";
    /**
     * 可以选择的最大的图片数量
     */
    private static final int MAX_IMAGE_NUMBER = 9;
    /**
     * 发帖点击事件
     */
    public BindingCommand postCommand;
    /**
     * 帖子的内容
     */
    public ObservableField<String> contentStr = new ObservableField<>();
    /**
     * 已经选择的图片数量
     */
    public ObservableField<String> imageNumberStr = new ObservableField<>("(0/" + MAX_IMAGE_NUMBER + ")");


    public AddPictureAdapter mAddPictureAdapter;
    public PostTypeAdapter mPostTypeAdapter;
    /**
     * 选择的帖子类型
     */
    private PostTypeBean mCheckedPostType;

    /**
     * 已经选择的图片集合
     */
    private List<AddPictureBean> mHaveChosenPictureList = new ArrayList<>();

    public PostViewModel(@NonNull Application application) {
        super(application, new PostModel());
        postCommand = new BindingCommand(this::uploadBatch);

        mAddPictureAdapter = new AddPictureAdapter();
        mAddPictureAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ibAddPicture:
                    mBaseLiveData.setValue(CHOOSE_IMAGE);
                    break;
                case R.id.ivPicture:

                    break;
                case R.id.ibDelete:
                    mAddPictureAdapter.remove(position);
                    if (mHaveChosenPictureList.size() == MAX_IMAGE_NUMBER) {
                        //如果已经选择了最大数量，需要在最后再添加一个添加图片按钮
                        //先添加一个添加照片的按钮
                        mAddPictureAdapter.addData(new AddPictureBean(AddPictureAdapter.ADD_PICTURE));
                    }
                    mHaveChosenPictureList.remove(position);
                    setHaveChosenPictureSize();
                    break;
            }
        });

        mPostTypeAdapter = new PostTypeAdapter();

        mPostTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCheckedPostType = mPostTypeAdapter.getItem(position);
            mPostTypeAdapter.singleElectionPosition(position);
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //先添加一个添加照片的按钮
        mAddPictureAdapter.addData(new AddPictureBean(AddPictureAdapter.ADD_PICTURE));

        //获取帖子类型表
        mModel.getPostType(postTypeBeans -> mPostTypeAdapter.setNewData(postTypeBeans), null);

    }

    /**
     * @return 还可以选择的最大的图片数量
     */
    public int canChooseImageNumber() {
        return MAX_IMAGE_NUMBER - mHaveChosenPictureList.size();
    }

    /**
     * 添加照片
     * 从相机拍照返回
     *
     * @param file
     */
    public void addPictureFromCamera(File file) {
        if (file == null) {
            return;
        }
        AddPictureBean bean = new AddPictureBean(AddPictureAdapter.NORMAL_PICTURE, file);
        mHaveChosenPictureList.add(bean);
        if (mHaveChosenPictureList.size() == MAX_IMAGE_NUMBER) {
            //如果已经选择的数量等于最大值，则替换最后一个位置(添加图片的按钮)，之后则不可再添加数据
            mAddPictureAdapter.setData(mAddPictureAdapter.getData().size() - 1, bean);
        } else {
            //如果已经选择的数量小于最大值，则在Adapter的data最后一个位置之前追加数据
            mAddPictureAdapter.addData(mAddPictureAdapter.getData().size() - 1, bean);
        }
        setHaveChosenPictureSize();
    }

    /**
     * 添加照片
     * 从相册选取返回
     *
     * @param list
     */
    public void addPictureFromPhotoLibrary(List<LocalMedia> list) {
        if (list == null) {
            return;
        }
        List<AddPictureBean> pictures = new ArrayList<>();
        String path;
        for (LocalMedia media : list) {
            if (media.isCompressed()) {
                path = media.getCompressPath();
            } else {
                path = media.getPath();
            }
            pictures.add(new AddPictureBean(AddPictureAdapter.NORMAL_PICTURE, new File(path)));
        }
        mHaveChosenPictureList.addAll(pictures);
        if (mHaveChosenPictureList.size() == MAX_IMAGE_NUMBER) {
            //如果选择的数量正好，则删除最后一个添加图片的按钮
            mAddPictureAdapter.remove(mAddPictureAdapter.getData().size() - 1);
            mAddPictureAdapter.addData(pictures);
        } else {
            mAddPictureAdapter.addData(mAddPictureAdapter.getData().size() - 1, pictures);
        }
        setHaveChosenPictureSize();
    }

    /**
     * 设置已经选择的图片数量
     */
    private void setHaveChosenPictureSize() {
        imageNumberStr.set("(" + mHaveChosenPictureList.size() + "/" + MAX_IMAGE_NUMBER + ")");
    }

    /**
     * 上传文件
     */
    private void uploadBatch() {
        String content = contentStr.get();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.warning(getApplication(), "请输入帖子内容");
            return;
        }
        if (mHaveChosenPictureList.isEmpty()) {
            ToastUtil.warning(getApplication(), "请添加帖子图片");
            return;
        }
        String[] paths = new String[mHaveChosenPictureList.size()];
        for (int i = 0; i < mHaveChosenPictureList.size(); i++) {
            paths[i] = mHaveChosenPictureList.get(i).getImageFile().getAbsolutePath();
        }

        mBaseLiveData.postValue(Constant.SHOW_DIALOG);

        mModel.uploadBatch(paths, bmobFiles -> post(content, bmobFiles),
                e -> {
                    mBaseLiveData.postValue(Constant.DISMISS_DIALOG);
                    LogUtil.e("上传文件失败," + e.toString());
                    ToastUtil.error(getApplication(), "上传文件失败," + e.toString());
                });
    }

    /**
     * 发帖
     *
     * @param content
     * @param bmobFiles
     */
    private void post(String content, List<BmobFile> bmobFiles) {
        mModel.post(content, bmobFiles, mCheckedPostType, objectId -> {
            mBaseLiveData.postValue(Constant.DISMISS_DIALOG);
            LogUtil.i("帖子发表成功，objectId: " + objectId);
            ToastUtil.success(getApplication(), "发帖成功");
        }, throwable -> {
            mBaseLiveData.postValue(Constant.DISMISS_DIALOG);
            LogUtil.i("帖子发表失败，" + throwable.toString());
            ToastUtil.error(getApplication(), "帖子发表失败，" + throwable.toString());
        });
    }
}
