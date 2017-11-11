package com.project.dajver.psypractice.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.dajver.psypractice.App;
import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.project.dajver.psypractice.ui.favorite.adapter.FavoriteNewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.favorite.task.FetchFavoritesTask;
import com.project.dajver.psypractice.ui.news.adapter.view.wraper.WrapperLinearLayout;
import com.project.dajver.psypractice.ui.news.details.NewsDetailsActivity;

import java.util.List;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class FavoriteFragment extends BaseFragment implements FetchFavoritesTask.OnFetchFavoritesListener,
        FavoriteNewsRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getViewId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new WrapperLinearLayout(context, LinearLayoutManager.VERTICAL,false));

        FetchFavoritesTask fetchFavoritesTask = new FetchFavoritesTask();
        fetchFavoritesTask.setOnFetchFavoritesListener(this);
        fetchFavoritesTask.execute();
    }

    @Override
    public void onFetchFavorites(List<FavoriteNewsModel> favoriteNewsModels) {
        if(favoriteNewsModels.size() > 0) recyclerView.setVisibility(View.VISIBLE);

        FavoriteNewsRecyclerAdapter newsRecyclerAdapter = new FavoriteNewsRecyclerAdapter(context, favoriteNewsModels);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
    }

    @Override
    public void onItemClick(String detailsLink) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(INTENT_LINK, detailsLink);
        startActivity(intent);
    }

    @Override
    public void onDeleteFavorite(FavoriteNewsModel newsModel, int itemsCount) {
        if(itemsCount == 1) recyclerView.setVisibility(View.GONE);

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.getFavoriteDao().delete(newsModel);
    }
}
