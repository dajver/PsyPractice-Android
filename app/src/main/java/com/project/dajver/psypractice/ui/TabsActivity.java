package com.project.dajver.psypractice.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.favorite.FavoriteFragment;
import com.project.dajver.psypractice.ui.news.NewsFragment;
import com.project.dajver.psypractice.ui.news.drawer.DrawerModel;
import com.project.dajver.psypractice.ui.news.drawer.adapter.DrawerAdapter;
import com.project.dajver.psypractice.ui.search.SearchFragment;
import com.project.dajver.psypractice.ui.videos.VideosFragment;
import com.project.dajver.psypractice.ui.view.BottomMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.DRAWER_BUSINESS;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_DEPENDENCY;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_OLD_AND_YONG;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_ON_RECEPTION;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_OTHER;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_PSYCHO_HEALTH;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_PUBLICATIONS;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_RELATION;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_TRAUMA;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_WORK_AND_SOCIETY;
import static com.project.dajver.psypractice.etc.Constants.LIST_LAST_PUBLICATIONS;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_BUSINESS;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_DEPENDENCY;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_OLD_AND_YONG;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_ON_RECEPTION;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_OTHER;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_PSYCHO_HEALTH;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_RELATION;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_TRAUMAS;
import static com.project.dajver.psypractice.etc.Constants.LIST_OF_WORK_AND_SOCIETY;
import static com.project.dajver.psypractice.etc.Constants.TAB_FAVORITE;
import static com.project.dajver.psypractice.etc.Constants.TAB_HOME;
import static com.project.dajver.psypractice.etc.Constants.TAB_SEARCH;
import static com.project.dajver.psypractice.etc.Constants.TAB_VIDEOS;

/**
 * Created by gleb on 11/9/17.
 */

public class TabsActivity extends BaseActivity implements BottomMenuView.OnTabClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    ListView listView;
    @BindView(R.id.bottom_navigation)
    BottomMenuView bottomNavigationView;

    private ActionBarDrawerToggle drawerToggle;

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
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.mipmap.ic_nav_drawer);

        final List<DrawerModel> drawerModels = new ArrayList<>();
        drawerModels.add(new DrawerModel(getString(R.string.news_title)));
        for(String str : getResources().getStringArray(R.array.drawerItems))
            drawerModels.add(new DrawerModel(str));

        DrawerAdapter adapter = new DrawerAdapter(this, drawerModels);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;
                switch (i) {
                    case DRAWER_PUBLICATIONS:
                        fragment = new NewsFragment().setInstance(LIST_LAST_PUBLICATIONS);
                        break;
                    case DRAWER_PSYCHO_HEALTH:
                        fragment = new NewsFragment().setInstance(LIST_OF_PSYCHO_HEALTH);
                        break;
                    case DRAWER_TRAUMA:
                        fragment = new NewsFragment().setInstance(LIST_OF_TRAUMAS);
                        break;
                    case DRAWER_RELATION:
                        fragment = new NewsFragment().setInstance(LIST_OF_RELATION);
                        break;
                    case DRAWER_OLD_AND_YONG:
                        fragment = new NewsFragment().setInstance(LIST_OF_OLD_AND_YONG);
                        break;
                    case DRAWER_DEPENDENCY:
                        fragment = new NewsFragment().setInstance(LIST_OF_DEPENDENCY);
                        break;
                    case DRAWER_WORK_AND_SOCIETY:
                        fragment = new NewsFragment().setInstance(LIST_OF_WORK_AND_SOCIETY);
                        break;
                    case DRAWER_BUSINESS:
                        fragment = new NewsFragment().setInstance(LIST_OF_BUSINESS);
                        break;
                    case DRAWER_OTHER:
                        fragment = new NewsFragment().setInstance(LIST_OF_OTHER);
                        break;
                    case DRAWER_ON_RECEPTION:
                        fragment = new NewsFragment().setInstance(LIST_OF_ON_RECEPTION);
                        break;
                }
                bottomNavigationView.setTabActive(TAB_HOME);
                getSupportActionBar().setTitle(drawerModels.get(i).getTitle());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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
