package com.project.dajver.psypractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.adapter.NewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.details.NewsDetailsActivity;
import com.project.dajver.psypractice.ui.task.ObtainPageTask;
import com.project.dajver.psypractice.ui.task.model.NewsModel;

import java.util.ArrayList;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsFragment extends BaseFragment implements ObtainPageTask.OnDataObtainedListener,
        NewsRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public int getViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        ObtainPageTask obtainPageTask = new ObtainPageTask();
        obtainPageTask.setOnDataObtainedListener(this);
        obtainPageTask.execute();

        recycleViewSetup(recyclerView);
    }

    @Override
    public void onDataObtained(ArrayList<NewsModel> newsModels) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        NewsRecyclerAdapter newsRecyclerAdapter = new NewsRecyclerAdapter(context, newsModels);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
    }

    @Override
    public void onItemClick(String detailsLink) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(INTENT_LINK, detailsLink);
        startActivity(intent);
    }
}
