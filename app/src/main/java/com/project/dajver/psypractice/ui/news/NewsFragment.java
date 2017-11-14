package com.project.dajver.psypractice.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.project.dajver.psypractice.App;
import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.RepositoryImpl;
import com.project.dajver.psypractice.api.models.NewsModel;
import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.project.dajver.psypractice.ui.news.adapter.NewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.news.adapter.view.EndlessRecyclerView;
import com.project.dajver.psypractice.ui.news.details.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;
import static com.project.dajver.psypractice.etc.Constants.LINK_PAGE;
import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsFragment extends BaseFragment implements NewsRecyclerAdapter.OnItemClickListener,
        EndlessRecyclerView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView recyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private List<FavoriteNewsModel> favoriteNewsModels;
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

        swipeRefreshLayout.setOnRefreshListener(this);

        new RepositoryImpl().getFavorites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FavoriteNewsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(List<FavoriteNewsModel> favoriteNewsModels) {
                        NewsFragment.this.favoriteNewsModels = favoriteNewsModels;
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { }
                });

        getNews(linkToPage);
    }

    private void setupAdapter() {
        pageCounter = 1;
        newsRecyclerAdapter = new NewsRecyclerAdapter(context);
        newsRecyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsRecyclerAdapter);
        recyclerView.setOnLoadMoreListener(this);
    }

    private void getNews(String url) {
        swipeRefreshLayout.setRefreshing(true);

        new RepositoryImpl().getLastNews(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<NewsModel>>() {

                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(ArrayList<NewsModel> newsModels) {
                        swipeRefreshLayout.setRefreshing(false);

                        for (NewsModel newsModel : newsModels) {
                            for (FavoriteNewsModel model : favoriteNewsModels) {
                                if (model.getTitle().equals(newsModel.getTitle()))
                                    newsModel.setFavorite(true);
                            }
                            newsRecyclerAdapter.addItem(newsModel);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context, context.getString(R.string.toast_request_internet_fail), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    @Override
    public void onItemClick(String detailsLink) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(INTENT_LINK, detailsLink);
        startActivity(intent);
    }

    @Override
    public void onAddFavorite(NewsModel newsModel) {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.getFavoriteDao().insert(new FavoriteNewsModel(newsModel));
    }

    @Override
    public void onDeleteFavorite(NewsModel newsModel) {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.getFavoriteDao().delete(new FavoriteNewsModel(newsModel));
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
