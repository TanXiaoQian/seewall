package com.seesong.seewall.presenter;

import android.app.Activity;

import com.seesong.seewall.view.IBaseView;

/**
 * Created by tanxiaoqian on 2018/6/20.
 */

public class BasePresenter<GV extends IBaseView> {

    protected GV mView;
    protected Activity mContext;

    public BasePresenter(Activity activity, GV mView) {
        this.mView = mView;
        this.mContext = activity;
    }


}
