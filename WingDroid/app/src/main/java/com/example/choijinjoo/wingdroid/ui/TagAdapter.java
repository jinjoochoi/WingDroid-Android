package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public class TagAdapter extends BaseAdapter<Tag, TagViewHolder> {

    public TagAdapter(Context context, List<Tag> items) {
        super(context, items);
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TagViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_tag, parent,false));
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}
