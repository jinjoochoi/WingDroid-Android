package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterViewHolder;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

public class CategorySearchAdapter extends BaseAdapter<Category, CategorySearchViewHolder> {

    CategoriesSelectedListener listener;

    public interface CategoriesSelectedListener {
        void selected(int position);
    }

    public CategorySearchAdapter(Context context, CategoriesSelectedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public CategorySearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_category, parent, false);
        CategorySearchViewHolder viewHolder = new CategorySearchViewHolder(context, view);
        view.setOnClickListener(it -> listener.selected(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategorySearchViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

}