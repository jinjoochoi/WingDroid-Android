package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;
import com.nex3z.flowlayout.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.flowLayout) FlowLayout flowLayout;
    @BindView(R.id.txtvStar) TextView txtvStar;


    public RepositoryViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getGifs().get(0).getUrl())
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());

        for (Tag tag : item.getTags()) {
            TextView txtvTag = LayoutInflater.from(context).inflate(R.layout.item_tag, null, false).findViewById(R.id.txtvTag);
            txtvTag.setText(tag.toString());
            flowLayout.addView(txtvTag);
        }

    }
}
