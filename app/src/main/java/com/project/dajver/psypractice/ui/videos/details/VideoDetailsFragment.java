package com.project.dajver.psypractice.ui.videos.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.RepositoryImpl;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/9/17.
 */

public class VideoDetailsFragment extends BaseFragment {

    @Override
    public int getViewId() {
        return R.layout.fragment_video_details;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        new RepositoryImpl().getVideoDetails(getActivity().getIntent().getExtras().getString(INTENT_LINK))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(String url) {
                        Uri uri = Uri.parse(url);
                        uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
                        startActivity(new Intent(Intent.ACTION_VIEW, uri ));
                        context.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, context.getString(R.string.toast_request_internet_fail), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() { }
                });
    }
}
