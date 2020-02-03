package com.example.flower.mvvm.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseActivity;
import com.example.flower.constant.ARouterPath;
import com.example.flower.constant.Constant;
import com.example.flower.databinding.ActivityMainBinding;
import com.example.flower.mvvm.view.fragment.FindFragment;
import com.example.flower.mvvm.view.fragment.MineFragment;
import com.example.flower.mvvm.view.fragment.SpaceFragment;
import com.example.flower.mvvm.view.fragment.SpecialFragment;
import com.example.flower.mvvm.viewmodel.MainViewModel;
import com.example.flower.rxbus.RxSubscriptions;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import java.io.File;

import me.yokeyword.fragmentation.ISupportFragment;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;
import permissions.dispatcher.RuntimePermissions;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

@RuntimePermissions
@Route(path = ARouterPath.MAIN_ACTIVITY_PATH)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements CompoundButton.OnCheckedChangeListener {
    private ScaleAnimation animation;
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
        //SplashActivity移出回退栈
        setResult(RESULT_OK);
        mLastCheckId = R.id.rb01;

        //底部按钮添加选中事件监听
        mBinding.rb01.setOnCheckedChangeListener(this);
        mBinding.rb02.setOnCheckedChangeListener(this);
        mBinding.rb04.setOnCheckedChangeListener(this);
        mBinding.rb05.setOnCheckedChangeListener(this);

        mBinding.rb03.setOnClickListener(v -> {
            if (!PermissionUtils.hasSelfPermissions(MainActivity.this, Manifest.permission.CAMERA)) {
                MainActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(MainActivity.this);
                return;
            }
            ARouter.getInstance()
                    .build(ARouterPath.KNOW_FLOWER_PATH)
                    .navigation();
        });


        //以view中心为缩放点，由初始状态放大1.2倍
        animation = new ScaleAnimation(
                1.0f, 1.2f,
                1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(200);
        animation.setFillAfter(false);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mFragmentArray.put(R.id.rb01, SpecialFragment.newInstance());
        mFragmentArray.put(R.id.rb02, FindFragment.newInstance());
        mFragmentArray.put(R.id.rb04, SpaceFragment.newInstance());
        mFragmentArray.put(R.id.rb05, MineFragment.newInstance());

        ISupportFragment firstFragment = findFragment(SpecialFragment.class);
        if (firstFragment == null) {
            loadMultipleRootFragment(R.id.flRoot,
                    0,
                    mFragmentArray.get(R.id.rb01),
                    mFragmentArray.get(R.id.rb02),
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
        MainActivityPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureFileUtils.deleteAllCacheDirFile(this);
        RxSubscriptions.clear();
    }

    /**
     * 用户给予权限时执行此方法
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    void needsPermission() {
        LogUtil.i("获取到相关权限");
        //先创建拍照文件保存目录文件夹
        new File(Constant.PHOTO_SAVE_PATH).mkdirs();
        //先创建压缩文件保存目录文件夹
        new File(Constant.COMPRESS_SAVE_PATH).mkdirs();
    }

    /**
     * 解释为什么调用该权限，只有当第一次请求被拒绝，下次请求之前会调用
     */
    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    void permissionOnShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("申请获取读取SD卡、摄像机、录像权限")
                .setPositiveButton("确认", (dialog, which) -> {
                    dialog.dismiss();
                    request.proceed();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                    request.cancel();
                })
                .create()
                .show();
    }

    /**
     * 拒绝权限请求调用
     */
    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    void permissionOnPermissionDenied() {
        ToastUtil.warning(this, "保存壁纸等功能将无法使用");
    }

    /**
     * 用户点击了不再询问调用
     */
    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    void permissionOnNeverAskAgain() {
        ToastUtil.warning(this, "保存壁纸等功能将无法使用，若想使用请到设置里手动授权");
    }

    /**
     * 底部按钮选择事件
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //如果回调的是未点击的状态，则不处理
        if (!isChecked) {
            return;
        }
        if (mLastCheckId == buttonView.getId()) {
            return;
        }
        switch (mLastCheckId) {
            case R.id.rb01:
                mBinding.rb01.setChecked(false);
                break;
            case R.id.rb02:
                mBinding.rb02.setChecked(false);
                break;
            case R.id.rb04:
                mBinding.rb04.setChecked(false);
                break;
            case R.id.rb05:
                mBinding.rb05.setChecked(false);
                break;
        }
        //启动动画
        mBinding.ivCameraIcon.startAnimation(animation);
        showHideFragment(mFragmentArray.get(buttonView.getId()), mFragmentArray.get(mLastCheckId));
        mLastCheckId = buttonView.getId();
    }
}
