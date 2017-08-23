package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview)
    ImageView imgvPreview;
    @BindView(R.id.txtvName)
    TextView txtvName;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.txtvStar)
    TextView txtvStar;
    @BindView(R.id.txtvDate)
    TextView txtvDate;
    @BindView(R.id.container)
    RelativeLayout container;
    RepositoryAdapter.RepositoryClickedListener listener;

    public RepositoryViewHolder(Context context, View itemView, RepositoryAdapter.RepositoryClickedListener listener) {
        super(context, itemView);
        this.listener = listener;
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_placeholder)
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
        txtvDate.setText(item.getCreatedAtDateFormattedString());
        flowLayout.removeAllViews();
        for (Tag tag : item.getTags()) {
            TextView txtvTag = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tag, null, false).findViewById(R.id.txtvTag);
            txtvTag.setText(tag.toString());
            flowLayout.addView(txtvTag);
        }
        container.setOnClickListener(it -> listener.clicked(getSafeAdapterPosition()));

    }
}
