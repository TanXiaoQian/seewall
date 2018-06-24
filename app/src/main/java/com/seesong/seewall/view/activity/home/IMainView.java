package com.seesong.seewall.view.activity.home;

import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.view.IBaseView;

import java.util.List;

/**
 * Created by tanxiaoqian on 2018/6/18.
 */

public interface IMainView extends IBaseView{

    void onSuccess(List<Wallpaper> wallpapers);

    void onLoadMore(List<Wallpaper> wallpapers);

    void onCateChange(List<Wallpaper> wallpapers);

    void initBackground(List<Wallpaper> wallpapers);

    void onError(Throwable e);

}
