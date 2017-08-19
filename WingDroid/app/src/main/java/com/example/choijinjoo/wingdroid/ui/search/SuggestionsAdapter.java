package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

public class SuggestionsAdapter extends BaseAdapter<Repository, SuggestionsViewHolder> {

    SuggestionsClickedListener listener;

    public interface SuggestionsClickedListener {
        void clicked(int position);
    }

    public SuggestionsAdapter(Context context, SuggestionsClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public SuggestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false);
        SuggestionsViewHolder viewHolder = new SuggestionsViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SuggestionsViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}