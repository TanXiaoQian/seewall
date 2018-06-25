package com.seesong.seewall.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.pgyersdk.crash.PgyCrashManager;
import com.seesong.seewall.App;
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PgyCrashManager.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getAppRefWatcher(this).watch(this);
        PgyCrashManager.unregister();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }
}
