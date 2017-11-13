package com.project.dajver.psypractice.api.models;

/**
 * Created by gleb on 11/7/17.
 */

public class VideosModel {

    private String title;
    private String imageUrl;
    private String videoLink;

    public VideosModel() { }

    public VideosModel(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public VideosModel(String title, String imageUrl, String videoLink) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoLink = videoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoDetailsLink() {
        return videoLink;
    }

    public void setVideoDetailsLink(String articleLink) {
        this.videoLink = articleLink;
    }
}
