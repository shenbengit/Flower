package com.example.flower.binding.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.flower.GlideApp;
import com.example.flower.binding.command.BindingCommand;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxCompoundButton;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 封装DataBinding适配器
 *
 * @author ShenBen
 * @date 2018/11/22 9:16
 * @email 714081644@qq.com
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class DataBindingAdapter {

    /**
     * 防重复点击间隔(秒)
     * 默认1秒
     */
    private static final int CLICK_INTERVAL = 1;

    /**
     * View的onClick()事件
     * requireAll 是意思是是否需要绑定全部参数, false为否
     *
     * @param view            onClick事件绑定
     * @param command         命令绑定
     * @param isThrottleFirst 是否开启防止过快点击
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, BindingCommand command, boolean isThrottleFirst) {
        if (!isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(o -> {
                        if (command != null) {
                            command.execute();
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒内只允许点一次
                    .subscribe(o -> {
                        if (command != null) {
                            command.execute();
                        }
                    });
        }
    }

    /**
     * View的onLongClick()事件
     *
     * @param view    onLongClick事件绑定
     * @param command 命令绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "onLongClickCommand")
    public static void onLongClickCommand(View view, BindingCommand command) {
        RxView.longClicks(view)
                .subscribe(o -> {
                    if (command != null) {
                        command.execute();
                    }
                });
    }

    /**
     * view的焦点发生变化的事件绑定
     *
     * @param view    view
     * @param command 命令绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "onFocusChangeCommand")
    public static void onFocusChangeCommand(View view, BindingCommand<Boolean> command) {
        RxView.focusChanges(view)
                .subscribe(aBoolean -> {
                    if (command != null) {
                        command.execute(aBoolean);
                    }
                });
    }

    /**
     * view 的onTouch事件
     * {@link View#onTouchEvent(MotionEvent)}
     *
     * @param view    view
     * @param command 命令绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "onTouchCommand")
    public static void onTouchCommand(View view, BindingCommand<MotionEvent> command) {
        RxView.touches(view)
                .subscribe(motionEvent -> {
                    if (command != null) {
                        command.execute(motionEvent);
                    }
                });
    }

    /**
     * CompoundButton选中监听
     * 子类可用
     * {@link CompoundButton#setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener)}
     * </p>
     *
     * @param button  onCheckedChange事件绑定
     * @param command 命令绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "onCompoundButtonCheckedChangeCommand")
    public static void onCompoundButtonCheckedChangeCommand(CompoundButton button, BindingCommand<Boolean> command) {
        RxCompoundButton.checkedChanges(button)
                .subscribe(aBoolean -> {
                    if (command != null) {
                        command.execute(aBoolean);
                    }
                });
    }

    /**
     * EditText输入框中值变化监听
     *
     * @param editText EditText
     * @param command  命令绑定
     * @see TextWatcher
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "addTextChangedCommand")
    public static void addTextChangedCommand(EditText editText, BindingCommand<String> command) {
        RxTextView.textChanges(editText)
                .subscribe(charSequence -> {
                    if (command != null) {
                        command.execute(charSequence.toString());
                    }
                });
    }

    /**
     * Glide 加载图片url
     *
     * @param imageView           要加载的图片控件
     * @param o                   图片资源
     * @param placeholderImageRes 图片加载中显示展位图
     * @param errorImageRes       图片加载失败显示占位图
     * @param noLoadCache         加载图片是否使用缓存,默认使用缓存
     * @param roundingRadius      圆角
     * @param isBlur              是否高斯模糊
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"imageObject", "placeholderImageRes", "errorImageRes", "noLoadCache", "roundingRadius", "isBlur"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, Object o, Drawable placeholderImageRes, Drawable errorImageRes, boolean noLoadCache, int roundingRadius, boolean isBlur) {
        if (o != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholderImageRes)
                    .error(errorImageRes);
            //圆角大于0，则加入圆角，四个角都是圆角
            if (roundingRadius > 0) {
                RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
                options.transform(roundedCorners);
            }
            if (isBlur) {
                options.transform(new BlurTransformation(25, 3));
            }

            if (!noLoadCache) {
                GlideApp.with(imageView)
                        .load(o)
                        .apply(options)
                        .into(imageView);
            } else {
                GlideApp.with(imageView)
                        .load(o)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(options)
                        .into(imageView);
            }
        }
    }

    /**
     * RadioGroup中RadioButton选中状态判断并返回选中值
     * <p>
     * RadioButton需要给定id
     * </p>
     *
     * @param group   RadioGroup
     * @param command 命令绑定
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = "onRadioGroupCheckedChangeCommand")
    public static void onRadioGroupCheckedChangeCommand(RadioGroup group, BindingCommand<RadioButton> command) {
        RxRadioGroup.checkedChanges(group)
                .subscribe(integer -> {
                    if (command != null) {
                        RadioButton button = group.findViewById(integer);
                        command.execute(button);
                    }
                });
    }

    /**
     * RecyclerView 绑定LayoutManager、Adapter、ItemDecoration
     *
     * @param recyclerView   RecyclerView
     * @param layoutManager  LayoutManager
     * @param adapter        Adapter
     * @param itemDecoration ItemDecoration
     */
    @BindingAdapter(value = {"bindRecyclerViewLayoutManager", "bindRecyclerViewAdapter", "addRecyclerViewItemDecoration"}, requireAll = false)
    public static void bindRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter, RecyclerView.ItemDecoration itemDecoration) {
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        if (adapter != null) {
            adapter.bindToRecyclerView(recyclerView);
        }
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    @BindingAdapter(value = {"recyclerViewSmoothScrollToPosition"})
    public static void recyclerViewSmoothScrollToPosition(RecyclerView recyclerView, int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    @BindingAdapter(value = {"bindViewPager2Adapter"})
    public static void bindViewPager2(ViewPager2 viewPager2, RecyclerView.Adapter adapter) {
        if (adapter != null) {
            viewPager2.setAdapter(adapter);
        }
    }

    @BindingConversion
    public static String convertDate(long date) {
        if (date <= 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date(date));
    }
}
