package com.seesong.seewall.module;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.seesong.seewall.model.Wallpaper;
import com.seesong.seewall.module.param.WallRequestParam;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tanxiaoqian on 2018/6/20.
 */

public class WallPagerModule {

//    https://pixabay.com/zh/editors_choice/
//    https://pixabay.com
//    https://pixabay.com/zh/photos/?order=latest&orientation=horizontal
//    https://pixabay.com/zh/photos/?orientation=horizontal
//https://pixabay.com/zh/photos/
// ?q=美女
// &hp=
// &image_type=all
// &order=popular
// &cat=
// &orientation=horizontal
// &min_width=&min_height=


    //    https://pixabay.com/zh/photos/
// ?min_height=
// &orientation=horizontal
// &image_type=all
// &cat=
// &q=美女
// &min_width=
// &order=popular
// &pagi=2
    private RxAppCompatActivity mCompatActivity;

    public WallPagerModule(Context appCompatActivity) {
        mCompatActivity = (RxAppCompatActivity) appCompatActivity;
    }

    public void loadWallPager(Observer<List<Wallpaper>> observer, final WallRequestParam param) {


        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter observableEmitter) throws Exception {

                try {
                    Document document = Jsoup.connect(getUrl(param))
                            //如果网站采用了策略禁止此类访问，需模仿浏览器行为
                            //.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                            .get();

                    Elements flex_grid = document.getElementsByClass("flex_grid");
                    String src;
                    if (!flex_grid.isEmpty()) {

                        List<Wallpaper> wallpapers = new ArrayList<>();
                        Wallpaper wallpaper = null;

                        Elements items = flex_grid.get(0).getElementsByClass("item");

                        for (Element element : items) {

                            Elements img = element.getElementsByTag("img");

                            if (!img.isEmpty()) {
                                src = img.get(0).attr("src");

                                if (src.startsWith("https")) {
                                    wallpaper = new Wallpaper();
                                    wallpaper.setSrc(src);
                                    Elements ems = element.getElementsByTag("em");
                                    Elements as = element.getElementsByTag("a");

                                    if (ems.size() >= 3) {
                                        wallpaper.setLickCount(ems.get(0).text());
                                        wallpaper.setFavoriteCount(ems.get(1).text());
                                        wallpaper.setCommentCount(ems.get(2).text());
                                    }
                                    if (as.size() > 1) {
                                        wallpaper.setUser(as.get(1).text());
                                    }
                                    wallpapers.add(wallpaper);
                                }

                            }

                        }

                        observableEmitter.onNext(wallpapers);

                    }


                } catch (IOException e1) {
                    observableEmitter.onError(e1);
                } finally {
                    observableEmitter.onComplete();
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mCompatActivity.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(observer);

    }


    private String getUrl(WallRequestParam param) {
        String url = "https://pixabay.com/zh/photos/";

        if (param != null && param.getParamEnumMap() != null) {

            Set<Map.Entry<WallRequestParam.Builder.Param, String>> entries =
                    param.getParamEnumMap().entrySet();

            StringBuilder sb = new StringBuilder(url);

            Iterator<Map.Entry<WallRequestParam.Builder.Param, String>> iterator = entries.iterator();

            sb.append("?");

            while (iterator.hasNext()) {
                Map.Entry<WallRequestParam.Builder.Param, String> next = iterator.next();

                if (!TextUtils.isEmpty(next.getValue()))
                    sb.append(next.getKey().getParam()).append("=").append(next.getValue()).append("&");
            }
            int i = sb.lastIndexOf("&");
            if (i == sb.length() - 1) {
                sb.deleteCharAt(i);
            }
            url = sb.toString();
        }

        return url;
    }

}
