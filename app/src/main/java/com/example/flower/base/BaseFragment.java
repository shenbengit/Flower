package com.example.flower.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.flower.App;
import com.example.flower.R;
import com.example.flower.base.support.AbstractSupportFragment;
import com.example.flower.constant.Constant;


/**
 * @author Ben
 * @date 2018/8/1
 * Email: 714081644@qq.com
 */

public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends AbstractSupportFragment {
    protected final String TAG = getClass().getSimpleName();

    protected VDB mBinding;

    protected VM mViewModel;
    /**
     * 加载中dialog
     */
    protected Dialog mLoadingDialog;

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
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getLifecycle().removeObserver(mViewModel);
        mBinding.unbind();
        mViewModel.mBaseLiveData.removeObservers(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((App) mActivity.getApplicationContext()).getRefWatcher().watch(this);
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

        //注意：子类不可再重新执行此方法，已防止崩溃，具体的回调请看[baseLiveDataObserver(String)]
        mViewModel.mBaseLiveData.observe(this, (Observer<String>) this::baseLiveDataObserver);
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


    /**
     * 设置ToolBar
     *
     * @param toolbar 传入子类中的ToolBar
     */
    protected void initToolbar(@NonNull Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.iv_back);
        toolbar.setNavigationOnClickListener(v -> onBackClick());
    }

    /**
     * 点击了ToolBar的返回按钮，一般来说不用重写
     */
    protected void onBackClick() {
        mActivity.onBackPressed();
    }


    /**
     * 由[BaseViewModel.getBaseLiveData]发送的消息都将在此回调，子类可以重写这个方法
     * 默认处理了[SHOW_LOADING_DIALOG]和[DISMISS_LOADING_DIALOG]这两个事件
     */
    protected void baseLiveDataObserver(String str) {
        switch (str) {
            case Constant.SHOW_DIALOG:
                mLoadingDialog.show();
                break;
            case Constant.DISMISS_DIALOG:
                mLoadingDialog.dismiss();
                break;
        }
    }

    /**
     * 可以重写这个方法自定义dialog
     */
    protected Dialog initLoadingDialog() {
        return new LoadingDailog.Builder(mActivity)
                .setMessage("请稍后...")
                .setCancelOutside(false)
                .setCancelable(false)
                .setShowMessage(true)
                .create();
    }

}
