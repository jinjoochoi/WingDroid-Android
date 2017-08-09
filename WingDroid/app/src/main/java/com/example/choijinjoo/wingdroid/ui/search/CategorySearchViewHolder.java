package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

public class CategorySearchViewHolder extends BaseViewHolder<Category> {
    @BindView(R.id.txtvCategory) TextView txtvCategory;
    @BindView(R.id.btnCategory) RelativeLayout btnCategory;

    public CategorySearchViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Category item) {
        txtvCategory.setText(item.getName());
        btnCategory.setSelected(item.getSelected());
        txtvCategory.setSelected(item.getSelected());
    }
}