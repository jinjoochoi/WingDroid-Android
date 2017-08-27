package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.TagAdapter;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.recvTag) RecyclerView recvTag;
    @BindView(R.id.txtvStar) TextView txtvStar;
    @BindView(R.id.txtvDescription) TextView txtvDescription;
    @BindView(R.id.txtvCategory) TextView txtvCategory;
    @BindView(R.id.txtvDate) TextView txtvDate;

    public BookMarkViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
        txtvCategory.setText(item.getCategory().getName());
        txtvDescription.setText(item.getDescription().length() > 60 ? item.getDescription().substring(0, 59) + "..." : item.getDescription());
        txtvDate.setText(item.getCreatedAtDateFormattedString());

        recvTag.setAdapter(new TagAdapter(context,item.getTags()));
        recvTag.setLayoutManager(new FlowLayoutManager());

    }
}
