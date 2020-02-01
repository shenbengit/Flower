package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.base.BaseViewModel;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.FragmentPopularBinding;
import com.example.flower.http.bmob.PostTypeBean;
import com.example.flower.mvvm.view.adapter.PostFragmentAdapter;
import com.example.flower.util.RxUtil;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 热门 Fragment
 *
 * @author ShenBen
 * @date 2020/1/30 16:50
 * @email 714081644@qq.com
 */
public class PopularFragment extends BaseFragment<FragmentPopularBinding, BaseViewModel> {
    private PostFragmentAdapter mPostFragmentAdapter;

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popular;
    }

    @Override
    protected Class<BaseViewModel> getModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        mPostFragmentAdapter = new PostFragmentAdapter(this);
        mBinding.vpDetail.setAdapter(mPostFragmentAdapter);
        //ViewPager2 设置为禁止滑动
//        mBinding.vpDetail.setUserInputEnabled(false);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout,
                mBinding.vpDetail, (tab, position) -> tab.setText(mPostFragmentAdapter.getItem(position).getTypeName()));
        tabLayoutMediator.attach();

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        BmobQuery<PostTypeBean> query = new BmobQuery<>();
        //筛选状态为启用状态的
        query.addWhereEqualTo("isEnable", true);
        query.findObjectsObservable(PostTypeBean.class)
                .compose(RxUtil.io_main())
                .compose(bindToLifecycle())
                .subscribe(new Observer<List<PostTypeBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PostTypeBean> list) {
                        if (list != null && !list.isEmpty()) {
                            list.add(0, new PostTypeBean(Constant.ALL_POST_TYPE_ID, "全部", true));
                            mPostFragmentAdapter.setNewData(list);
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
