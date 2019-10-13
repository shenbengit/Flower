package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.flower.R;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.constant.ARouterPath;
import com.example.flower.http.bean.DailyDiscoveryBean;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.model.SpecialModel;
import com.example.flower.mvvm.view.activity.ArticleDetailWebActivity;
import com.example.flower.mvvm.view.activity.SpecialTypeActivity;
import com.example.flower.mvvm.view.adapter.ArticleAdapter;
import com.example.flower.mvvm.view.adapter.DailyDiscoveryAdapter;
import com.example.flower.mvvm.view.adapter.SpecialAdapter;
import com.example.flower.util.LogUtil;
import com.example.flower.util.ToastUtil;

import java.util.Calendar;
import java.util.List;

/**
 * @author ShenBen
 * @date 2019/9/22 13:41
 * @email 714081644@qq.com
 */
public class SpecialViewModel extends BaseViewModel<SpecialModel> {

    public final ObservableField<String> calendarString = new ObservableField<>();

    public BindingCommand searchCommand;

    /**
     * 下拉刷新成功
     */
    public static final String REFRESH_SUCCESS = "REFRESH_SUCCESS";
    /**
     * 下拉刷新失败
     */
    public static final String REFRESH_FAIL = "REFRESH_FAIL";
    /**
     * 上拉加载成功
     */
    public static final String LOAD_MORE_SUCCESS = "LOAD_MORE_SUCCESS";
    /**
     * 上拉加载失败
     */
    public static final String LOAD_MORE_FAIL = "LOAD_MORE_FAIL";
    /**
     * 上拉加载完所有数据
     */
    public static final String LOAD_MORE_COMPLETE = "LOAD_MORE_COMPLETE";

    public BindingCommand todayCommand;

    /**
     * 推荐相关RecyclerView配置
     */
    public LinearLayoutManager mArticleLayoutManager;
    public ArticleAdapter mArticleAdapter;
    public RecyclerView.ItemDecoration mArticleItemDecoration;

    /**
     * 专题RecyclerView配置
     */
    public LinearLayoutManager mSpecialLayoutManager;
    public SpecialAdapter mSpecialAdapter;
    public RecyclerView.ItemDecoration mSpecialItemDecoration;

    /**
     * 每日发现RecyclerView配置
     */
    public LinearLayoutManager mDiscoveryLayoutManager;
    public DailyDiscoveryAdapter mDiscoveryAdapter;
    public RecyclerView.ItemDecoration mDiscoveryDecoration;

    private int mDiscoveryPageIndex = -1;

    public SpecialViewModel(@NonNull Application application) {
        super(application, new SpecialModel());
        initCommand();

        calendarString.set(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));

        mArticleLayoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        mArticleAdapter = new ArticleAdapter();
        mArticleAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageBean.DataBean.CommunityHomePageFirstPlateViewBean.ArticleForFirstPlateViewsBean bean = mArticleAdapter.getItem(position);
            ARouter.getInstance()
                    .build(ARouterPath.ARTICLE_DETAIL_WEB_ACTIVITY_PATH)
                    .withParcelable(ArticleDetailWebActivity.ARTICLE_DETAIL, bean)
                    .navigation();
        });
        mArticleItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int right = (int) getApplication().getResources().getDimension(R.dimen.dp_60);
                outRect.set(0, 0, right, 0);
            }
        };

        mSpecialLayoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        mSpecialAdapter = new SpecialAdapter();
        mSpecialAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageBean.DataBean.CommunityHomePageSecondPlateViewBean.CategoryForSecondPlateViewsBean item = mSpecialAdapter.getItem(position);
            ARouter.getInstance()
                    .build(ARouterPath.SPECIAL_TYPE_ACTIVITY_PATH)
                    .withParcelable(SpecialTypeActivity.SPECIAL_DETAIL, item)
                    .navigation();
        });
        mSpecialItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int left;
                int right;
                left = right = (int) getApplication().getResources().getDimension(R.dimen.dp_7);
                outRect.set(left, 0, right, 0);
            }
        };

        mDiscoveryLayoutManager = new LinearLayoutManager(getApplication());
        mDiscoveryAdapter = new DailyDiscoveryAdapter();
        mDiscoveryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(getApplication(), "点击位置: " + position);
            }
        });

        mDiscoveryDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int left;
                int right;
                int top;
                int bottom;
                left = right = (int) getApplication().getResources().getDimension(R.dimen.dp_20);
                top = (int) getApplication().getResources().getDimension(R.dimen.dp_5);
                bottom = (int) getApplication().getResources().getDimension(R.dimen.dp_10);
                outRect.set(left, top, right, bottom);
            }
        };

    }

    private void initCommand() {
        searchCommand = new BindingCommand(() -> ToastUtil.show(getApplication(), "此功能暂未开放"));
        todayCommand = new BindingCommand(() -> ARouter.getInstance()
                .build(ARouterPath.TODAY_ACTIVITY_PATH)
                .navigation());
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        refreshData();
    }

    public void refreshData() {
        mModel.getHomePageForNewVersion("null", homePageBean -> {
            if (homePageBean.getData() != null) {
                HomePageBean.DataBean data = homePageBean.getData();
                if (data.getCommunityHomePageFirstPlateView() != null) {
                    mArticleAdapter.setNewData(data.getCommunityHomePageFirstPlateView().getArticleForFirstPlateViews());
                }
                if (data.getCommunityHomePageSecondPlateView() != null) {
                    mSpecialAdapter.setNewData(data.getCommunityHomePageSecondPlateView().getCategoryForSecondPlateViews());
                }
            }
        }, null);
        getRecommendedToday(false);
    }

    public void getRecommendedToday(boolean isLoadMore) {
        if (!isLoadMore) {
            mDiscoveryPageIndex = -1;
        }
        ++mDiscoveryPageIndex;
        mModel.getDailyDiscovery(mDiscoveryPageIndex, "", "", bean -> {
            List<DailyDiscoveryBean.DataBean> data = bean.getData();
            //如果数据为空
            if (data == null || data.isEmpty()) {
                if (isLoadMore) {
                    //如果是上拉加载
                    //则说明已经加载完所有数据
                    mBaseLiveData.setValue(LOAD_MORE_COMPLETE);
                } else {
                    //如果是下拉刷新
                    //则说明没有数据
                    mBaseLiveData.setValue(REFRESH_SUCCESS);
                    mDiscoveryAdapter.setNewData(null);
                    ToastUtil.show(getApplication(), "没有数据");
                }
                return;
            }
            //数据不为空的情况
            if (isLoadMore) {
                //上拉加载数据成功
                mDiscoveryAdapter.addData(data);
                mBaseLiveData.setValue(LOAD_MORE_SUCCESS);
            } else {
                //下拉刷新数据成功
                mDiscoveryAdapter.setNewData(data);
                mBaseLiveData.setValue(REFRESH_SUCCESS);
            }

        }, throwable -> {
            LogUtil.e("获取今日推荐失败，message：" + throwable.getMessage());
            if (isLoadMore) {
                mBaseLiveData.setValue(LOAD_MORE_FAIL);
            } else {
                mBaseLiveData.setValue(REFRESH_FAIL);
            }
        });
    }
}
