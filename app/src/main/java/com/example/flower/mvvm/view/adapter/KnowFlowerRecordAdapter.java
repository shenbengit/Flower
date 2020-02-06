package com.example.flower.mvvm.view.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.http.bean.KnowFlowerResultBean;
import com.example.flower.http.bmob.IdentifyResultBean;

/**
 * @author ShenBen
 * @date 2020/2/5 12:16
 * @email 714081644@qq.com
 */
public class KnowFlowerRecordAdapter extends BaseQuickAdapter<IdentifyResultBean, BaseViewHolder> {

    public KnowFlowerRecordAdapter() {
        super(R.layout.item_konw_flower_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, IdentifyResultBean item) {
        helper.addOnClickListener(R.id.ivMore);
        //2020-02-05 16:04:35	时间格式
        String day = item.getCreatedAt().substring(8, 10);
        String month = getMoth(item.getCreatedAt().substring(5, 7));
        helper.setText(R.id.tvDay, day)
                .setText(R.id.tvMonth, month);
        ImageView ivPicture = helper.getView(R.id.ivPicture);
        if (item.getPicture() != null
                && item.getResults() != null
                && !item.getResults().isEmpty()) {
            KnowFlowerResultBean.ResponseBean.IdentifyResultsBean bean = item.getResults().get(0);
            GlideApp.with(ivPicture)
                    .load(item.getPicture().getFileUrl())
                    .placeholder(R.drawable.grey_bg)
                    .error(R.drawable.grey_bg)
                    .into(ivPicture);
            helper.setText(R.id.tvName, bean.getName())
                    .setText(R.id.tvDescribe, bean.getDesc());
        } else {
            GlideApp.with(ivPicture)
                    .load(R.drawable.grey_bg)
                    .placeholder(R.drawable.grey_bg)
                    .error(R.drawable.grey_bg)
                    .into(ivPicture);
            helper.setText(R.id.tvName, "")
                    .setText(R.id.tvDescribe, "");
        }
    }

    private String getMoth(String month) {
        switch (month) {
            case "01":
                return "一月";
            case "02":
                return "二月";
            case "03":
                return "三月";
            case "04":
                return "四月";
            case "05":
                return "五月";
            case "06":
                return "六月";
            case "07":
                return "七月";
            case "08":
                return "八月";
            case "09":
                return "九月";
            case "10":
                return "十月";
            case "11":
                return "十一月";
            case "12":
                return "十二月";
            default:
                return "";
        }
    }
}
