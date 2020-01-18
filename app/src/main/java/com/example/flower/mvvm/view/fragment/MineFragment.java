package com.example.flower.mvvm.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentMineBinding;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.mvvm.viewmodel.MineViewModel;
import com.example.flower.util.LogUtil;

import cn.bmob.v3.BmobUser;

/**
 * 我的Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected Class<MineViewModel> getModelClass() {
        return MineViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        LogUtil.i("当前登录用户是否已经登录： " + BmobUser.isLogin());
        UserBean currentUser = BmobUser.getCurrentUser(UserBean.class);
        LogUtil.i("当前登录用户： " + currentUser);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("MineFragment - requestCode: " + requestCode);
    }
}
