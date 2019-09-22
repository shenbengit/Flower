package com.example.flower.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.example.flower.base.support.AbstractSupportActivity;


/**
 * @author Ben
 * @date 2018/8/1
 * Email: 714081644@qq.com
 */

public abstract class BaseActivity<VDB extends ViewDataBinding, VM extends BaseViewModel> extends AbstractSupportActivity {

    protected final String TAG = getClass().getSimpleName();

    protected VDB mBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        beforeSetContentView();
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init();
        initView();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mViewModel);
        mBinding.unbind();
    }

    /**
     * 在设置View之前调用
     */
    protected void beforeSetContentView() {

    }

    /**
     * 获取布局文件id
     *
     * @return 返回布局文件id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化ViewModel、DataBinding
     */
    private void init() {
        //拿到ViewModel实例
        mViewModel = ViewModelProviders.of(this).get(getModelClass());
        mViewModel.injectLifecycleProvider(this);
        //绑定ViewModel的生命周期
        getLifecycle().addObserver(mViewModel);
        mBinding.setVariable(getVariableId(), mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    /**
     * 获取ViewModel
     *
     * @return
     */
    protected abstract Class<VM> getModelClass();

    /**
     * 获取ViewModel的id
     *
     * @return BR的id
     */
    protected abstract int getVariableId();

    /**
     * 初始化view，控件初始化设置相关监听等在此方法执行
     */
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);
}
