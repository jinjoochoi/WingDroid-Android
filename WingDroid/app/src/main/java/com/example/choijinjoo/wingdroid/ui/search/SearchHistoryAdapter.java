package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.SearchHistory;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class SearchHistoryAdapter extends RealmRecyclerViewAdapter<SearchHistory, SearchHistoryViewHolder> {

    SearchHistoryListener listener;
    Context context;

    public interface SearchHistoryListener {
        void selected(int position);
        void delete(int position);
    }

    public SearchHistoryAdapter(@Nullable OrderedRealmCollection data, boolean autoUpdate, SearchHistoryListener listener, Context context) {
        super(data, autoUpdate);
        this.listener = listener;
        this.context = context;
    }

    public SearchHistoryAdapter(@Nullable OrderedRealmCollection data, boolean autoUpdate, boolean updateOnModification, SearchHistoryListener listener, Context context) {
        super(data, autoUpdate, updateOnModification);
        this.listener = listener;
        this.context = context;
    }

    @Override
    public SearchHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        SearchHistoryViewHolder viewHolder = new SearchHistoryViewHolder(context, view);
//        viewHolder.imgvDelete.setOnClickListener(it -> listener.delete(viewHolder.getSafeAdapterPosition()));
//        viewHolder.txtvHistory.setOnClickListener(it -> listener.selected(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchHistoryViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }
}