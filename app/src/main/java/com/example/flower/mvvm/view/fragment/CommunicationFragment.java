package com.example.flower.mvvm.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.flower.BR;
import com.example.flower.R;
import com.example.flower.base.BaseFragment;
import com.example.flower.databinding.FragmentCommunicationBinding;
import com.example.flower.mvvm.viewmodel.CommunicationViewModel;

/**
 * 交流Fragment
 *
 * @author ShenBen
 * @date 2019/9/22 13:40
 * @email 714081644@qq.com
 */
public class CommunicationFragment extends BaseFragment<FragmentCommunicationBinding, CommunicationViewModel> {

    public static CommunicationFragment newInstance() {
        return new CommunicationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_communication;
    }

    @Override
    protected Class<CommunicationViewModel> getModelClass() {
        return CommunicationViewModel.class;
    }

    @Override
    protected int getVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

    }
}
