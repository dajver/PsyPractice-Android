package com.project.dajver.psypractice.ui.news.details.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dajver.psypractice.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 11/12/17.
 */

public class DescriptionDetailsView extends LinearLayout {

    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.image)
    ImageView image;

    private Context context;

    public DescriptionDetailsView(Context context) {
        super(context);
        init(context);
    }

    public DescriptionDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DescriptionDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.view_description_details, this);
        ButterKnife.bind(this);
    }

    public void setDescription(String description) {
        this.description.setText(Html.fromHtml(description));
    }

    public void setImage(String imagePath) {
        if(!TextUtils.isEmpty(imagePath) && imagePath.startsWith("http"))
            Picasso.with(context).load(imagePath).into(image);
        else
            image.setVisibility(GONE);
    }
}
