package com.example.flower.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.flower.base.BaseViewModel;
import com.example.flower.binding.command.BindingAction;
import com.example.flower.binding.command.BindingCommand;
import com.example.flower.http.bean.HomePageBean;
import com.example.flower.mvvm.model.SpecialModel;
import com.example.flower.mvvm.view.adapter.ArticleAdapter;
import com.example.flower.mvvm.view.adapter.RecommendedTodayAdapter;
import com.example.flower.mvvm.view.adapter.SpecialAdapter;
import com.example.flower.util.ToastUtil;

import java.util.Calendar;

/**
 * @author ShenBen
 * @date 2019/9/22 13:41
 * @email 714081644@qq.com
 */
public class SpecialViewModel extends BaseViewModel<SpecialModel> {

    public final ObservableField<String> calendarString = new ObservableField<>();
    public BindingCommand calendarCommand;

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
     * 专题RecyclerView配置
     */
    public StaggeredGridLayoutManager mRecommendedLayoutManager;
    public RecommendedTodayAdapter mRecommendedAdapter;
    public RecyclerView.ItemDecoration mRecommendedDecoration;

    public SpecialViewModel(@NonNull Application application) {
        super(application, new SpecialModel());
        initCommand();

        calendarString.set(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));

        mArticleLayoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        mArticleAdapter = new ArticleAdapter();
        mArticleItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(0, 0, 60, 0);
            }
        };

        mSpecialLayoutManager = new LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false);
        mSpecialAdapter = new SpecialAdapter();
        mSpecialItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(10, 6, 10, 6);
            }
        };

        mRecommendedLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecommendedLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecommendedAdapter = new RecommendedTodayAdapter();
        mRecommendedDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(10, 6, 10, 6);
            }
        };
        mRecommendedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(getApplication(), "点击位置: " + position);
            }
        });
    }

    private void initCommand() {
        calendarCommand = new BindingCommand(new BindingAction() {
            @Override
            public void execute() {
                ToastUtil.show(getApplication(), "点击了日历");
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refreshData();
    }

    private void refreshData() {
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

        mModel.getRecommendedToday(0, "", "", recommendedTodayBean -> mRecommendedAdapter.setNewData(recommendedTodayBean.getData()), null);
    }
}
