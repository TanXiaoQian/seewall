package com.seesong.seewall.view.activity.detail;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.seesong.seewall.R;
import com.seesong.seewall.databinding.ActivityDetailBinding;
import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.view.anim.easytrans.EasyTransition;
import com.seesong.seewall.view.anim.easytrans.EasyTransitionOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import razerdp.blur.PopupBlurOption;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class DetailActivity extends AppCompatActivity implements IDetailView {

    public static final String DATA = "data";


    public static void startActivity(AppCompatActivity context, Bundle bundle, EasyTransitionOptions options) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtras(bundle);
        // start transition
        EasyTransition.startActivity(intent, options);
    }

    private Wallpaper mWallpaper;

    @BindView(R.id.image_wall)
    ImageView imageDetail;

    @BindView(R.id.lottie_loading)
    LottieAnimationView lottieLoading;
    @BindView(R.id.back)
    AppCompatImageView back;

    private DetailToolPop toolPop;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_share:
                    if (mWallpaper != null && !TextUtils.isEmpty(mWallpaper.getSrc()))
                        onShare(mWallpaper.getSrc());
                    break;
                case R.id.image_photo:
                    imageDetail.setDrawingCacheEnabled(true);
                    Bitmap bitmap = imageDetail.getDrawingCache();
                    setWall(bitmap);
                    break;
                case R.id.image_download:

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ButterKnife.bind(this);
        mWallpaper = getIntent().getParcelableExtra(DATA);
        binding.setWallpaper(mWallpaper);
        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        initView();
    }


    private void initView() {
        EasyTransition.enter(this,
                500,
                new DecelerateInterpolator(),
                new AnimatorListenerAdapter() {

                });

        if (mWallpaper != null) {
            Glide.with(this).
                    load(mWallpaper.getSrc())
                    .error(R.drawable.pic_place_holder)
                    .into(new GlideDrawableImageViewTarget(imageDetail) {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            showLoading();
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            dismissLoading();
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            dismissLoading();
                        }
                    });

        }

    }


    @Override
    public void onBackPressed() {
        EasyTransition.exit(DetailActivity.this, 500, new DecelerateInterpolator());
    }

    @Override
    public void showLoading() {
        lottieLoading.setRepeatCount(ValueAnimator.INFINITE);
        lottieLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        lottieLoading.cancelAnimation();
        lottieLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showPop() {
        if (toolPop == null) {
            toolPop = new DetailToolPop(this);
            toolPop.setOnClickListener(mOnClickListener);
            toolPop.setOnBeforeShowCallback(new BasePopupWindow.OnBeforeShowCallback() {
                @Override
                public boolean onBeforeShow(View popupRootView, View anchorView, boolean hasShowAnima) {
                    PopupBlurOption option = new PopupBlurOption();
                    option.setBlurView(imageDetail)
                            .setFullScreen(false);
                    toolPop.setBlurOption(option);
                    return true;
                }
            });
        }
        toolPop.showPopupWindow();
    }


    @OnClick({R.id.image_wall, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_wall:
                showPop();
                break;
            case R.id.back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    private void setWall(Bitmap bitmap) {
        try {
            WallpaperManager wpm = (WallpaperManager) getSystemService(
                    Context.WALLPAPER_SERVICE);

            if (wpm != null) {
                wpm.setBitmap(bitmap);
                Toast.makeText(DetailActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("TAG", "Failed to set wallpaper: " + e);
        }
    }

    private void onShare(String url) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "这张图片猴厉害" + url);
        //自定义选择框的标题
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));
        //系统默认标题
        startActivity(shareIntent);

    }

    @Override
    public void onSave(String url) {

    }
}
