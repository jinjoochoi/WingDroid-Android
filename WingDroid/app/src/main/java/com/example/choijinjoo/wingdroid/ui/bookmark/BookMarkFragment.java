package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.source.remote.firebase.RepositoryDataSource;
import com.example.choijinjoo.wingdroid.source.remote.firebase.UserDataSource;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterDialog;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkFragment extends BaseFragment implements FirebaseArray.OnChangedListener{
    @BindView(R.id.recvRepositories) RecyclerView recvRepositories;
    @BindView(R.id.containerSort) LinearLayout containerSort;
    @BindView(R.id.imgvFilter) ImageView imgvFilter;
    BookMarkAdapter adapter;
    FirebaseArray firebaseArray;

    private SortCriteria order_by;


    public static BookMarkFragment newInstance() {
        return new BookMarkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_bookmark;
    }

    @Override
    protected void initLayout() {
        recvRepositories.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new BookMarkAdapter(getActivity(),this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);
        recvRepositories.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        containerSort.setOnClickListener(it -> showSelectSortCriteriaDialog());
        imgvFilter.setOnClickListener(it -> showCategoryFilterDialog());
        firebaseArray.setOnChangedListener(this);
    }

    @Override
    protected void loadData() {
        order_by = SortCriteria.RECENT;
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Query query = UserDataSource.getInstance().getBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid());
            firebaseArray = new FirebaseArray(query);
        }else{

        }
    }
    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),adapter.getItem(position));
        startActivity(intent);
    }

    private void showCategoryFilterDialog() {
        CategoryFilterDialog.getInstance(getActivity(),
                category -> {

                }).show();
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(), order_by,
                criteria -> {

                }).show();
    }


    @Override
    public void onChildChanged(EventType type, int index, int oldIndex) {
        switch (type) {
            case ADDED:
                RepositoryDataSource.getInstance().getRepositoryById((String) firebaseArray.getItem(index).getValue(),
                        new RepositoryDataSource.RepositoryListener() {
                            @Override
                            public void added(Repository repository) {
                                repository.setId((String)firebaseArray.getItem(index).getValue());
                                adapter.add(repository);
                                adapter.notifyItemChanged(index);
                            }

                            @Override
                            public void empty() {

                            }
                        });
                break;
            case REMOVED:
                adapter.remove(index);
                adapter.notifyItemRemoved(index);
                break;
            default:
                throw new IllegalStateException("Incomplete case statement");
        }
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseArray.cleanup();
    }

}
