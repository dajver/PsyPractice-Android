package com.project.dajver.psypractice.ui.news.task.model;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsModel {

    private String title;
    private String description;
    private String imageUrl;
    private String articleLink;
    private int viewsCount;
    private boolean isFavorite;

    public NewsModel() { }

    public NewsModel(String title, String description, String imageUrl, String articleLink, int viewsCount) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.articleLink = articleLink;
        this.viewsCount = viewsCount;
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

    public String getArticleDetailsLink() {
        return articleLink;
    }

    public void setArticleDetailsLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
