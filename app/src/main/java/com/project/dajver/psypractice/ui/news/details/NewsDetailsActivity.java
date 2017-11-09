package com.project.dajver.psypractice.ui.news.details;

import android.os.Bundle;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;

public class NewsDetailsActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) { }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean isDrawerBurgerVisible() {
        return false;
    }
}
