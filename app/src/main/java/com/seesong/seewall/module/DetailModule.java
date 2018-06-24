package com.seesong.seewall.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tanxiaoqian on 2018/6/24.
 */

public class DetailModule {


    private RxAppCompatActivity mCompatActivity;

    public DetailModule(Context appCompatActivity) {
        mCompatActivity = (RxAppCompatActivity) appCompatActivity;
    }

    public void download(Observer<Bitmap> observer, final String url) {

        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter observableEmitter) throws Exception {

                InputStream inputStream = null;
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .get()
                            .url(url)
                            .build();

                    Call call = client.newCall(request);

                    Response response = call.execute();

                    inputStream = response.body().byteStream();
                    Bitmap bitmap =
                            BitmapFactory.decodeStream(inputStream);

                    observableEmitter.onNext(bitmap);
                    Log.e("TAG", "下载成功");
                } catch (IOException e) {
                    observableEmitter.onError(e);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }


            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(observer);

    }


}
