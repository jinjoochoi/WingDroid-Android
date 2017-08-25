package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class NewViewHolder extends BaseViewHolder<Repository> {
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.txtvStar) TextView txtvStar;
    @BindView(R.id.imgvRead) View imgvRead;

    public NewViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Repository item) {
        Glide.with(context)
                .load(item.getImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL )
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
        if(item.getClicks() > 0){
            imgvRead.setVisibility(View.INVISIBLE);
        }else{
            imgvRead.setVisibility(View.VISIBLE);
        }
    }
}
