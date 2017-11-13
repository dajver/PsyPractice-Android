package com.project.dajver.psypractice.api;

import com.project.dajver.psypractice.api.models.NewsModel;
import com.project.dajver.psypractice.api.models.SearchModel;
import com.project.dajver.psypractice.api.models.VideosModel;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.project.dajver.psypractice.api.models.NewsDetailsModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by gleb on 11/13/17.
 */

public interface IRepository {

    Observable<ArrayList<NewsModel>> getLastNews(String url);
    Observable<ArrayList<SearchModel>> getSearchData(String url);
    Observable<ArrayList<VideosModel>> getVideos(String url);
    Observable<List<FavoriteNewsModel>> getFavorites();
    Observable<NewsDetailsModel> getNewsDetails(String url);
    Observable<String> getVideoDetails(String url);
}
