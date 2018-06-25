package com.seesong.seewall;

import android.app.Application;
import android.content.Context;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.seesong.seewall.view.ActivityHelper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tanxiaoqian on 2018/6/18.
 */

public class App extends Application {


    private RefWatcher mAppRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mAppRefWatcher = LeakCanary.install(this);
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyCrashManager.register(this);
        ActivityHelper activityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(activityHelper);
    }

    private void post(){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();

        Request request = new Request.Builder()
                .url("www.pixabay.com")
                .post(formBody.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public static RefWatcher getAppRefWatcher(Context context) {
        App applicationContext = (App) context.getApplicationContext();
        return applicationContext.mAppRefWatcher;
    }
}
