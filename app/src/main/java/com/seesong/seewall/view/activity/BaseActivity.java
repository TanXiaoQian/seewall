package com.seesong.seewall.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pgyersdk.crash.PgyCrashManager;
import com.seesong.seewall.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tanxiaoqian on 2018/6/19.
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {

    protected Unbinder mUnbinder;


    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PgyCrashManager.register(this);
        transparentStatusBar();
        bindView();
        initView();
        initPresenter();
    }

    protected void bindView() {
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);
    }

    protected abstract @LayoutRes
    int getContentViewId();


    protected abstract void initView();

    protected abstract void initPresenter();

    /**
     * 检测系统版本并使状态栏全透明
     */
    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 新增滑动返回，舍弃过渡动效

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    private void setTranslucentStatus(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            attributes.flags |= bits;
        } else {
            attributes.flags &= ~bits;
        }
        window.setAttributes(attributes);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PgyCrashManager.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyCrashManager.unregister();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
