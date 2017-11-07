package com.project.dajver.psypractice;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.dajver.psypractice.ui.drawer.DrawerModel;
import com.project.dajver.psypractice.ui.drawer.adapter.DrawerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.dajver.psypractice.etc.Constants.DRAWER_BUSINESS;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_DEPENDENCY;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_OLD_AND_YONG;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_ON_RECEPTION;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_OTHER;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_PSYCHO_HEALTH;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_RELATION;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_TRAUMA;
import static com.project.dajver.psypractice.etc.Constants.DRAWER_WORK_AND_SOCIETY;

/**
 * Created by gleb on 11/7/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.mainView)
    LinearLayout mainView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    ListView listView;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);
        onCreateView(savedInstanceState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle = new ActionBarDrawerToggle(BaseActivity.this, drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(drawerView.getWidth() * slideOffset);
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };;
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setDrawerIndicatorEnabled(false);
        if(isDrawerBurgerVisible())
            drawerToggle.setHomeAsUpIndicator(R.mipmap.ic_nav_drawer);
        else
            drawerToggle.setHomeAsUpIndicator(R.mipmap.ic_drawer_back);

        List<DrawerModel> drawerModels = new ArrayList<>();
        for(String str : getResources().getStringArray(R.array.drawerItems))
            drawerModels.add(new DrawerModel(str));

        DrawerAdapter adapter = new DrawerAdapter(this, drawerModels);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case DRAWER_PSYCHO_HEALTH:

                        break;
                    case DRAWER_TRAUMA:

                        break;
                    case DRAWER_RELATION:

                        break;
                    case DRAWER_OLD_AND_YONG:

                        break;
                    case DRAWER_DEPENDENCY:

                        break;
                    case DRAWER_WORK_AND_SOCIETY:

                        break;
                    case DRAWER_BUSINESS:

                        break;
                    case DRAWER_OTHER:

                        break;
                    case DRAWER_ON_RECEPTION:

                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isDrawerBurgerVisible()) {
                    if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                } else {
                    finish();
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

    public abstract int getViewId();
    public abstract void onCreateView(Bundle savedInstanceState);
    public abstract boolean isDrawerBurgerVisible();
}
