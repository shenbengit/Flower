package com.example.flower.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author ShenBen
 * @date 2020/1/25 15:35
 * @email 714081644@qq.com
 */
public class PlantBase64Bean {
    @SerializedName("image_data")
    private String imageData;

    public PlantBase64Bean() {

    }

    public PlantBase64Bean(String imageData) {
        this.imageData = imageData;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
