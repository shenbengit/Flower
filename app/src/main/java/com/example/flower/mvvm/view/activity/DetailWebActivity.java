package com.example.flower.mvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import com.example.flower.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityDetailWebBinding;
import com.example.flower.http.bean.SpecialDetailBean;
import com.example.flower.mvvm.viewmodel.DetailWebViewModel;

/**
 * 网页详情Activity，包括专题详情，推荐详情
 */
@Route(path = ARouterPath.DETAIL_WEB_ACTIVITY_PATH)
public class DetailWebActivity extends BaseActivity<ActivityDetailWebBinding, DetailWebViewModel> {

    public static final String DETAIL_BEAN = "DETAIL_BEAN";
    private SpecialDetailBean.DataBean mBean;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_web;
    }

    @Override
    protected Class<DetailWebViewModel> getModelClass() {
        return DetailWebViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent != null) {
            mBean = intent.getParcelableExtra(DETAIL_BEAN);
        }
        if (mBean != null) {
            mBinding.toolbar.setTitle(mBean.getName());
        }
        initToolbar(mBinding.toolbar);
        webView=mBinding.webView;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //优先使用缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        //webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (mBean != null) {
            mViewModel.getArticleInfo("", mBean.getId());
            webView.loadUrl(Constant.BASE_URL + mBean.getDetailUrl());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    /**
     * 点击返回 先判断webView是否可以后退，如果可以则后退，否者不处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearCache(true);
            webView.clearHistory();
            webView.clearFormData();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onBackClick() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackClick();
    }
}
