package com.project.dajver.psypractice.ui.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.dajver.psypractice.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class SearchPreviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<String> stringList = new ArrayList<>();
    private OnPreviewItemClickListener onPreviewItemClickListener;
    private Context context;

    public SearchPreviewRecyclerAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_preview, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsViewHolder viewHolder = (NewsViewHolder) holder;
        viewHolder.title.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void setOnPreviewItemClickListener(OnPreviewItemClickListener onPreviewItemClickListener) {
        this.onPreviewItemClickListener = onPreviewItemClickListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView title;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPreviewItemClickListener.onPreviewItemClick(stringList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnPreviewItemClickListener {
        void onPreviewItemClick(String title);
    }
}