package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;
import com.example.choijinjoo.wingdroid.ui.base.LoadingViewHolder;

import java.util.ArrayList;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryAdapter extends BaseAdapter<Repository, RecyclerView.ViewHolder> {
    RepositoryClickedListener listener;
    private boolean isLoadingAdded = false;

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    public RepositoryAdapter(Context context, RepositoryClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM:
                View view = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);
                viewHolder = new RepositoryViewHolder(context, view, listener);
                break;
            case LOADING:
                View v2 = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM)
            ((RepositoryViewHolder) holder).bindData(items.get(position));
    }

    public interface RepositoryClickedListener {
        void clicked(int position);
    }


    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public Repository getLastItem() {
        return getItem(getItemCount() - 1);
    }
}
