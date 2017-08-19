package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseBottomSheetDialog;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

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

    public interface CategorySelectedListener {
        void seleted(List<Category> criteria);
    }

    public static CategoryFilterDialog getInstance(Context context, List<Category> categories,  CategorySelectedListener listener) {
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
    }

    private void changeItemLayout(int position){
        adapter.getItem(position).selected();
        adapter.notifyItemChanged(position);
        listener.seleted(adapter.getItems());
    }

}
