package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryAdapter extends BaseAdapter<Repository,RepositoryViewHolder> {
    RepositoryClickedListener listener;

    public RepositoryAdapter(Context context, RepositoryClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);
        RepositoryViewHolder viewHolder =  new RepositoryViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    public interface RepositoryClickedListener{
        void clicked(int position);
    }

}
