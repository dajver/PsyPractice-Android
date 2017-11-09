package com.project.dajver.psypractice.ui.news.details;

import android.os.Bundle;
import android.view.MenuItem;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;

public class NewsDetailsActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
