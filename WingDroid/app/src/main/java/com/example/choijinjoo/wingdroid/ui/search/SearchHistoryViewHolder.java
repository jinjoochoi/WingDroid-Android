package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

public class SearchHistoryViewHolder extends BaseViewHolder<SearchHistory> {
    @BindView(R.id.txtvHistory) TextView txtvHistory;
    @BindView(R.id.imgvDelete) ImageView imgvDelete;

    public SearchHistoryViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(SearchHistory item) {
        txtvHistory.setText(item.getSearch());
    }
}