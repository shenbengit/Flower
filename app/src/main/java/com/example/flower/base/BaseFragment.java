package com.example.flower.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.example.flower.App;
import com.example.flower.R;
import com.example.flower.base.support.AbstractSupportFragment;


/**
 * @author Ben
 * @date 2018/8/1
 * Email: 714081644@qq.com
 */

public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends AbstractSupportFragment {

    protected VDB mBinding;

    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(mViewModel);
        mBinding.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((App) _mActivity.getApplicationContext()).getRefWatcher().watch(this);
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
        mViewModel = ViewModelProviders.of(this).get(getModelClass());
        mViewModel.injectLifecycleProvider(this);
        mBinding.setVariable(getVariableId(), mViewModel);
        getLifecycle().addObserver(mViewModel);
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
    protected abstract void initData();

}
