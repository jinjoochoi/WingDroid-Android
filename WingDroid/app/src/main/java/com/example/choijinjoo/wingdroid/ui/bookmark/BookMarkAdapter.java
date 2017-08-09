package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.feed.RepositoryViewHolder;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkAdapter extends BaseAdapter<Repository,BookMarkViewHolder> {
    RepositoryClickedListener listener;

    public interface RepositoryClickedListener{
        void clicked(int position);
    }

    public BookMarkAdapter(Context context, RepositoryClickedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public BookMarkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, parent, false);
        BookMarkViewHolder viewHolder =  new BookMarkViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookMarkViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }
}
