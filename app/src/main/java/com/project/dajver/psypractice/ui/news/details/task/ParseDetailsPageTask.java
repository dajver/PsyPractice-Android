package com.project.dajver.psypractice.ui.news.details.task;

import android.os.AsyncTask;

import com.project.dajver.psypractice.ui.news.task.model.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;

/**
 * Created by gleb on 11/7/17.
 */

public class ParseDetailsPageTask extends AsyncTask<String, Void, NewsModel> {

    private OnDetailsParsedListener onDetailsParsedListener;

    @Override
    protected NewsModel doInBackground(String... params) {
        Document doc = null;
        try {
            doc = Jsoup.connect(BASE_URL + params[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element h1 = doc.select("h1").first();
        Element titleElement = h1.getElementsByClass("uk-text-center").first();
        Element descriptionElement = doc.getElementById("newsdettext");
        Element img = doc.select("img").first();

        NewsModel newsModels = new NewsModel();
        newsModels.setTitle(titleElement.text());
        newsModels.setDescription(descriptionElement.html());
        newsModels.setImageUrl(BASE_URL + img.attr("src"));
        return newsModels;
    }

    private String getImageLink(String str) {
        Pattern pattern = Pattern.compile("url\\('(.+?)'\\)");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        return BASE_URL + matcher.group(1);
    }

    private int getViewsCount(String input) {
        int lastNumberInt = 0;
        Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");
        Matcher matcher = lastIntPattern.matcher(input);
        if (matcher.find()) {
            String someNumberStr = matcher.group(1);
            lastNumberInt = Integer.parseInt(someNumberStr);
        }
        return lastNumberInt;
    }

    @Override
    protected void onPostExecute(NewsModel result) {
        super.onPostExecute(result);
        onDetailsParsedListener.onDetailsParsed(result);
    }

    public void setOnDetailsParsedListener(OnDetailsParsedListener onDetailsParsedListener) {
        this.onDetailsParsedListener = onDetailsParsedListener;
    }

    public interface OnDetailsParsedListener {
        void onDetailsParsed(NewsModel newsModels);
    }
}