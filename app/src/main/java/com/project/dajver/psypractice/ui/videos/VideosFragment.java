package com.project.dajver.psypractice.ui.videos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.RepositoryImpl;
import com.project.dajver.psypractice.ui.news.adapter.view.EndlessRecyclerView;
import com.project.dajver.psypractice.ui.videos.adapter.VideosRecyclerAdapter;
import com.project.dajver.psypractice.ui.videos.details.VideoDetailsActivity;
import com.project.dajver.psypractice.api.models.VideosModel;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;
import static com.project.dajver.psypractice.etc.Constants.LINK_PAGE;
import static com.project.dajver.psypractice.etc.Constants.LIST_VIDEOS_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class VideosFragment extends BaseFragment implements VideosRecyclerAdapter.OnItemClickListener,
        EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private VideosRecyclerAdapter videosRecyclerAdapter;
    private int pageCounter = 1;

    @Override
    public int getViewId() {
        return R.layout.fragment_videos;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        setupAdapter();

        swipeRefreshLayout.setOnRefreshListener(this);

        getVideos(LIST_VIDEOS_LINK);
    }

    private void setupAdapter() {
        pageCounter = 1;
        videosRecyclerAdapter = new VideosRecyclerAdapter(context);
        videosRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setOnLoadMoreListener(this);
        recyclerView.setAdapter(videosRecyclerAdapter);
    }

    private void getVideos(String link) {
        swipeRefreshLayout.setRefreshing(true);

        new RepositoryImpl().getVideos(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<VideosModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(ArrayList<VideosModel> videosModels) {
                        swipeRefreshLayout.setRefreshing(false);

                        for (VideosModel model : videosModels)
                            videosRecyclerAdapter.addItem(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, context.getString(R.string.toast_request_internet_fail), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    @Override
    public void onItemClick(String detailsLink) {
        Intent intent = new Intent(context, VideoDetailsActivity.class);
        intent.putExtra(INTENT_LINK, detailsLink);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        pageCounter++;
        getVideos(LIST_VIDEOS_LINK + LINK_PAGE + pageCounter);
    }

    @Override
    public void onRefresh() {
        setupAdapter();
        getVideos(LIST_VIDEOS_LINK);
    }
}
