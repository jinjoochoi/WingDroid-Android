package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;
import com.example.choijinjoo.wingdroid.ui.search.SearchResultViewHolder;
import com.example.choijinjoo.wingdroid.ui.search.SearchTagViewHolder;

import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public class SearchTagAdapter extends BaseAdapter<Tag, SearchTagViewHolder> {

    String search;

    public SearchTagAdapter(Context context, List<Tag> items, String search) {
        super(context, items);
        this.search = search;
    }

    @Override
    public SearchTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchTagViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_tag, parent,false));
    }

    @Override
    public void onBindViewHolder(SearchTagViewHolder holder, int position) {
        holder.bindData(items.get(position),search);
    }

    public void setSearch(String search){
        this.search = search;
    }

}
