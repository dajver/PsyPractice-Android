package com.project.dajver.psypractice.ui.videos.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.project.dajver.psypractice.ui.videos.task.model.VideosModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.project.dajver.psypractice.etc.Constants.BASE_URL;

/**
 * Created by gleb on 11/7/17.
 */

public class FetchVideosTask extends AsyncTask<String, Void, ArrayList<VideosModel>> {

    private OnVideosFetchedListener onVideosFetchedListener;
    private ArrayList<VideosModel> videosModels = new ArrayList<>();
    private Context context;

    public FetchVideosTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<VideosModel> doInBackground(String... params) {
        Document doc = null;
        try {
            Log.e("VIDEOS LINK", params[0]);
            doc = Jsoup.connect(params[0]).get();

            Elements mainElement = doc.getElementsByClass("newslesttitle-tab uk-h4");
            Elements imageElement = doc.getElementsByClass("uk-panel-box");

            for(int i = 0; i < mainElement.size(); i++) {
                String titleText = mainElement.get(i).text();

                Elements videoLink = mainElement.get(i).select("a");
                String url = videoLink.attr("href");

                Elements imageLink = imageElement.get(i).select("img");
                String imageUrl = imageLink.attr("src");

                if(url.contains("useful")) {
                    VideosModel model = new VideosModel(titleText, imageUrl, BASE_URL + url);
                    videosModels.add(model);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            videosModels = null;
        }
        return videosModels;
    }

    @Override
    protected void onPostExecute(ArrayList<VideosModel> result) {
        super.onPostExecute(result);
        onVideosFetchedListener.onVideosFetched(result);
    }

    public void setOnVideosFetchedListener(OnVideosFetchedListener OnVideosFetchedListener) {
        this.onVideosFetchedListener = OnVideosFetchedListener;
    }

    public interface OnVideosFetchedListener {
        void onVideosFetched(ArrayList<VideosModel> videosModels);
    }
}