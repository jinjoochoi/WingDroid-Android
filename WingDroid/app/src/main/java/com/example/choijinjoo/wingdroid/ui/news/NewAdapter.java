package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class NewAdapter extends BaseAdapter<Repository, NewViewHolder> {
    NewClickListener listener;

    public interface NewClickListener {
        void clicked(int position);
    }

    public NewAdapter(Context context, NewClickListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    public NewAdapter(Context context, List items, NewClickListener listener) {
        super(context, items);
        this.listener = listener;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new, parent, false);
        NewViewHolder viewHolder = new NewViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}
