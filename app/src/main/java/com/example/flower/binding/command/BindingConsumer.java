package com.example.flower.binding.command;

/**
 * @author ShenBen
 * @date 2018/11/22 9:27
 * @email 714081644@qq.com
 */
public interface BindingConsumer<T> {
    void execute(T t);
}
