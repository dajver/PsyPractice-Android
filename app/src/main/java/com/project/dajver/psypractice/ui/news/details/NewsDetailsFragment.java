package com.project.dajver.psypractice.ui.news.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.news.details.models.DescriptionTextModel;
import com.project.dajver.psypractice.ui.news.details.task.ParseDetailsPageTask;
import com.project.dajver.psypractice.ui.news.details.task.models.NewsDetailsModel;
import com.project.dajver.psypractice.ui.news.details.views.DescriptionDetailsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;
import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsDetailsFragment extends BaseFragment implements ParseDetailsPageTask.OnDetailsParsedListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.descriptionView)
    LinearLayout descriptionView;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private NewsDetailsModel newsModel;
    private String linkToArticle;

    @Override
    public int getViewId() {
        return R.layout.fragment_news_details;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        linkToArticle = BASE_URL + getActivity().getIntent().getExtras().getString(INTENT_LINK);
        ParseDetailsPageTask obtainPageTask = new ParseDetailsPageTask();
        obtainPageTask.setOnDetailsParsedListener(this);
        obtainPageTask.execute(linkToArticle);
    }

    @Override
    public void onDetailsParsed(NewsDetailsModel newsModel) {
        this.newsModel = newsModel;
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        if(newsModel != null) {
            title.setText(newsModel.getTitle());
            date.setText(newsModel.getDate());
            ArrayList<DescriptionTextModel> textModels = getDescription(newsModel.getDescription());
            for (int i = 0; i < textModels.size(); i++) {
                DescriptionDetailsView detailsView = new DescriptionDetailsView(context);
                detailsView.setDescription(textModels.get(i).getText());
                detailsView.setImage(textModels.get(i).getImage());
                descriptionView.addView(detailsView);
            }

            Picasso.with(context).load(newsModel.getImageUrl()).into(image);
        } else
            Toast.makeText(context, context.getString(R.string.toast_request_internet_fail), Toast.LENGTH_LONG).show();
    }

    private ArrayList<DescriptionTextModel> getDescription(String htmlString) {
        String imageRegex = "src\\s*=\\s*['\"]([^'\"]+)['\"]";
        String textRegex = "(<(/)img>)|(<img.+?>)";

        Pattern p = Pattern.compile(imageRegex);
        String[] descriptionText = htmlString.split(textRegex);
        Matcher m = p.matcher(htmlString);

        ArrayList<DescriptionTextModel> textModels = new ArrayList<>();
        for(int i = 0; i < descriptionText.length; i++) {
            DescriptionTextModel textModel = new DescriptionTextModel();
            textModel.setText(descriptionText[i]);
            if(m.find())
                textModel.setImage(m.group(1));
            textModels.add(textModel);
        }
        return textModels;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, newsModel.getTitle());
                sendIntent.putExtra(Intent.EXTRA_TEXT, linkToArticle);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.openBrowser:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkToArticle));
                startActivity(browserIntent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
