package com.example.flower.mvvm.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Window;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.databinding.ActivityMainBinding;
import com.example.flower.mvvm.view.fragment.CollegeFragment;
import com.example.flower.mvvm.view.fragment.CommunicationFragment;
import com.example.flower.mvvm.view.fragment.FindFragment;
import com.example.flower.mvvm.view.fragment.MineFragment;
import com.example.flower.mvvm.view.fragment.SpecialFragment;
import com.example.flower.mvvm.viewmodel.MainViewModel;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import me.yokeyword.fragmentation.ISupportFragment;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

@Route(path = ARouterPath.MAIN_ACTIVITY_PATH)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private final SparseArray<ISupportFragment> mFragmentArray = new SparseArray<>(5);
    private int mLastCheckId;

    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        setResult(RESULT_OK);
        mLastCheckId = R.id.rb01;

        RxRadioGroup.checkedChanges(mBinding.rg)
                .subscribe(id -> {
                    if (mLastCheckId == id) {
                        return;
                    }
                    showHideFragment(mFragmentArray.get(id), mFragmentArray.get(mLastCheckId));
                    mLastCheckId = id;
                });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mFragmentArray.put(R.id.rb01, SpecialFragment.newInstance());
        mFragmentArray.put(R.id.rb02, FindFragment.newInstance());
        mFragmentArray.put(R.id.rb03, CommunicationFragment.newInstance());
        mFragmentArray.put(R.id.rb04, CollegeFragment.newInstance());
        mFragmentArray.put(R.id.rb05, MineFragment.newInstance());

        ISupportFragment firstFragment = findFragment(SpecialFragment.class);
        if (firstFragment == null) {
            loadMultipleRootFragment(R.id.flRoot,
                    0,
                    mFragmentArray.get(R.id.rb01),
                    mFragmentArray.get(R.id.rb02),
                    mFragmentArray.get(R.id.rb03),
                    mFragmentArray.get(R.id.rb04),
                    mFragmentArray.get(R.id.rb05));
        }

        //EXOPlayer内核，支持格式更多
        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
        //exo缓存模式，支持m3u8，只支持exo
        CacheFactory.setCacheManager(ProxyCacheManager.class);
        //切换渲染模式
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        //切换绘制模式
        GSYVideoType.setRenderType(GSYVideoType.TEXTURE);
    }
}
