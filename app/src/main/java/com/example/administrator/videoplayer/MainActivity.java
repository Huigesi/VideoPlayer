package com.example.administrator.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.videoplayer.Bean.TodayContentBean;
import com.example.administrator.videoplayer.Presenter.IVideoPresenter;
import com.example.administrator.videoplayer.Presenter.VideoPresenter;
import com.example.administrator.videoplayer.View.IVideoView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IVideoView {
    private IVideoPresenter iVideoPresenter;
    private RecyclerView rv_video;
    private ItemVideoAdapter itemVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_video);
        iVideoPresenter = new VideoPresenter(this);
        rv_video = findViewById(R.id.rv_video);
        iVideoPresenter.loadVideo();
        itemVideoAdapter = new ItemVideoAdapter(this);
    }

    @Override
    public void showVideo(List<TodayContentBean> todayContentBeans, List<String> videoList) {

        itemVideoAdapter.setData(todayContentBeans,videoList);
        rv_video.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv_video.setAdapter(itemVideoAdapter);
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(String throwable) {

    }
}
