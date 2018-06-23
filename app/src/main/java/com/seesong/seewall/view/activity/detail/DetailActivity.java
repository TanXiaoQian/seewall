package com.seesong.seewall.view.activity.detail;

import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seesong.seewall.R;
import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.presenter.DetailPresenter;
import com.seesong.seewall.view.activity.BaseActivity;
import com.seesong.seewall.view.anim.easytrans.EasyTransition;
import com.seesong.seewall.view.anim.easytrans.EasyTransitionOptions;


import butterknife.BindView;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class DetailActivity extends BaseActivity implements IDetailView {

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        EasyTransition.enter(this,
                500,
                new DecelerateInterpolator(),
                new AnimatorListenerAdapter() {

                });
        mWallpaper = getIntent().getParcelableExtra(DATA);
        if (mWallpaper != null)
            Glide.with(this).
                    load(mWallpaper.getSrc())
                    .into(imageDetail);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DetailPresenter(this, this);
    }


    @Override
    public void onBackPressed() {
        EasyTransition.exit(DetailActivity.this, 500, new DecelerateInterpolator());
    }
}
