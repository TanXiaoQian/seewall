package com.seesong.seewall;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;

/**
 * Created by tanxiaoqian on 2018/6/18.
 */

public class App extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyCrashManager.register(this);
    }



}
