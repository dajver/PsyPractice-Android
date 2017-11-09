package com.project.dajver.psypractice.ui.videos.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.videos.details.task.FetchVideoLinkTask;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/9/17.
 */

public class VideoDetailsFragment extends BaseFragment implements FetchVideoLinkTask.OnVideoLinkTakenListener {

    @Override
    public int getViewId() {
        return R.layout.fragment_video_details;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        FetchVideoLinkTask fetchVideoLinkTask = new FetchVideoLinkTask();
        fetchVideoLinkTask.setOnVideoLinkTakenListener(this);
        fetchVideoLinkTask.execute(getActivity().getIntent().getExtras().getString(INTENT_LINK));
    }

    @Override
    public void onVideoTakenListener(String url) {
        Uri uri = Uri.parse(url);
        uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
        startActivity(new Intent(Intent.ACTION_VIEW, uri ));
        context.finish();
    }
}
