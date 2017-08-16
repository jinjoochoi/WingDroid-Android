package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.source.remote.firebase.RepositoryDataSource;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedFragment extends BaseFragment implements FirebaseArray.OnChangedListener{
    @BindView(R.id.recvRepositories) RecyclerView recvRepositories;
    @BindView(R.id.containerSort) LinearLayout containerSort;
    RepositoryAdapter adapter;
    Query ref;
    StaggeredGridLayoutManager layoutManager;
    Category category;
    FirebaseArray firebaseArray;

    private static final String KEY_CATEGORY = "category";

    public static FeedFragment newInstance(Category category) {
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY, Parcels.wrap(category));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed;
    }

    @Override
    protected void initLayout() {
        if (ref != null) {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recvRepositories.setLayoutManager(layoutManager);
            adapter = new RepositoryAdapter(getActivity(), this::moveToDetailActivity);
            recvRepositories.setAdapter(adapter);
            containerSort.setOnClickListener(this::showSelectSortCriteriaDialog);
            firebaseArray.setOnChangedListener(this);
        }
    }

    @Override
    protected void loadData() {
        category = Parcels.unwrap(getArguments().getParcelable(KEY_CATEGORY));
        if (category != null) {
            if (category.getName().equals("All"))
                ref = RepositoryDataSource.getInstance().repositories();
            else
                ref = RepositoryDataSource.getInstance().repositoriesByCategory(category);
            firebaseArray = new FirebaseArray(ref);
        }
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position));
        startActivity(intent);
    }

    private void showSelectSortCriteriaDialog(View view) {
        SelectSortCriteriaDialog.getInstance(
                getActivity(), it -> {
                    if (it == SortCriteria.RECENT) {
                        layoutManager.setReverseLayout(false);
                        firebaseArray = new FirebaseArray(ref.orderByChild("updatedAt"));
                    } else {
                        firebaseArray = new FirebaseArray(ref.orderByChild("star"));
                        layoutManager.setReverseLayout(true);
                    }
                    adapter.clear();
                    recvRepositories.setLayoutManager(layoutManager);
                    firebaseArray.setOnChangedListener(this);
                }).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firebaseArray.setOnChangedListener(null);
        firebaseArray.cleanup();
    }

    /*
     *  Repository DataReference change listener
     */

    @Override
    public void onChildChanged(EventType type, int index, int oldIndex) {
        switch (type) {
            case ADDED:
                // Category가 All인 경우에는  모든 Repository를 불러옵니다.
                if(category.getName().equals("All")){
                    Repository repository = firebaseArray.getItem(index).getValue(Repository.class);
                    repository.setId(firebaseArray.getItem(index).getKey());
                    adapter.add(repository);
                    adapter.notifyItemInserted(index);
                }else {
                    // Category가 있는 경우에는 Category DataReference에서 해당 카테고리를 가지고 있는 Repository의 id를 가져온 뒤,
                    // Repository DataReference에서 id로 Repository를 가져옵니다.
                    String repositoryId = (String) firebaseArray.getItem(index).getValue();
                    RepositoryDataSource.getInstance().getRepositoryById(
                            repositoryId, new RepositoryDataSource.RepositoryListener() {
                                @Override
                                public void added(Repository repository) {
                                    repository.setId(repositoryId);
                                    adapter.add(repository);
                                    adapter.notifyItemInserted(index);
                                }
                                @Override
                                public void empty() {

                                }
                            });
                }
                break;
            case CHANGED:
                adapter.notifyItemChanged(index);
                break;
            case REMOVED:
                adapter.notifyItemRemoved(index);
                break;
            case MOVED:
                adapter.notifyItemMoved(oldIndex, index);
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
}
