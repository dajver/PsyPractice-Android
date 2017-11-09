package com.project.dajver.psypractice.ui.videos.details.task;

import android.os.AsyncTask;
import android.util.Log;

import com.project.dajver.psypractice.ui.news.task.model.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gleb on 11/7/17.
 */

public class FetchVideoLinkTask extends AsyncTask<String, Void, String> {

    private OnVideoLinkTakenListener onVideoLinkTakenListener;
    private ArrayList<NewsModel> newsModels = new ArrayList<>();

    @Override
    protected String doInBackground(String... params) {
        Document doc = null;
        try {
            Log.e("VIDEO LINK", params[0]);
            doc = Jsoup.connect(params[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element linkElement = doc.getElementsByClass("news-detail").first();
        Element articleLink = linkElement.select("iframe").first();
        String url = articleLink.attr("src");

        return url;
    }

    @Override
    protected void onPostExecute(String link) {
        super.onPostExecute(link);
        String youtube = "https://www.youtube.com/watch?v=";
        onVideoLinkTakenListener.onVideoTakenListener(youtube + extractYoutubeVideoId(link));
    }

    public String extractYoutubeVideoId(String ytUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        if(matcher.find()){
            videoId= matcher.group();
        }
        return videoId;
    }

    public void setOnVideoLinkTakenListener(OnVideoLinkTakenListener onVideoLinkTakenListener) {
        this.onVideoLinkTakenListener = onVideoLinkTakenListener;
    }

    public interface OnVideoLinkTakenListener {
        void onVideoTakenListener(String url);
    }
}