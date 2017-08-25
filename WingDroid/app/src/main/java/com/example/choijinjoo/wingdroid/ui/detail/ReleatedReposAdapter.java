package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

public class ReleatedReposAdapter extends BaseAdapter<Repository, RelatedReposViewHolder> {

    SimmilarsClickedListener listener;

    public interface SimmilarsClickedListener {
        void clicked(int position);
    }

    public ReleatedReposAdapter(Context context, SimmilarsClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public RelatedReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_related, parent, false);
        RelatedReposViewHolder viewHolder = new RelatedReposViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RelatedReposViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}