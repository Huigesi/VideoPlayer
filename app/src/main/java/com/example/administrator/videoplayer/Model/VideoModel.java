package com.example.administrator.videoplayer.Model;

import android.util.Log;

import com.example.administrator.videoplayer.Bean.MainUrlBean;
import com.example.administrator.videoplayer.Bean.VideoBean;
import com.example.administrator.videoplayer.Http.Api;
import com.example.administrator.videoplayer.Http.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/19.
 */

public class VideoModel implements IVideoModel {
    private String TAG="VideoModel";
    @Override
    public void loadVideo(String category, final IOnLoadListener iOnLoadListener) {
        final RetrofitHelper retrofitHelper = new RetrofitHelper(Api.TODAY_HOST);
        retrofitHelper.getToday(category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<VideoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        iOnLoadListener.contentSuccess(videoBean);
                    }
                });

    }

    @Override
    public void loadVideoContent(final List<String> apiList, final IOnLoadListener iOnLoadListener) {
        final RetrofitHelper retrofitHelper= new RetrofitHelper(Api.TODAY_HOST);
        final List<MainUrlBean> mainUrlBeans = new ArrayList<>();
        Observable.from(apiList)
                .flatMap(new Func1<String, Observable<MainUrlBean>>() {
                    @Override
                    public Observable<MainUrlBean> call(String s) {
                        Log.i(TAG, "call: "+s);
                        return retrofitHelper.getMainUrl(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MainUrlBean>() {
                    @Override
                    public void onCompleted() {
                        iOnLoadListener.mainUrlSuccess(mainUrlBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iOnLoadListener.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(MainUrlBean mainUrlBean) {
                        mainUrlBeans.add(mainUrlBean);
                    }
                });
    }


}
