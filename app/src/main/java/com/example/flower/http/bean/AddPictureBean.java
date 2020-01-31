package com.example.flower.http.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.File;

/**
 * @author ShenBen
 * @date 2020/1/31 16:16
 * @email 714081644@qq.com
 */
public class AddPictureBean implements MultiItemEntity {
    /**
     * @see com.example.flower.mvvm.view.adapter.AddPictureAdapter#ADD_PICTURE
     * @see com.example.flower.mvvm.view.adapter.AddPictureAdapter#NORMAL_PICTURE
     */
    private int itemType;

    private File imageFile;

    public AddPictureBean() {
    }

    public AddPictureBean(int itemType) {
        this.itemType = itemType;
    }

    public AddPictureBean(int itemType, File imageFile) {
        this.itemType = itemType;
        this.imageFile = imageFile;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
