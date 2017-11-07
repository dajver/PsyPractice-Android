package com.project.dajver.psypractice.ui.details;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.details.task.ParseDetailsPageTask;
import com.project.dajver.psypractice.ui.details.task.parser.URLImageParser;
import com.project.dajver.psypractice.ui.task.model.NewsModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsDetailsFragment extends BaseFragment implements ParseDetailsPageTask.OnDetailsParsedListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public int getViewId() {
        return R.layout.fragment_news_details;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        ParseDetailsPageTask obtainPageTask = new ParseDetailsPageTask();
        obtainPageTask.setOnDetailsParsedListener(this);
        obtainPageTask.execute(getActivity().getIntent().getExtras().getString(INTENT_LINK));
    }

    @Override
    public void onDetailsParsed(NewsModel newsModel) {
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        title.setText(newsModel.getTitle());

        URLImageParser p = new URLImageParser(description, context);
        Spanned htmlSpan = Html.fromHtml(newsModel.getDescription(), p, null);
        description.setText(htmlSpan);

        Picasso.with(context).load(newsModel.getImageUrl()).into(image);
    }
}
