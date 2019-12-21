package com.example.flower.mvvm.view.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bean.PlantsDetailBean;

/**
 * @author ShenBen
 * @date 2019/12/1 16:46
 * @email 714081644@qq.com
 */
public class PlantsDetailDialog extends Dialog {
    private ImageView ivClose;
    private ImageView ivPicture;
    private TextView tvPlantName;
    private TextView tvSmell;
    private TextView tvHumidity;
    private TextView tvMaintain;
    private TextView tvLanguage;

    public PlantsDetailDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public PlantsDetailDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_plants_detail);
        ivClose = findViewById(R.id.ivClose);
        ivPicture = findViewById(R.id.ivPicture);
        tvPlantName = findViewById(R.id.tvPlantName);
        tvSmell = findViewById(R.id.tvSmell);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvMaintain = findViewById(R.id.tvMaintain);
        tvLanguage = findViewById(R.id.tvLanguage);
        ivClose.setOnClickListener(v -> dismiss());
    }

    @SuppressLint("CheckResult")
    public void setDetail(PlantsDetailBean.DataBean bean) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.grey_bg)
                .error(R.drawable.grey_bg);
        //圆角大于0，则加入圆角，四个角都是圆角
        options.transform(new RoundedCorners((int) getContext().getResources().getDimension(R.dimen.dp_10)));
        GlideApp.with(ivPicture)
                .load(bean.getFlowerImage())
                .apply(options)
                .into(ivPicture);
        tvPlantName.setText(bean.getFlowerName());
        tvSmell.setText(bean.getFlowerSmell());
        tvHumidity.setText(bean.getFlowerHumidity());
        tvMaintain.setText(bean.getFlowerMaintain());
        tvLanguage.setText(bean.getFlowerLanguage());
    }

}
