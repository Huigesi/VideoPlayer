package com.example.administrator.videoplayer.Model;

import com.example.administrator.videoplayer.Bean.MainUrlBean;
import com.example.administrator.videoplayer.Bean.TodayContentBean;
import com.example.administrator.videoplayer.Bean.VideoBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface IOnLoadListener {
    void contentSuccess(VideoBean videoBean);
    void mainUrlSuccess(List<MainUrlBean> mainUrlBeans);
    void success(List<MainUrlBean> mainUrlBeans,List<TodayContentBean> contentBeans);
    void fail(String throwable);
}
