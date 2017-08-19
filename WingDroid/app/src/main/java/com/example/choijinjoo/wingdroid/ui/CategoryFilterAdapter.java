package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

public class CategoryFilterAdapter extends BaseAdapter<Category, CategoryFilterViewHolder> {

    CategoriesSelectedListener listener;

    public interface CategoriesSelectedListener {
        void selected(int position);
    }

    public CategoryFilterAdapter(Context context, CategoriesSelectedListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public CategoryFilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_category, parent, false);
        CategoryFilterViewHolder viewHolder = new CategoryFilterViewHolder(context, view);
        view.setOnClickListener(it -> listener.selected(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryFilterViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    public AdapterItemInfo getItemByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name))
                return new AdapterItemInfo(items.get(i),i);
            return null;
        }
        return null;
    }


    class AdapterItemInfo {
        Category category;
        int position;

        public AdapterItemInfo(Category category, int position) {
            this.category = category;
            this.position = position;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}