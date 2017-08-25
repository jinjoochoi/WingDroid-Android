package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.View;

import com.athkalia.emphasis.EmphasisTextView;
import com.athkalia.emphasis.HighlightMode;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public class SearchTagViewHolder extends BaseViewHolder<Tag> {
    @BindView(R.id.txtvTag)
    EmphasisTextView txtvTag;

    public SearchTagViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    public void bindData(Tag item, String search) {
        txtvTag.setText(item.toString());
        hightlightTextView(txtvTag, "#" + search);

    }

    @Override
    public void bindData(Tag item) {}

    private void hightlightTextView(EmphasisTextView textView, String search) {
        textView.setTextToHighlight(search);
        textView.setHighlightMode(HighlightMode.TEXT);
        textView.setTextHighlightColor(R.color.blue);
        textView.setCaseInsensitive(true);
        textView.highlight();
    }
}
