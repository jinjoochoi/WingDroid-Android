package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

public class SearchHistoryAdapter extends BaseAdapter<SearchHistory, SearchHistoryViewHolder> {

    SearchHistoryListener listener;

    public interface SearchHistoryListener {
        void selected(int position);
        void delete(int position);
    }

    public SearchHistoryAdapter(Context context,  SearchHistoryListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public SearchHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        SearchHistoryViewHolder viewHolder = new SearchHistoryViewHolder(context, view);
        viewHolder.imgvDelete.setOnClickListener(it -> listener.delete(viewHolder.getSafeAdapterPosition()));
        viewHolder.txtvHistory.setOnClickListener(it -> listener.selected(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchHistoryViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }
}