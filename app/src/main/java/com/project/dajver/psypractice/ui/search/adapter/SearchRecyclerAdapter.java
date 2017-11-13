package com.project.dajver.psypractice.ui.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.models.SearchModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<SearchModel> newsModels = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public SearchRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SearchModel newsModel) {
        newsModels.add(newsModel);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(newsModels.get(position).getTitle());
        viewHolder.description.setText(newsModels.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.description)
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(newsModels.get(getAdapterPosition()).getArticleDetailsLink());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String detailsLink);
    }
}