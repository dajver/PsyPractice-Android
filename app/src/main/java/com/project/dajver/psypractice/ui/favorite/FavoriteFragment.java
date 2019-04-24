package com.project.dajver.psypractice.ui.favorite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.dajver.psypractice.App;
import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.RepositoryImpl;
import com.project.dajver.psypractice.etc.InternetCheckingHelper;
import com.project.dajver.psypractice.ui.favorite.adapter.FavoriteNewsRecyclerAdapter;
import com.project.dajver.psypractice.ui.favorite.db.DatabaseHelper;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.project.dajver.psypractice.ui.news.adapter.view.wraper.WrapperLinearLayout;
import com.project.dajver.psypractice.ui.news.details.NewsDetailsActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class FavoriteFragment extends BaseFragment implements FavoriteNewsRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyText)
    TextView emptyText;

    @Override
    public int getViewId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new WrapperLinearLayout(context, LinearLayoutManager.VERTICAL,false));

        if(InternetCheckingHelper.isHasInternet(context)) {
            new RepositoryImpl().getFavorites()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<FavoriteNewsModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(List<FavoriteNewsModel> favoriteNewsModels) {
                            if(favoriteNewsModels.size() > 0) recyclerView.setVisibility(View.VISIBLE);

                            FavoriteNewsRecyclerAdapter newsRecyclerAdapter = new FavoriteNewsRecyclerAdapter(context, favoriteNewsModels);
                            newsRecyclerAdapter.setOnItemClickListener(FavoriteFragment.this);
                            recyclerView.setAdapter(newsRecyclerAdapter);
                        }

                        @Override
                        public void onError(Throwable e) { }

                        @Override
                        public void onComplete() { }
                    });
        } else
            emptyText.setText(context.getString(R.string.toast_request_internet_fail));
    }

    @Override
    public void onItemClick(String detailsLink) {
        if(!detailsLink.startsWith("/")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.dialog_not_in_app_title))
                    .setMessage(getString(R.string.dialog_not_in_app_text))
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.dialog_not_in_app_no), (dialog, id) -> dialog.cancel())
                    .setPositiveButton(getString(R.string.dialog_not_in_app_yes), (dialog, id) -> {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailsLink));
                        startActivity(browserIntent);
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra(INTENT_LINK, detailsLink);
            startActivity(intent);
        }
    }

    @Override
    public void onDeleteFavorite(FavoriteNewsModel newsModel, int itemsCount) {
        if(itemsCount == 1) recyclerView.setVisibility(View.GONE);

        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        databaseHelper.getFavoriteDao().delete(newsModel);
    }
}
