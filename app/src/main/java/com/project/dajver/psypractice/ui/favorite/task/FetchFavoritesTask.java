package com.project.dajver.psypractice.ui.favorite.task;

import android.os.AsyncTask;

import com.project.dajver.psypractice.App;
import com.project.dajver.psypractice.db.DatabaseHelper;
import com.project.dajver.psypractice.db.model.FavoriteNewsModel;

import java.util.List;

/**
 * Created by gleb on 11/11/17.
 */

public class FetchFavoritesTask extends AsyncTask<String, Void, List<FavoriteNewsModel>> {

    private OnFetchFavoritesListener onFetchFavoritesListener;

    @Override
    protected List<FavoriteNewsModel> doInBackground(String... params) {
        DatabaseHelper databaseHelper = App.getInstance().getDatabaseInstance();
        return databaseHelper.getFavoriteDao().getAllFavorites();
    }

    @Override
    protected void onPostExecute(List<FavoriteNewsModel> favoriteNewsModels) {
        super.onPostExecute(favoriteNewsModels);
        onFetchFavoritesListener.onFetchFavorites(favoriteNewsModels);
    }

    public void setOnFetchFavoritesListener(OnFetchFavoritesListener onFetchFavoritesListener) {
        this.onFetchFavoritesListener = onFetchFavoritesListener;
    }

    public interface OnFetchFavoritesListener {
        void onFetchFavorites(List<FavoriteNewsModel> favoriteNewsModels);
    }
}
