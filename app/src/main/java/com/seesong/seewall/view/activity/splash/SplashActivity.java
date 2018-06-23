package com.seesong.seewall.view.activity.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.seesong.seewall.R;
import com.seesong.seewall.view.activity.BaseActivity;
import com.seesong.seewall.view.activity.home.MainActivity;

import butterknife.BindView;

/**
 * Created by tanxiaoqian on 2018/6/23.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.lottie_splash)
    LottieAnimationView lottieSplash;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        lottieSplash.setRepeatCount(2);
        lottieSplash.addAnimatorListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                MainActivity.startActivity(SplashActivity.this);
                finish();
            }
        });
    }

    @Override
    protected void initPresenter() {

    }


}
