package com.seesong.seewall.presenter;

import android.app.Activity;
import android.graphics.Bitmap;

import com.seesong.seewall.module.DetailModule;
import com.seesong.seewall.view.activity.detail.IDetailView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class DetailPresenter extends BasePresenter<IDetailView> {


    private DetailModule mDetailModule;


    public DetailPresenter(Activity activity, IDetailView mView) {
        super(activity, mView);
        mDetailModule = new DetailModule(activity);
    }


    public void setWallpaper(String url) {

        mDetailModule.download(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                mView.onSetWallpaper(bitmap);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, url);

    }



    public void download(String url) {
        mDetailModule.download(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Bitmap bitmap) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, url);
    }

}
