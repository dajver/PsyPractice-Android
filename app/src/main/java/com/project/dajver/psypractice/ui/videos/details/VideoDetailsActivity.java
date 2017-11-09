package com.project.dajver.psypractice.ui.videos.details;

import android.os.Bundle;

import com.project.dajver.psypractice.BaseActivity;
import com.project.dajver.psypractice.R;

/**
 * Created by gleb on 11/9/17.
 */

public class VideoDetailsActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_video_details;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        getSupportActionBar().hide();
    }
}
