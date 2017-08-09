package com.example.choijinjoo.wingdroid.ui.detail;

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
import com.nex3z.flowlayout.FlowLayout;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class SimmilarsViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.txtvStar) TextView txtvStar;


    public SimmilarsViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getGifs().get(0).getUrl())
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
    }
}
