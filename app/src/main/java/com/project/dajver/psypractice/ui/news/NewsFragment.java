package com.project.dajver.psypractice.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.news.adapter.NewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.news.adapter.view.EndlessRecyclerView;
import com.project.dajver.psypractice.ui.news.details.NewsDetailsActivity;
import com.project.dajver.psypractice.ui.news.task.FetchNewsTask;
import com.project.dajver.psypractice.ui.news.task.model.NewsModel;

import java.util.ArrayList;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;
import static com.project.dajver.psypractice.etc.Constants.LINK_PAGE;
import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsFragment extends BaseFragment implements FetchNewsTask.OnDataObtainedListener,
        NewsRecyclerAdapter.OnItemClickListener, EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private String linkToPage = LIST_LAST_PUBLICATIONS;
    private int pageCounter = 1;

    public static NewsFragment setInstance(String link) {
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.linkToPage = link;
        return newsFragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        setupAdapter();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        getNews(linkToPage);
    }

    private void setupAdapter() {
        newsRecyclerAdapter = new NewsRecyclerAdapter(context);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
        recyclerView.setOnLoadMoreListener(this);
    }

    private void getNews(String url) {
        FetchNewsTask fetchNewsTask = new FetchNewsTask();
        fetchNewsTask.setOnDataObtainedListener(this);
        fetchNewsTask.execute(url);
    }

    @Override
    public void onDataObtained(ArrayList<NewsModel> newsModels) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);

        for(NewsModel model : newsModels)
            newsRecyclerAdapter.addItem(model);
    }

    @Override
    public void onItemClick(String detailsLink) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(INTENT_LINK, detailsLink);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        pageCounter++;
        getNews(linkToPage + LINK_PAGE + pageCounter);
    }

    @Override
    public void onRefresh() {
        setupAdapter();
        getNews(linkToPage);
    }
}
