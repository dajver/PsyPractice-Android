package com.project.dajver.psypractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_NEWS_LINK;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_NEWS_PAGE_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsFragment extends BaseFragment implements ObtainPageTask.OnDataObtainedListener,
        NewsRecyclerAdapter.OnItemClickListener, EndlessRecyclerView.OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private int pageCounter = 1;

    @Override
    public int getViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        newsRecyclerAdapter = new NewsRecyclerAdapter(context);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
        recyclerView.setOnLoadMoreListener(this);

        getNews(LIST_OF_NEWS_LINK);
    }

    private void getNews(String url) {
        ObtainPageTask obtainPageTask = new ObtainPageTask();
        obtainPageTask.setOnDataObtainedListener(this);
        obtainPageTask.execute(url);
    }

    @Override
    public void onDataObtained(ArrayList<NewsModel> newsModels) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

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
        getNews(LIST_OF_NEWS_PAGE_LINK + pageCounter);
    }
}
