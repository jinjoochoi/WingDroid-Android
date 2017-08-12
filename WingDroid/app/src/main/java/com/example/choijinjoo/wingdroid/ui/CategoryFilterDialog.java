package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseBottomSheetDialog;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 7..
 */

public class CategoryFilterDialog extends BaseBottomSheetDialog {
    @BindView(R.id.recvCategories)
    RecyclerView recvCategories;
    CategorySelectedListener listener;
    CategoryFilterAdapter adapter;

    public interface CategorySelectedListener {
        void seleted(Category criteria);
    }

    public static CategoryFilterDialog getInstance(Context context, CategorySelectedListener listener) {
        return new CategoryFilterDialog(context, listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_repository_filter;
    }

    public CategoryFilterDialog(@NonNull Context context, CategorySelectedListener listener) {
        super(context);
        this.listener = listener;

    }

    @Override
    protected void initLayout() {
        setCancelable(true);
        adapter = new CategoryFilterAdapter(context, position ->{
            adapter.getItem(position).selected();
            adapter.notifyItemChanged(position);
        });
        recvCategories.setAdapter(adapter);
        recvCategories.setLayoutManager(new FlowLayoutManager());
        makeMockCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> adapter.setItems(categories));
    }

    private Observable<List<Category>> makeMockCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Button"));
        categories.add(new Category("Calendar"));
        categories.add(new Category("Effect"));
        categories.add(new Category("Graph"));
        categories.add(new Category("Image"));
        categories.add(new Category("Label/Form"));
        categories.add(new Category("List/Grid"));
        categories.add(new Category("Loading"));
        categories.add(new Category("Menu"));
        categories.add(new Category("Progress"));
        categories.add(new Category("SeekBar"));
        categories.add(new Category("SideBar"));
        return Observable.just(categories);
    }



}
