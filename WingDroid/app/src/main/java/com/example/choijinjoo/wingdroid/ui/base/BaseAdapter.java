package com.example.choijinjoo.wingdroid.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;
    protected Context context;

    public BaseAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    public void add(T item) {
        int position = getItemCount();
        items.add(item);
        notifyItemInserted(position);
    }

    public void add(T item, int index) {
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void add(List<T> items) {
        int position = getItemCount();
        for (T item : items) {
            items.add(item);
        }
        notifyItemRangeInserted(position, items.size());
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            items.remove(position);
            notifyDataSetChanged();
        }
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        remove(position);
    }

    public void clear() {
        int count = items.size();
        if (count > 0) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
