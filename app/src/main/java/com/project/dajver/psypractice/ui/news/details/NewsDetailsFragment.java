package com.project.dajver.psypractice.ui.news.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.news.details.models.DescriptionTextModel;
import com.project.dajver.psypractice.ui.news.details.task.ParseDetailsPageTask;
import com.project.dajver.psypractice.ui.news.details.views.DescriptionDetailsView;
import com.project.dajver.psypractice.ui.news.task.model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

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

        ArrayList<DescriptionTextModel> textModels = getDescription(newsModel.getDescription());
        for(int i = 0; i < textModels.size(); i++) {
            DescriptionDetailsView detailsView = new DescriptionDetailsView(context);
            detailsView.setDescription(textModels.get(i).getText());
            detailsView.setImage(textModels.get(i).getImage());
            descriptionView.addView(detailsView);
        }

        Picasso.with(context).load(newsModel.getImageUrl()).into(image);
    }

    private ArrayList<DescriptionTextModel> getDescription(String htmlString) {
        String regex = "src\\s*=\\s*['\"]([^'\"]+)['\"]";
        Pattern p = Pattern.compile(regex);
        String[] descriptionText = htmlString.split("(<(/)img>)|(<img.+?>)");
        Matcher m = p.matcher(htmlString);

        ArrayList<DescriptionTextModel> textModels = new ArrayList<>();
        for(int i = 0; i < descriptionText.length; i++) {
            DescriptionTextModel textModel = new DescriptionTextModel();
            textModel.setText(descriptionText[i]);
            if(m.find()) {
                textModel.setImage(m.group(1));
            }
            textModels.add(textModel);
        }
        return textModels;
    }
}
