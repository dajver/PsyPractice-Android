package com.project.dajver.psypractice.ui.search.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.search.task.model.SearchModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.project.dajver.psypractice.etc.Constants.LINK_PUBLICATIONS;

/**
 * Created by gleb on 11/7/17.
 */

public class FetchSearchTask extends AsyncTask<String, Void, ArrayList<SearchModel>> {

    private Context context;
    private OnSearchEndedListener onSearchEndedListener;
    private ArrayList<SearchModel> searchModels = new ArrayList<>();

    public FetchSearchTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<SearchModel> doInBackground(String... params) {
        Document doc = null;
        try {
            Log.e("SEARCH LINK", params[0]);
            doc = Jsoup.connect(params[0]).get();

            Elements titleElement = doc.getElementsByClass("class 234");
            Elements descriptionElement = doc.select("td").select("p");
            Elements linkElement = doc.getElementsByClass("class 234");

            for(int i = 0; i < titleElement.size(); i++) {
                Elements articleLink = linkElement.get(i).select("a");
                String url = articleLink.attr("href");

                String titleText = titleElement.get(i).text();
                String descriptionText = descriptionElement.get(i).text();

                if(url.contains(LINK_PUBLICATIONS)) {
                    SearchModel model = new SearchModel(titleText,
                            descriptionText,
                            url);
                    searchModels.add(model);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            searchModels = null;
        }
        return searchModels;
    }

    @Override
    protected void onPostExecute(ArrayList<SearchModel> result) {
        super.onPostExecute(result);
        if(result.size() > 0)
            onSearchEndedListener.onSearchFinished(result);
        else {
            SearchModel model = new SearchModel(context.getString(R.string.search_fail_empty), "", "");
            result.add(model);

            onSearchEndedListener.onSearchFinished(result);
        }
    }

    public void setOnSearchEndedListener(OnSearchEndedListener onSearchEndedListener) {
        this.onSearchEndedListener = onSearchEndedListener;
    }

    public interface OnSearchEndedListener {
        void onSearchFinished(ArrayList<SearchModel> searchModels);
    }
}