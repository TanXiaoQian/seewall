package com.seesong.seewall.presenter;

import android.app.Activity;

import com.seesong.seewall.module.DetailModule;
import com.seesong.seewall.view.activity.detail.IDetailView;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public class DetailPresenter extends BasePresenter<IDetailView> {


    private DetailModule mDetailModule;

    public DetailPresenter(Activity activity, IDetailView mView) {
        super(activity, mView);
        mDetailModule = new DetailModule();
    }


    public void download(String url) {
        mDetailModule.download(url);
    }

}
