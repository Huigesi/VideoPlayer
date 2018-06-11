package com.example.administrator.videoplayer.Model;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface IVideoModel {
    void loadVideo(String category, IOnLoadListener iOnLoadListener);
    void loadVideoContent(List<String> apiList, IOnLoadListener iOnLoadListener);

}
