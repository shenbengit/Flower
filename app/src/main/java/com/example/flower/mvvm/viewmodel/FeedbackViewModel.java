package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.Constant;
import com.example.flower.http.bmob.FeedbackBean;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;

import cn.bmob.v3.BmobUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ShenBen
 * @date 2020/2/8 15:14
 * @email 714081644@qq.com
 */
public class FeedbackViewModel extends BaseViewModel {
    /**
     * 建议内容
     */
    public ObservableField<String> opinionStr = new ObservableField<>();
    /**
     * 联系方式
     */
    public ObservableField<String> contactStr = new ObservableField<>();

    public BindingCommand sendCommand;


    public FeedbackViewModel(@NonNull Application application) {
        super(application);
        sendCommand = new BindingCommand(this::sendOpinion);
    }

    /**
     * 发送建议
     */
    private void sendOpinion() {
        String opinion = opinionStr.get();
        if (TextUtils.isEmpty(opinion)) {
            ToastUtil.warning(getApplication(), "请输入反馈内容！");
            return;
        }
        String contact = contactStr.get();
        if (!TextUtils.isEmpty(contact)) {
            if (!contact.matches("0?(13|14|15|17|18|19)[0-9]{9}")//手机号
                    && !contact.matches("[1-9]([0-9]{4,10})")//QQ号
                    && !contact.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}")//邮箱
            ) {
                ToastUtil.warning(getApplication(), "联系方式输入有误！");
                return;
            }
        }
        FeedbackBean bean = new FeedbackBean();
        bean.setUser(BmobUser.getCurrentUser(UserBean.class));
        bean.setOpinion(opinion);
        bean.setContactInformation(contact);
        bean.saveObservable()
                .compose(mLifecycleProvider.bindToLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        ToastUtil.success(getApplication(), "意见反馈成功！");
                        mBaseLiveData.setValue(Constant.BACK_PRESSED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.error(getApplication(), "意见反馈失败！" + e.toString());
                        LogUtil.e("意见反馈失败！" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
