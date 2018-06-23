package com.seesong.seewall.presenter;

import android.app.Activity;

import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.module.WallPagerModule;
import com.seesong.seewall.module.param.WallRequestParam;
import com.seesong.seewall.view.activity.home.IMainView;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class HomePresenter extends BasePresenter<IMainView> {


    private WallPagerModule mWallPagerModule;
    private WallRequestParam mWallRequestParam;

    public HomePresenter(Activity context, IMainView mView) {
        super(context, mView);
        mWallPagerModule = new WallPagerModule(context);
        mWallRequestParam = getRequestParam();
    }


    public void loadData() {
        mView.showLoading();
        mWallPagerModule.loadWallPager(new DisposableObserver<List<Wallpaper>>() {
            @Override
            public void onNext(List<Wallpaper> wallpapers) {
                mView.dismissLoading();
                mView.onSuccess(wallpapers);
                mView.initBackground(wallpapers);
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                mView.onError(e);
            }

            @Override
            public void onComplete() {
                //TODO 请求结束
                mView.dismissLoading();
            }
        }, mWallRequestParam);
    }

    public void loadMore() {
        getRequestParam().increasePage();

        mWallPagerModule.loadWallPager(new DisposableObserver<List<Wallpaper>>() {
            @Override
            public void onNext(List<Wallpaper> wallpapers) {
                mView.onLoadMore(wallpapers);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        }, mWallRequestParam);
    }


    public void changeCate(String q) {
        getRequestParam().setQ(q);
        mWallPagerModule.loadWallPager(new DisposableObserver<List<Wallpaper>>() {
            @Override
            public void onNext(List<Wallpaper> wallpapers) {
                mView.onCateChange(wallpapers);
                mView.initBackground(wallpapers);
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, mWallRequestParam);
    }


    private WallRequestParam getRequestParam() {
        if (mWallRequestParam == null) {
            mWallRequestParam = new WallRequestParam.Builder().build();
        }
        return mWallRequestParam;
    }

}
