package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.athkalia.emphasis.EmphasisTextView;
import com.athkalia.emphasis.HighlightMode;
import com.bumptech.glide.Glide;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;

import org.apmem.tools.layouts.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgvPreview)
    ImageView imgvPreview;
    @BindView(R.id.txtvName)
    EmphasisTextView txtvName;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.txtvStar)
    TextView txtvStar;
    @BindView(R.id.txtvDescription)
    EmphasisTextView txtvDescription;
    @BindView(R.id.txtvCategory)
    EmphasisTextView txtvCategory;
    Context context;

    public SearchResultViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Repository item, String search) {
        Glide.with(context)
                .load(item.getImage())
                .into(imgvPreview);
        txtvName.setText(item.getName());
        txtvStar.setText(item.getFormattedStarString());
        txtvCategory.setText(item.getCategory().getName());
        txtvDescription.setText(item.getDescription().length() > 30 ? item.getDescription().substring(0, 29) + "..." : item.getDescription());

        flowLayout.removeAllViews();
        for (Tag tag : item.getTags()) {
            EmphasisTextView txtvTag = (EmphasisTextView) LayoutInflater.from(context).inflate(R.layout.item_tag, null, false).findViewById(R.id.txtvTag);
            txtvTag.setText(tag.toString());
            hightlightTextView(txtvTag,"#"+search);
            flowLayout.addView(txtvTag);
        }

        hightlightTextView(txtvName, search);
        hightlightTextView(txtvDescription, search);
        hightlightTextView(txtvCategory, search);
    }

    private void hightlightTextView(EmphasisTextView textView, String search) {
        textView.setTextToHighlight(search);
        textView.setHighlightMode(HighlightMode.TEXT);
        textView.setTextHighlightColor(R.color.blue);
        textView.setCaseInsensitive(true);
        textView.highlight();
    }

    public int getSafeAdapterPosition() {
        return getAdapterPosition() == RecyclerView.NO_POSITION ? 0 : getAdapterPosition();
    }
}