package com.project.dajver.psypractice.ui.news.drawer;

/**
 * Created by gleb on 11/7/17.
 */

public class DrawerModel {

    private String title;
    private int icons;

    public DrawerModel(String title, int icons) {
        this.title = title;
        this.icons = icons;
    }

    public DrawerModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcons() {
        return icons;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }
}
