package com.example.choijinjoo.wingdroid.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context context;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public abstract void bindData(T item);
}
