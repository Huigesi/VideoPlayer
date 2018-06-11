package com.example.administrator.videoplayer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.videoplayer.Bean.MainUrlBean;
import com.example.administrator.videoplayer.Bean.TodayContentBean;
import com.example.administrator.videoplayer.Bean.VideoBean;
import com.example.administrator.videoplayer.Http.Api;
import com.example.administrator.videoplayer.Http.RetrofitHelper;
import com.example.administrator.videoplayer.Presenter.VideoPresenter;
import com.example.administrator.videoplayer.View.IVideoView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements IVideoView {

    private static final String TAG = "MainActivity";
    private VideoPresenter videoPresenter;
    private RecyclerView rv_video;
    private ItemVideoAdapter itemVideoAdapter;
    private SwipeRefreshLayout srl_video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        videoPresenter.loadVideo();
        srl_video.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                videoPresenter.loadVideo();
            }
        });
    }

    private void initView() {
        rv_video = findViewById(R.id.rv_video);
        srl_video = findViewById(R.id.srl_video);
        itemVideoAdapter = new ItemVideoAdapter(this);
        videoPresenter = new VideoPresenter(this);
    }


    @Override
    public void showVideo(List<TodayContentBean> todayContentBeans, List<String> videoList) {
        Log.i(TAG, "showVideo: "+videoList.size());
        itemVideoAdapter.setData(todayContentBeans,videoList);
        rv_video.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv_video.setAdapter(itemVideoAdapter);
    }

    @Override
    public void hideDialog() {
        srl_video.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_video.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(String throwable) {
        Toast.makeText(this, throwable, Toast.LENGTH_SHORT).show();

    }
}
