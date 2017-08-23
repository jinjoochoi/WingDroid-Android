package com.example.choijinjoo.wingdroid.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
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

    public void add(List<T> added) {
        int position = getItemCount();
        for (T item : added) {
            items.add(item);
        }
        notifyItemRangeInserted(position, items.size());

    }

    public void remove(int position) {
        if (position < getItemCount()) {
            items.remove(position);
            notifyItemRemoved(position);
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

    public void change(int index, T item) {
        int count = items.size();
        if (index < count) {
            items.set(index, item);
            notifyItemChanged(index);
        }
    }

    public void setItems(List<T> items) {
        this.items = new ArrayList<T>(items);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
