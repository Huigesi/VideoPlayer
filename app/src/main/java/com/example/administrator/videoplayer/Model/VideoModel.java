package com.example.administrator.videoplayer.Model;

import android.util.Log;

import com.example.administrator.videoplayer.Bean.MainUrlBean;
import com.example.administrator.videoplayer.Bean.TodayContentBean;
import com.example.administrator.videoplayer.Bean.VideoBean;
import com.example.administrator.videoplayer.Http.Api;
import com.example.administrator.videoplayer.Http.RetrofitHelper;
import com.example.administrator.videoplayer.Presenter.VideoPresenter;

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
        final List<MainUrlBean> videoList = new ArrayList<>();
        final List<TodayContentBean> contentBeans = new ArrayList<>();
        final RetrofitHelper retrofitHelper = new RetrofitHelper(Api.TODAY_HOST);

        retrofitHelper.getToday(category)
                .flatMap(new Func1<VideoBean, Observable<MainUrlBean>>() {
                    @Override
                    public Observable<MainUrlBean> call(VideoBean videoBean) {
                        return Observable.from(videoBean.getData())
                                .flatMap(new Func1<VideoBean.DataBean, Observable<MainUrlBean>>() {
                                    @Override
                                    public Observable<MainUrlBean> call(VideoBean.DataBean dataBean) {
                                        String content = dataBean.getContent();
                                        TodayContentBean contentBean = VideoPresenter.getTodayContentBean(content);
                                        contentBeans.add(contentBean);
                                        String api = VideoPresenter.getVideoContentApi(contentBean.getVideo_id());
                                        return retrofitHelper.getMainUrl(api);
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MainUrlBean>() {
                    @Override
                    public void onCompleted() {
                        iOnLoadListener.success(videoList, contentBeans);
                        Log.i(TAG, "onCompleted: " + videoList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        iOnLoadListener.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(MainUrlBean mainUrlBean) {
                        videoList.add(mainUrlBean);
                    }
                });
        /*final RetrofitHelper retrofitHelper = new RetrofitHelper(Api.TODAY_HOST);
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
                });*/

    }
/*
* 另一种做法要用到的方法，这里注释掉了。
* */
    @Override
    public void loadVideoContent(final List<String> apiList, final IOnLoadListener iOnLoadListener) {
        /*final RetrofitHelper retrofitHelper= new RetrofitHelper(Api.TODAY_HOST);
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
                });*/
    }


}
