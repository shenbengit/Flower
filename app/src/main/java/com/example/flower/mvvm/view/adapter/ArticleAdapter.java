package com.example.flower.mvvm.view.adapter;

import com.example.flower.R;
import com.example.flower.databinding.ItemArticleBinding;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.view.adapter.base.BaseBindingAdapter;
import com.example.flower.mvvm.view.adapter.base.BaseBindingViewHolder;

/**
 * 主页上方推荐Adapter
 * @author ShenBen
 * @date 2019/9/22 21:52
 * @email 714081644@qq.com
 */
public class ArticleAdapter extends BaseBindingAdapter<HomePageBean.DataBean.CommunityHomePageFirstPlateViewBean.ArticleForFirstPlateViewsBean> {
    public ArticleAdapter() {
        super(R.layout.item_article);
    }

    @Override
    protected void convert(BaseBindingViewHolder helper, HomePageBean.DataBean.CommunityHomePageFirstPlateViewBean.ArticleForFirstPlateViewsBean item) {
        ItemArticleBinding binding = helper.getBinding();
        binding.setItem(item);
    }
}
