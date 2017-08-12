package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.List;

public class SearchResultAdapter extends BaseAdapter<Repository, SearchResultViewHolder> {
    SearchResultListener listener;

    public interface SearchResultListener {
        void selected(int position);
    }

    public SearchResultAdapter(Context context, List<Repository> items, SearchResultListener listener) {
        super(context, items);
        this.listener = listener;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        SearchResultViewHolder viewHolder = new SearchResultViewHolder(context, view);
        view.setOnClickListener(it -> listener.selected(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }
}