package com.example.flower.binding.command;

/**
 * @author ShenBen
 * @date 2018/11/22 9:23
 * @email 714081644@qq.com
 */
public class BindingCommand<T> {
    private BindingAction mAction;
    private BindingConsumer<T> mConsumer;
    /**
     * 表示是否当前可以执行
     * 默认可以执行
     */
    private boolean isCanExecuted = true;

    public BindingCommand(BindingAction mAction) {
        this.mAction = mAction;
    }

    public BindingCommand(BindingAction mAction, boolean isCanExecuted) {
        this.mAction = mAction;
        this.isCanExecuted = isCanExecuted;
    }

    public BindingCommand(BindingConsumer<T> mConsumer) {
        this.mConsumer = mConsumer;
    }

    public BindingCommand(BindingConsumer<T> mConsumer, boolean isCanExecuted) {
        this.mConsumer = mConsumer;
        this.isCanExecuted = isCanExecuted;
    }

    public void execute() {
        if (mAction != null && isCanExecuted) {
            mAction.execute();
        }
    }

    public void execute(T parameter) {
        if (mConsumer != null && isCanExecuted) {
            mConsumer.execute(parameter);
        }
    }

    public boolean isCanExecuted() {
        return isCanExecuted;
    }

    public void setCanExecuted(boolean canExecuted) {
        isCanExecuted = canExecuted;
    }
}
