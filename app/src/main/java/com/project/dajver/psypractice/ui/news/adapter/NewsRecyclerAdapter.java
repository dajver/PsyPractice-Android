package com.project.dajver.psypractice.ui.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<NewsModel> newsModels = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public NewsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(NewsModel newsModel) {
        newsModels.add(newsModel);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NewsViewHolder viewHolder = (NewsViewHolder) holder;
        viewHolder.title.setText(newsModels.get(position).getTitle());
        viewHolder.description.setText(newsModels.get(position).getDescription());
        viewHolder.viewsCount.setText(String.valueOf(newsModels.get(position).getViewsCount()));
        Picasso.with(context).load(newsModels.get(position).getImageUrl()).into(viewHolder.image);
        if(newsModels.get(position).isFavorite())
            viewHolder.favorite.setImageResource(R.mipmap.ic_favorite_blue_selected);
        else
            viewHolder.favorite.setImageResource(R.mipmap.ic_favorite_gray);
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
        @BindView(R.id.favorite)
        public ImageView favorite;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(newsModels.get(getAdapterPosition()).getArticleDetailsLink()));
            favorite.setOnClickListener(view -> {
                if(newsModels.get(getAdapterPosition()).isFavorite()) {
                    newsModels.get(getAdapterPosition()).setFavorite(false);
                    favorite.setImageResource(R.mipmap.ic_favorite_gray);
                    onItemClickListener.onDeleteFavorite(newsModels.get(getAdapterPosition()));
                } else {
                    newsModels.get(getAdapterPosition()).setFavorite(true);
                    favorite.setImageResource(R.mipmap.ic_favorite_blue_selected);
                    onItemClickListener.onAddFavorite(newsModels.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String detailsLink);
        void onAddFavorite(NewsModel newsModel);
        void onDeleteFavorite(NewsModel newsModel);
    }
}