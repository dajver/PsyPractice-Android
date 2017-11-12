package com.project.dajver.psypractice.ui.news.details.task.models;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsDetailsModel {

    private String title;
    private String description;
    private String imageUrl;
    private String date;

    public NewsDetailsModel() { }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
