package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseBottomSheetDialog;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 7..
 */

public class CategoryFilterDialog extends BaseBottomSheetDialog {
    @BindView(R.id.recvCategories)
    RecyclerView recvCategories;
    CategorySelectedListener listener;
    CategoryFilterAdapter adapter;
    List<Category> categories;
    Set<String> criteria;

    public interface CategorySelectedListener {
        void seleted(Set<String> criteria);
    }

    public static CategoryFilterDialog getInstance(Context context, List<Category> categories, CategorySelectedListener listener) {
        return new CategoryFilterDialog(context, listener, categories);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_repository_filter;
    }

    public CategoryFilterDialog(@NonNull Context context, CategorySelectedListener listener, List<Category> categories) {
        super(context);
        this.listener = listener;
        this.categories = categories;
        initLayout();

    }

    protected void initLayout() {
        setCancelable(true);
        adapter = new CategoryFilterAdapter(context, this::changeItemLayout);
        recvCategories.setAdapter(adapter);
        recvCategories.setLayoutManager(new FlowLayoutManager());
        adapter.setItems(categories);
        criteria = new HashSet<>();
    }

    private void changeItemLayout(int position) {
        if (adapter.getItem(position).getName().equals("All")) {
            if(!adapter.getItem(position).getSelected()) {
                for (Category category : categories) {
                    category.setSelected(false);
                }
                criteria.clear();
                criteria.add(adapter.getItem(position).getId());
                adapter.getItem(position).selected();
                adapter.notifyDataSetChanged();
            }
        } else {
            adapter.getItem(position).selected();
            if (criteria.contains("All")) {
                if(adapter.getItem(position).getSelected()) {
                    CategoryFilterAdapter.AdapterItemInfo item = adapter.getItemByName("All");
                    item.getCategory().setSelected(false);
                    adapter.notifyItemChanged(item.position);
                    criteria.remove(item.getCategory().getId());
                }else{
                    CategoryFilterAdapter.AdapterItemInfo item = adapter.getItemByName("All");
                    item.getCategory().setSelected(true);
                    for (Category category : categories) {
                        category.setSelected(false);
                    }
                    adapter.notifyDataSetChanged();
                    criteria.clear();
                    criteria.add(item.getCategory().getId());
                }
            }else {
                if (adapter.getItem(position).getSelected())
                    criteria.add(adapter.getItem(position).getId());
                else
                    criteria.remove(adapter.getItem(position).getId());
            }
            listener.seleted(criteria);
        }

    }

}
