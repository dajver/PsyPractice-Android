package com.project.dajver.psypractice.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.dajver.psypractice.BaseFragment;
import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.news.adapter.view.EndlessRecyclerView;
import com.project.dajver.psypractice.ui.news.details.NewsDetailsActivity;
import com.project.dajver.psypractice.ui.search.adapter.SearchPreviewRecyclerAdapter;
import com.project.dajver.psypractice.ui.search.adapter.SearchRecyclerAdapter;
import com.project.dajver.psypractice.ui.search.task.FetchSearchTask;
import com.project.dajver.psypractice.ui.search.task.model.SearchModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.project.dajver.psypractice.etc.Constants.INTENT_LINK;
import static com.project.dajver.psypractice.etc.Constants.LINK_SEARCH_PAGE;
import static com.project.dajver.psypractice.etc.Constants.LIST_SEARCH_LINK;

/**
 * Created by gleb on 11/7/17.
 */

public class SearchFragment extends BaseFragment implements SearchView.OnQueryTextListener,
        FetchSearchTask.OnSearchEndedListener, EndlessRecyclerView.OnLoadMoreListener,
        SearchRecyclerAdapter.OnItemClickListener, SearchPreviewRecyclerAdapter.OnPreviewItemClickListener {

    @BindView(R.id.recyclerView)
    EndlessRecyclerView endlessRecyclerView;

    private SearchView searchView;
    private SearchRecyclerAdapter searchRecyclerAdapter;
    private String query;
    private int pageCounter = 1;

    @Override
    public int getViewId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreate(View view, Bundle savedInstanceState) {
        setPreviewAdapter();
    }

    private void setupAdapter() {
        searchRecyclerAdapter = new SearchRecyclerAdapter(context);
        searchRecyclerAdapter.setOnItemClickListener(this);
        endlessRecyclerView.setAdapter(searchRecyclerAdapter);
        endlessRecyclerView.setOnLoadMoreListener(this);
    }

    private void setPreviewAdapter() {
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.previewSearchItems));
        SearchPreviewRecyclerAdapter recyclerAdapter = new SearchPreviewRecyclerAdapter(context, list);
        recyclerAdapter.setOnPreviewItemClickListener(this);
        endlessRecyclerView.setAdapter(recyclerAdapter);
        endlessRecyclerView.setOnLoadMoreListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_view, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        setupAdapter();
        search(LIST_SEARCH_LINK, getQuery(query));
        return false;
    }

    private void search(String url, String query) {
        FetchSearchTask fetchSearchTask = new FetchSearchTask(context);
        fetchSearchTask.setOnSearchEndedListener(this);
        fetchSearchTask.execute(url + query);
    }

    private String getQuery(String query) {
        try {
            return URLEncoder.encode(query, "cp1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(TextUtils.isEmpty(newText) || newText.length() == 0) {
            setPreviewAdapter();
        }
        return false;
    }

    @Override
    public void onSearchFinished(ArrayList<SearchModel> searchModels) {
        if(searchModels != null) {
            for (SearchModel model : searchModels)
                searchRecyclerAdapter.addItem(model);
        } else {
            Toast.makeText(context, context.getString(R.string.toast_request_internet_fail), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoadMore() {
        pageCounter++;
        search(LIST_SEARCH_LINK, getQuery(query) + LINK_SEARCH_PAGE + pageCounter);
    }

    @Override
    public void onItemClick(String detailsLink) {
        if(!TextUtils.isEmpty(detailsLink)) {
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra(INTENT_LINK, detailsLink);
            startActivity(intent);
        }
    }

    @Override
    public void onPreviewItemClick(String title) {
        searchView.setQuery(title, true);
        searchView.setIconified(false);
    }
}
