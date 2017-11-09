package com.project.dajver.psypractice.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.dajver.psypractice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.project.dajver.psypractice.etc.Constants.TAB_FAVORITE;
import static com.project.dajver.psypractice.etc.Constants.TAB_HOME;
import static com.project.dajver.psypractice.etc.Constants.TAB_SEARCH;
import static com.project.dajver.psypractice.etc.Constants.TAB_VIDEOS;

/**
 * Created by gleb on 11/9/17.
 */

public class BottomMenuView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.home)
    ImageView home;
    @BindView(R.id.videos)
    ImageView videos;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.favorite)
    ImageView favorite;

    private OnTabClickListener onTabClickListener;

    public BottomMenuView(Context context) {
        super(context);
        init(context);
    }

    public BottomMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_bottom_menu, this);
        ButterKnife.bind(this);
        setupViews();
    }

    private void setupViews() {
        home.setOnClickListener(this);
        videos.setOnClickListener(this);
        search.setOnClickListener(this);
        favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                onTabClickListener.onTabClickListener(TAB_HOME);
                setTabActive(TAB_HOME);
                break;
            case R.id.videos:
                onTabClickListener.onTabClickListener(TAB_VIDEOS);
                setTabActive(TAB_VIDEOS);
                break;
            case R.id.search:
                onTabClickListener.onTabClickListener(TAB_SEARCH);
                setTabActive(TAB_SEARCH);
                break;
            case R.id.favorite:
                onTabClickListener.onTabClickListener(TAB_FAVORITE);
                setTabActive(TAB_FAVORITE);
                break;
        }
    }

    private void setTabActive(int tabId) {
        switch (tabId) {
            case TAB_HOME:
                home.setImageResource(R.mipmap.ic_home_blue);
                videos.setImageResource(R.mipmap.ic_videos_gray);
                search.setImageResource(R.mipmap.ic_search_gray);
                favorite.setImageResource(R.mipmap.ic_favorite_gray);
                break;
            case TAB_VIDEOS:
                home.setImageResource(R.mipmap.ic_home_gray);
                videos.setImageResource(R.mipmap.ic_videos_blue);
                search.setImageResource(R.mipmap.ic_search_gray);
                favorite.setImageResource(R.mipmap.ic_favorite_gray);
                break;
            case TAB_SEARCH:
                home.setImageResource(R.mipmap.ic_home_gray);
                videos.setImageResource(R.mipmap.ic_videos_gray);
                search.setImageResource(R.mipmap.ic_search_blue);
                favorite.setImageResource(R.mipmap.ic_favorite_gray);
                break;
            case TAB_FAVORITE:
                home.setImageResource(R.mipmap.ic_home_gray);
                videos.setImageResource(R.mipmap.ic_videos_gray);
                search.setImageResource(R.mipmap.ic_search_gray);
                favorite.setImageResource(R.mipmap.ic_favorite_blue);
                break;
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public interface OnTabClickListener {
        void onTabClickListener(int id);
    }
}
