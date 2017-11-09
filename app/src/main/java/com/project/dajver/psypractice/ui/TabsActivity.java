package com.project.dajver.psypractice.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.events.EventsFragment;
import com.project.dajver.psypractice.ui.news.NewsFragment;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;

/**
 * Created by gleb on 11/9/17.
 */

public class TabsActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigationView;

    @Override
    public int getViewId() {
        return R.layout.activity_tabs;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(getString(R.string.tabs_title_publications));

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.setIconSize(30, 30);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_publications));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new NewsFragment().setInstance(LIST_LAST_PUBLICATIONS))
                        .commit();
                break;
            case R.id.action_event:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_events));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new EventsFragment())
                        .commit();
                break;
            case R.id.action_specialist:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_specialists));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new EventsFragment())
                        .commit();
                break;
            case R.id.action_video:
                getSupportActionBar().setTitle(getString(R.string.tabs_title_videos));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new EventsFragment())
                        .commit();
                break;

        }
        return true;
    }
}
