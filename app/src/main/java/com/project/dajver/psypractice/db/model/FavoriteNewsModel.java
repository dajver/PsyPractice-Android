package com.project.dajver.psypractice.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.project.dajver.psypractice.ui.news.task.model.NewsModel;

/**
 * Created by gleb on 11/11/17.
 */

@Entity
public class FavoriteNewsModel {
    @NonNull
    @PrimaryKey
    String title;
    String description;
    String imageUrl;
    String detailsLink;
    int viewsCount;

    public FavoriteNewsModel() { }

    public FavoriteNewsModel(NewsModel favoriteNewsModel) {
        this.title = favoriteNewsModel.getTitle();
        this.description = favoriteNewsModel.getDescription();
        this.imageUrl = favoriteNewsModel.getImageUrl();
        this.detailsLink = favoriteNewsModel.getArticleDetailsLink();
        this.viewsCount = favoriteNewsModel.getViewsCount();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }
}
