package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;
import com.example.choijinjoo.wingdroid.ui.search.SuggestionsViewHolder;

import java.util.ArrayList;

public class SimmilarsAdapter extends BaseAdapter<Repository, SimmilarsViewHolder> {

    SimmilarsClickedListener listener;

    public interface SimmilarsClickedListener {
        void clicked(int position);
    }

    public SimmilarsAdapter(Context context, SimmilarsClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public SimmilarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simmilars, parent, false);
        SimmilarsViewHolder viewHolder = new SimmilarsViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimmilarsViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}