package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

public class CategoryFilterViewHolder extends BaseViewHolder<Category> {
    @BindView(R.id.txtvCategory) TextView txtvCategory;
    @BindView(R.id.btnCategory) LinearLayout btnCategory;

    public CategoryFilterViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void bindData(Category item) {
        txtvCategory.setText(item.getName());
        btnCategory.setSelected(item.getSelected());
        txtvCategory.setSelected(item.getSelected());
    }
}