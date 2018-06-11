package com.example.administrator.videoplayer.Http;

import com.example.administrator.videoplayer.Bean.MainUrlBean;
import com.example.administrator.videoplayer.Bean.VideoBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface RetrofitService {

    /*
    * http://is.snssdk.com/api/news/feed/v51/?category=video
    * */
    @GET("news/feed/v51/")
    Observable<VideoBean> getToday(@Query("category") String category);


    /*
    * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/v02004f00000bbpbk3l2v325q7lmkds0?r=6781281688452415&s=2734808831
    * */
    @GET
    Observable<MainUrlBean> getVideoMainUrl(@Url String url);

}
