package com.example.flower.mvvm.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.flower.GlideApp;
import com.example.flower.R;
import com.example.flower.constant.ARouterPath;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

@Route(path = ARouterPath.VIDEO_ACTIVITY_PATH)
public class VideoActivity extends AppCompatActivity {

    public static final String VIDEO_URL = "VIDEO_URL";
    public static final String TITLE = "TITLE";
    /**
     * 视频占位图
     */
    public static final String THUMB_IMAGE_URL = "THUMB_IMAGE_URL";

    private String videoUrl;
    private String title;
    private String thumbImageUrl;

    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoPlayer = findViewById(R.id.videoPlayer);
        //透明状态栏
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        Intent intent = getIntent();
        if (intent != null) {
            videoUrl = intent.getStringExtra(VIDEO_URL);
            title = intent.getStringExtra(TITLE);
            thumbImageUrl = intent.getStringExtra(THUMB_IMAGE_URL);

            //增加封面
            ImageView imageView = new ImageView(this);
            GlideApp.with(imageView)
                    .load(thumbImageUrl)
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            videoPlayer.setThumbImageView(imageView);
            videoPlayer.setUp(videoUrl, false, title);
            //增加title
            videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
            //设置返回键
            videoPlayer.getBackButton().setVisibility(View.VISIBLE);
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            videoPlayer.getFullscreenButton().setOnClickListener(v -> orientationUtils.resolveByClick());
            //是否可以滑动调整
            videoPlayer.setIsTouchWiget(true);
            //设置返回按键功能
            videoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
            //设置旋转
            orientationUtils = new OrientationUtils(this, videoPlayer);

            videoPlayer.startPlayLogic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}
