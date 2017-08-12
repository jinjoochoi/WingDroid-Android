package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.BindView;

public class SearchResultViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.flowLayout) FlowLayout flowLayout;
    @BindView(R.id.txtvStar) TextView txtvStar;
    @BindView(R.id.txtvDescription) TextView txtvDescription;

    public SearchResultViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getGifs().get(0).getUrl())
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
        txtvDescription.setText(item.getDescription());

        for (Tag tag : item.getTags()) {
            TextView txtvTag = LayoutInflater.from(context).inflate(R.layout.item_tag, null, false).findViewById(R.id.txtvTag);
            txtvTag.setText(tag.toString());
            flowLayout.addView(txtvTag);
        }
    }
}