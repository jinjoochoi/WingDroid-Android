package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public class TagViewHolder extends BaseViewHolder<Tag> {
    @BindView(R.id.txtvTag)
    TextView txtvTag;

    public TagViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Tag item) {
        txtvTag.setText(item.toString());
    }
}
