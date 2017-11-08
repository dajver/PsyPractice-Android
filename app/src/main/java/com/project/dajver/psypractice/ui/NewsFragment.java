package com.project.dajver.psypractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.adapter.NewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.adapter.view.EndlessRecyclerView;
import com.project.dajver.psypractice.ui.details.NewsDetailsActivity;
import com.project.dajver.psypractice.ui.task.ObtainPageTask;
import com.project.dajver.psypractice.ui.task.model.NewsModel;

import java.util.ArrayList;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;
import static com.project.dajver.psypractice.etc.Constants.INTENT_TITLE;
import static com.project.dajver.psypractice.etc.Constants.LINK_PAGE;
import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsFragment extends BaseFragment implements ObtainPageTask.OnDataObtainedListener,
        NewsRecyclerAdapter.OnItemClickListener, EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private String linkToPage;
    private int pageCounter = 1;

    @Override
    public int getViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        setupAdapter();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        Intent intent = context.getIntent();
        linkToPage = intent.hasExtra(INTENT_LINK) ? intent.getExtras().getString(INTENT_LINK) : LIST_LAST_PUBLICATIONS;
        ((NewsActivity) context).setActionBarTitle(intent.hasExtra(INTENT_TITLE) ? intent.getExtras().getString(INTENT_TITLE) : getString(R.string.news_title));

        getNews(linkToPage);
    }

    private void setupAdapter() {
        newsRecyclerAdapter = new NewsRecyclerAdapter(context);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
        recyclerView.setOnLoadMoreListener(this);
    }

    private void getNews(String url) {
        ObtainPageTask obtainPageTask = new ObtainPageTask();
        obtainPageTask.setOnDataObtainedListener(this);
        obtainPageTask.execute(url);
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
