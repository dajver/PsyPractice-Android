package com.project.dajver.psypractice.api.models;

/**
 * Created by gleb on 11/7/17.
 */

public class SearchModel {

    private String title;
    private String description;
    private String articleLink;

    public SearchModel() { }

    public SearchModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public SearchModel(String title, String description, String articleLink) {
        this.title = title;
        this.description = description;
        this.articleLink = articleLink;
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

    public String getArticleDetailsLink() {
        return articleLink;
    }

    public void setArticleDetailsLink(String articleLink) {
        this.articleLink = articleLink;
    }
}
