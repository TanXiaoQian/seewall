package com.seesong.seewall.view.activity.detail;

import android.graphics.Bitmap;

import com.seesong.seewall.view.IBaseView;

/**
 * Created by tanxiaoqian on 2018/6/22.
 */

public interface IDetailView extends IBaseView {

    void onSetWallpaper(Bitmap bitmap);

    void onShare(String url);

    void onDownload();

}
