package com.project.dajver.psypractice.ui.favorite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.ui.favorite.db.model.FavoriteNewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class FavoriteNewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<FavoriteNewsModel> newsModels = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public FavoriteNewsRecyclerAdapter(Context context, List<FavoriteNewsModel> newsModels) {
        this.context = context;
        this.newsModels = newsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsViewHolder viewHolder = (NewsViewHolder) holder;
        viewHolder.title.setText(newsModels.get(position).getTitle());
        viewHolder.description.setText(newsModels.get(position).getDescription());
        viewHolder.viewsCount.setText(String.valueOf(newsModels.get(position).getViewsCount()));
        Picasso.with(context).load(newsModels.get(position).getImageUrl()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.description)
        public TextView description;
        @BindView(R.id.viewsCount)
        public TextView viewsCount;
        @BindView(R.id.image)
        public ImageView image;
        @BindView(R.id.remove)
        public ImageView remove;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(newsModels.get(getAdapterPosition()).getDetailsLink());
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onDeleteFavorite(newsModels.get(getAdapterPosition()), getItemCount());
                    newsModels.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String detailsLink);
        void onDeleteFavorite(FavoriteNewsModel newsModel, int itemsCount);
    }
}