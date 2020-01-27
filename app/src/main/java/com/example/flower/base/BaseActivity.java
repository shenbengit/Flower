package com.example.flower.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.flower.R;
import com.example.flower.base.support.AbstractSupportActivity;
import com.example.flower.constant.Constant;


/**
 * @author Ben
 * @date 2018/8/1
 * Email: 714081644@qq.com
 */

public abstract class BaseActivity<VDB extends ViewDataBinding, VM extends BaseViewModel> extends AbstractSupportActivity {

    protected final String TAG = getClass().getSimpleName();

    protected VDB mBinding;
    protected VM mViewModel;
    /**
     * 加载中dialog
     */
    protected Dialog mLoadingDialog;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        beforeSetContentView();
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init();
        mLoadingDialog = initLoadingDialog();
        initView();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mViewModel);
        mBinding.unbind();
        mViewModel.mBaseLiveData.removeObservers(this);
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
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.iv_back);
        toolbar.setNavigationOnClickListener(v -> onBackClick());
    }

    /**
     * 点击了ToolBar的返回按钮，一般来说不用重写
     */
    protected void onBackClick() {
        onBackPressedSupport();
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
        return new LoadingDailog.Builder(this)
                .setMessage("请稍后...")
                .setCancelOutside(false)
                .setCancelable(false)
                .setShowMessage(true)
                .create();
    }
}
