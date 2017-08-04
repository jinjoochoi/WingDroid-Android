package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview)
    ImageView imgvPreview;
    @BindView(R.id.txtvName)
    TextView txtvName;

    public RepositoryViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getGifs().get(0).getUrl())
                .into(imgvPreview);
        txtvName.setText(item.getName());
    }
}
