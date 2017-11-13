package com.project.dajver.psypractice.ui.videos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.project.dajver.psypractice.api.models.VideosModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/7/17.
 */

public class VideosRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<VideosModel> newsModels = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public VideosRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(VideosModel newsModel) {
        newsModels.add(newsModel);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos, parent, false);
        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        VideosViewHolder viewHolder = (VideosViewHolder) holder;
        viewHolder.title.setText(newsModels.get(position).getTitle());
        Picasso.with(context).load(newsModels.get(position).getImageUrl()).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.image)
        public ImageView image;

        public VideosViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(newsModels.get(getAdapterPosition()).getVideoDetailsLink()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String detailsLink);
    }
}