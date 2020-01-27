package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.http.bmob.UserBean;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author ShenBen
 * @date 2020/1/4 17:44
 * @email 714081644@qq.com
 */
public class LoginViewModel extends BaseViewModel {

    private static final int SMS_CODE = 101;
    /**
     * 登录成功标识
     */
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    /**
     * 手机号
     */
    public ObservableField<String> phoneStr;
    /**
     * 验证码
     */
    public ObservableField<String> verificationCodeStr;
    /**
     * 倒计时字符
     */
    public ObservableField<String> countDownStr;
    /**
     * 获取验证码点击事件
     */
    public BindingCommand getCodeCommand;
    /**
     * 登录点击事件
     */
    public BindingCommand loginCommand;

    private boolean isRequestSMSCode = false;
    /**
     * 发送验证码倒计时60秒
     */
    private int countDownSecond = 60;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == SMS_CODE) {
                if (countDownSecond <= 0) {
                    isRequestSMSCode = false;
                    countDownSecond = 60;
                    countDownStr.set("立即获取");
                    return;
                }
                --countDownSecond;
                countDownStr.set(countDownSecond + "秒");
                sendEmptyMessageDelayed(SMS_CODE, 1000);
            }
        }
    };

    public LoginViewModel(@NonNull Application application) {
        super(application);
        phoneStr = new ObservableField<>("");
        verificationCodeStr = new ObservableField<>("");
        countDownStr = new ObservableField<>("立即获取");
        getCodeCommand = new BindingCommand(this::requestSMSCode);

        loginCommand = new BindingCommand(this::loginUser);
    }

    /**
     * 请求短信验证码
     */
    private void requestSMSCode() {
        if (isRequestSMSCode) {
            return;
        }
        String phone = phoneStr.get();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.warning(getApplication(), "请输入手机号");
            return;
        }
        if (!phone.matches("0?(13|14|15|17|18|19)[0-9]{9}")) {
            ToastUtil.warning(getApplication(), "请输入正确的手机号");
            return;
        }
        isRequestSMSCode = true;
        mHandler.sendEmptyMessage(SMS_CODE);
        BmobSMS.requestSMSCode(phone, "花田", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    ToastUtil.success(getApplication(), "发送验证码成功");
                } else {
                    mHandler.removeCallbacksAndMessages(SMS_CODE);
                    isRequestSMSCode = false;
                    countDownSecond = 60;
                    countDownStr.set("立即获取");

                    LogUtil.e("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage());
                    ToastUtil.error(getApplication(), "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }

    /**
     * 用户登录
     */
    private void loginUser() {
        String phone = phoneStr.get();
        String code = verificationCodeStr.get();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.warning(getApplication(), "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.warning(getApplication(), "请输入验证码");
            return;
        }
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean user, BmobException e) {
                if (e == null) {
                    ToastUtil.success(getApplication(), "登录成功");
                    mHandler.postDelayed(() -> mBaseLiveData.setValue(LOGIN_SUCCESS), 1000);
                } else {
                    ToastUtil.error(getApplication(), "登录失败：" + e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
