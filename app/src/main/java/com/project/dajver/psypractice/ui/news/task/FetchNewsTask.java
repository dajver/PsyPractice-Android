package com.project.dajver.psypractice.ui.news.task;

import android.os.AsyncTask;
import android.util.Log;

import com.project.dajver.psypractice.ui.news.task.model.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;

/**
 * Created by gleb on 11/7/17.
 */

public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsModel>> {

    private OnDataObtainedListener onDataObtainedListener;
    private ArrayList<NewsModel> newsModels = new ArrayList<>();

    @Override
    protected ArrayList<NewsModel> doInBackground(String... params) {
        Document doc = null;
        try {
            Log.e("NEWS LINK", params[0]);
            doc = Jsoup.connect(params[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements titleElement = doc.getElementsByClass("newslesttitle");
        Elements descriptionElement = doc.getElementsByClass("ptext");
        Elements imageElement = doc.getElementsByClass("preview_picture");
        Elements linkElement = doc.getElementsByClass("uk-width-1-3");

        for(int i = 0; i < titleElement.size(); i++) {
            Elements ahref = imageElement.get(i).select("a[href]");
            String style = ahref.attr("style");

            Elements articleLink = linkElement.get(i).select("a");
            String url = articleLink.attr("href");

            String titleText = titleElement.get(i).text();
            String descriptionText = descriptionElement.get(i).text();
            String title = titleText.replaceAll(String.valueOf(getViewsCount(titleElement.get(i).text())), "");

            NewsModel model = new NewsModel(title,
                    descriptionText,
                    getImageLink(style),
                    url,
                    getViewsCount(titleText));
            newsModels.add(model);
        }
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
    protected void onPostExecute(ArrayList<NewsModel> result) {
        super.onPostExecute(result);
        onDataObtainedListener.onDataObtained(result);
    }

    public void setOnDataObtainedListener(OnDataObtainedListener onDataObtainedListener) {
        this.onDataObtainedListener = onDataObtainedListener;
    }

    public interface OnDataObtainedListener {
        void onDataObtained(ArrayList<NewsModel> newsModels);
    }
}