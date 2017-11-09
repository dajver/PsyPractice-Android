package com.project.dajver.psypractice.ui;

import android.os.Bundle;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.favorite.FavoriteFragment;
import com.project.dajver.psypractice.ui.search.SearchFragment;
import com.project.dajver.psypractice.ui.news.NewsFragment;
import com.project.dajver.psypractice.ui.videos.VideosFragment;
import com.project.dajver.psypractice.ui.view.BottomMenuView;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;
import static com.project.dajver.psypractice.etc.Constants.TAB_FAVORITE;
import static com.project.dajver.psypractice.etc.Constants.TAB_HOME;
import static com.project.dajver.psypractice.etc.Constants.TAB_SEARCH;
import static com.project.dajver.psypractice.etc.Constants.TAB_VIDEOS;

/**
 * Created by gleb on 11/9/17.
 */

public class TabsActivity extends BaseActivity implements BottomMenuView.OnTabClickListener {

    @BindView(R.id.bottom_navigation)
    BottomMenuView bottomNavigationView;

    @Override
    public int getViewId() {
        return R.layout.activity_tabs;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.tabs_title_publications));

        bottomNavigationView.setOnTabClickListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new NewsFragment())
                .commit();
    }

    @Override
    public boolean isDrawerBurgerVisible() {
        return true;
    }

    @Override
    public void onTabClickListener(int id) {
        switch (id) {
            case TAB_HOME:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_publications));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new NewsFragment().setInstance(LIST_LAST_PUBLICATIONS))
                        .commit();
                break;
            case TAB_VIDEOS:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_videos));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new VideosFragment())
                        .commit();
                break;
            case TAB_SEARCH:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_search));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SearchFragment())
                        .commit();
                break;
            case TAB_FAVORITE:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_favorite));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new FavoriteFragment())
                        .commit();
                break;

        }
    }
}
