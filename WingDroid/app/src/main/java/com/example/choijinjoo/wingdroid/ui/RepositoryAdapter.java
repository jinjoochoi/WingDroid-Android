package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryAdapter extends BaseAdapter<Repository,RepositoryViewHolder> {
    public RepositoryAdapter(Context context, List items) {
        super(context, items);
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }
}
