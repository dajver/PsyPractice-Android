package com.project.dajver.psypractice.ui.adapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.project.dajver.psypractice.ui.adapter.view.wraper.WrapperLinearLayout;

/**
 * Created by gleb on 11/7/17.
 */

public class EndlessRecyclerView extends RecyclerView {

    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private WrapperLinearLayout mLayoutManager;
    private Context mContext;
    private OnLoadMoreListener onLoadMoreListener;

    public EndlessRecyclerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EndlessRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EndlessRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        mLayoutManager = new WrapperLinearLayout(mContext, LinearLayoutManager.VERTICAL,false);
        this.setLayoutManager(mLayoutManager);
        this.setItemAnimator(new DefaultItemAnimator());
        this.setHasFixedSize(true);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if(dy > 0) {
            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                Log.e("...", "Call Load More !");
                if(onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}