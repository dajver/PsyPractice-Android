package com.project.dajver.psypractice.ui.news.details.task;

import android.os.AsyncTask;

import com.project.dajver.psypractice.ui.news.details.task.models.NewsDetailsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;

/**
 * Created by gleb on 11/7/17.
 */

public class ParseDetailsPageTask extends AsyncTask<String, Void, NewsDetailsModel> {

    private OnDetailsParsedListener onDetailsParsedListener;

    @Override
    protected NewsDetailsModel doInBackground(String... params) {
        NewsDetailsModel newsModels = null;
        Document doc;
        try {
            doc = Jsoup.connect(params[0]).get();

            Element h1 = doc.select("h1").first();
            Element titleElement = h1.getElementsByClass("uk-text-center").first();
            Element descriptionElement = doc.getElementById("newsdettext");
            Element img = doc.select("img").first();
            Element date = doc.getElementsByClass("avtoprdate uk-margin-top uk-text-center uk-margin-bottom").first();

            newsModels = new NewsDetailsModel();
            newsModels.setTitle(titleElement.text());
            newsModels.setDescription(descriptionElement.html());
            newsModels.setImageUrl(BASE_URL + img.attr("src"));
            newsModels.setDate(date.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsModels;
    }

    @Override
    protected void onPostExecute(NewsDetailsModel result) {
        super.onPostExecute(result);
        onDetailsParsedListener.onDetailsParsed(result);
    }

    public void setOnDetailsParsedListener(OnDetailsParsedListener onDetailsParsedListener) {
        this.onDetailsParsedListener = onDetailsParsedListener;
    }

    public interface OnDetailsParsedListener {
        void onDetailsParsed(NewsDetailsModel newsModels);
    }
}