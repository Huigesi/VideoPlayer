package com.example.administrator.videoplayer.View;


import com.example.administrator.videoplayer.Bean.TodayContentBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface IVideoView {
    void showVideo(List<TodayContentBean> todayContentBeans, List<String> videoList);
    void hideDialog();
    void showDialog();
    void showErrorMsg(String throwable);
}
