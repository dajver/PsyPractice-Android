package com.project.dajver.psypractice.ui;

import android.os.Bundle;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;

public class NewsActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_news;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) { }

    @Override
    public boolean isDrawerBurgerVisible() {
        return true;
    }
}
