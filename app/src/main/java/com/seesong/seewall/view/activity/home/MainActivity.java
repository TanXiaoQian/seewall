package com.seesong.seewall.view.activity.home;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seesong.seewall.R;
import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.presenter.HomePresenter;
import com.seesong.seewall.view.activity.BaseActivity;
import com.seesong.seewall.view.activity.detail.DetailActivity;
import com.seesong.seewall.view.activity.home.adapter.WallPagerAdapter;
import com.seesong.seewall.view.anim.BlurTransformation;
import com.seesong.seewall.view.anim.easytrans.EasyTransitionOptions;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by tanxiaoqian on 2018/6/18.
 */

public class MainActivity extends BaseActivity<HomePresenter> implements IMainView {

    public static void startActivity(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vs_home_content)
    ViewStub vsHomeContent;
    @BindView(R.id.lottie_loading)
    LottieAnimationView lottieLoading;


    @BindView(R.id.discrete_image)
    DiscreteScrollView discreteImage;
    @BindView(R.id.bg_img1)
    ImageView bgImg1;
    @BindView(R.id.bg_img2)
    ImageView bgImg2;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_scenery)
    RadioButton rbScenery;
    @BindView(R.id.rb_cartoon)
    RadioButton rbCartoon;
    @BindView(R.id.rb_creativity)
    RadioButton rbCreativity;
    @BindView(R.id.rb_automobile)
    RadioButton rbAutomobile;
    @BindView(R.id.rb_game)
    RadioButton rbGame;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.rb_belle)
    RadioButton rbBelle;
    @BindView(R.id.rb_films)
    RadioButton rbFilms;


    private LinearLayout mErrorContainers;
    private LottieAnimationView mLottieNetError;
    private Button mStatusReloadButton;

    private boolean isIntentTriggered;
    private int mMaxIndex;
    private int mPreIntentPos;
    private BlurTransformation mBlurTransformation;


    private List<Wallpaper> wallpapers;
    private WallPagerAdapter mWallPagerAdapter;

    private Map<Integer, String> mCateMap;
    private int mCurrentCate;


    private DiscreteScrollView.ScrollStateChangeListener mScrollStateChangeListener = new DiscreteScrollView.ScrollStateChangeListener() {
        @Override
        public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
            isIntentTriggered = true;
        }

        @Override
        public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {

            mMaxIndex = adapterPosition > mMaxIndex ? adapterPosition : mMaxIndex;
            boolean isOdd = adapterPosition % 2 != 0;

            List<Wallpaper> data = mWallPagerAdapter.getData();
            int size = data.size();
            if (isOdd) {
                displayBgImage(data.get(adapterPosition), bgImg2);

                if (mMaxIndex < adapterPosition + 1 && adapterPosition + 1 < size) {// 预加载右边一张
                    displayBgImage(data.get(adapterPosition + 1), bgImg1);
                }

            } else {
                displayBgImage(data.get(adapterPosition), bgImg1);
                if (mMaxIndex < adapterPosition + 1 && adapterPosition + 1 < size) { // 预加载右边一张
                    displayBgImage(data.get(adapterPosition + 1), bgImg2);
                }
            }

            if (Math.abs(adapterPosition - mWallPagerAdapter.getData().size()) <= 3) {
                mPresenter.loadMore();
            }


        }

        @Override
        public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
            float position = Math.abs(scrollPosition);
            boolean isOdd = currentPosition % 2 != 0;

            List<Wallpaper> data = mWallPagerAdapter.getData();
            int size = data.size();

            if (mPreIntentPos != newPosition) {
                isIntentTriggered = false;
            }

            if (isOdd) {
                if (!isIntentTriggered && newPosition >= 0 && newPosition <= size - 1) {
                    displayBgImage(data.get(newPosition), bgImg1);
                    isIntentTriggered = true;
                }
                bgImg1.setAlpha(position);
                bgImg2.setAlpha(1 - position);
            } else {
                if (!isIntentTriggered && newPosition >= 0 && newPosition <= size - 1) {
                    displayBgImage(data.get(newPosition), bgImg2);

                    isIntentTriggered = true;
                }

                bgImg1.setAlpha(1 - position);
                bgImg2.setAlpha(position);
            }

            mPreIntentPos = newPosition;
        }
    };

    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            Wallpaper wallpaper = (Wallpaper) view.getTag(R.id.item_data);


            EasyTransitionOptions options = EasyTransitionOptions.makeTransitionOptions(
                    MainActivity.this, view
            );
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailActivity.DATA, wallpaper);


            DetailActivity.startActivity(MainActivity.this, bundle, options);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        initCategory();
        initWallScrollView();
        mBlurTransformation = new BlurTransformation(this, 10);
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mCurrentCate != checkedId) {
                    mPresenter.changeCate(mCateMap.get(checkedId));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                mCurrentCate = checkedId;
            }
        });

    }


    @Override
    protected void initPresenter() {
        mPresenter = new HomePresenter(this, this);
        mPresenter.loadData();
    }

    private void initCategory() {
        String font = "font.ttf";
        Typeface typeFace = Typeface.createFromAsset(getAssets(), font);
        rbAll.setTypeface(typeFace);
        rbScenery.setTypeface(typeFace);
        rbCartoon.setTypeface(typeFace);
        rbCreativity.setTypeface(typeFace);
        rbAutomobile.setTypeface(typeFace);
        rbGame.setTypeface(typeFace);
        rbBelle.setTypeface(typeFace);
        rbFilms.setTypeface(typeFace);

        mCateMap = new HashMap<>();
        mCateMap.put(R.id.rb_all, getString(R.string.all));
        mCateMap.put(R.id.rb_belle, getString(R.string.belle));
        mCateMap.put(R.id.rb_scenery, getString(R.string.scenery));
        mCateMap.put(R.id.rb_cartoon, getString(R.string.cartoon));
        mCateMap.put(R.id.rb_creativity, getString(R.string.creativity));
        mCateMap.put(R.id.rb_automobile, getString(R.string.automobile));
        mCateMap.put(R.id.rb_game, getString(R.string.game));
        mCateMap.put(R.id.rb_films, getString(R.string.films));
    }

    private void initWallScrollView() {
        discreteImage.setItemTransformer(
                new ScaleTransformer.Builder()
                        .setMinScale(0.8f)
                        .build()
        );
        wallpapers = new ArrayList<>();
        discreteImage.addScrollStateChangeListener(mScrollStateChangeListener);
        mWallPagerAdapter = new WallPagerAdapter(wallpapers);
        mWallPagerAdapter.setOnItemChildClickListener(mOnItemChildClickListener);
        discreteImage.setAdapter(mWallPagerAdapter);
    }


    private void displayBgImage(Wallpaper wallpaper, ImageView imageView) {
        Glide.with(this)
                .load(wallpaper.getSrc())
                .transform(mBlurTransformation)
                .placeholder(R.drawable.pic_place_holder)
                .error(R.drawable.pic_place_holder)
                .crossFade()
                .into(imageView);
    }

    private void inflate() {
        vsHomeContent.inflate();
        mErrorContainers = findViewById(R.id.error_containers);
        mLottieNetError = findViewById(R.id.lottie_net_error);
        mStatusReloadButton = findViewById(R.id.status_reload_button);
        mStatusReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadData();
            }
        });
    }

    @Override
    public void onSuccess(List<Wallpaper> wallpapers) {
        if (mErrorContainers != null) {
            mErrorContainers.setVisibility(View.GONE);
        }
        mWallPagerAdapter.addData(wallpapers);
    }


    @Override
    public void dismissLoading() {
        lottieLoading.setVisibility(View.GONE);
        lottieLoading.cancelAnimation();
    }

    @Override
    public void showLoading() {
        lottieLoading.setRepeatCount(ValueAnimator.INFINITE);
        lottieLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMore(List<Wallpaper> wallpapers) {
        mWallPagerAdapter.addData(wallpapers);
    }

    @Override
    public void onCateChange(List<Wallpaper> wallpapers) {
        mWallPagerAdapter.setNewData(wallpapers);
    }

    @Override
    public void onError(Throwable e) {
        inflate();
    }

    @Override
    public void initBackground(final List<Wallpaper> wallpapers) {
        if (wallpapers != null) {
            if (wallpapers.size() > 0) {
                bgImg1.animate().alpha(1).setDuration(1000)
                        .withStartAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(wallpapers.get(0), bgImg1);
                            }
                        });
            }
            if (wallpapers.size() > 1) {
                bgImg2.animate().alpha(0).setDuration(1000)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                displayBgImage(wallpapers.get(1), bgImg2);
                            }
                        });
            }
        }
    }


}
