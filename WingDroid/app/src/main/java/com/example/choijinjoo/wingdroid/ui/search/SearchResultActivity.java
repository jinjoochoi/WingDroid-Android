package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.source.remote.firebase.RepositoryDataSource;
import com.example.choijinjoo.wingdroid.source.remote.firebase.UserDataSource;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray;
import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by choijinjoo on 2017. 8. 16..
 */

public class SearchResultActivity extends BaseActivity  {
    public static final String SEARCH_BY_CATEGORY = "category";
    public static final String SEARCH_BY_NAME = "name";
    public static final String SEARCH_BY_TAG = "tag";

    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.containerHistory) LinearLayout containerHistory;
    @BindView(R.id.containerResult) RelativeLayout containerResult;
    @BindView(R.id.containerEmpty) RelativeLayout containerEmpty;
    @BindView(R.id.recvResults) RecyclerView recvResults;
    @BindView(R.id.recvHistories) RecyclerView recvHistories;
    @BindView(R.id.btnSearch) RelativeLayout btnSearch;
    FirebaseArray resultFBArray;
    FirebaseArray resultHistoryFBArray;

    String searchType = "";

    SearchResultAdapter resultAdapter;
    SearchHistoryAdapter searchHistoryAdapter;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        return intent;
    }

    public static Intent getStartIntent(Context context, Category category) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCH_BY_CATEGORY, Parcels.wrap(category));
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_search_result;
    }

    @Override
    protected void initLayout() {
        RxSearchView.queryTextChanges(searchView)
                .doOnNext(this::init)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(c -> !StringUtils.isEmpty(c) && !searchType.equals(SEARCH_BY_CATEGORY))
                .map(c -> c.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::searchWithName);

        // search history container
        searchHistoryAdapter = new SearchHistoryAdapter(this,
                new SearchHistoryAdapter.SearchHistoryListener() {
                    @Override
                    public void selected(int position) {
                        if(searchHistoryAdapter.getItem(position).getType().equals(SEARCH_BY_CATEGORY))
                            searchWithCategory(searchHistoryAdapter.getItem(position).getCategory());
                        else{
                            searchWithName(searchHistoryAdapter.getItem(position).getSearch());
                        }
                    }

                    @Override
                    public void delete(int position) {
                        UserDataSource.getInstance().deleteSearchHistory(
                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                searchHistoryAdapter.getItem(position).getId());
                    }
                });
        recvHistories.setAdapter(searchHistoryAdapter);
        btnSearch.setOnClickListener(it -> {
            searchView.onActionViewExpanded();
        });
        searchView.setOnQueryTextFocusChangeListener(((view, b) -> {
            if(!view.isFocused()) {
                containerHistory.setVisibility(View.VISIBLE);
                containerResult.setVisibility(View.GONE);
            }
        }));

        // result container
        resultAdapter = new SearchResultAdapter(this,this::moveToDetailActivity);
        recvResults.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recvResults.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recvResults.setAdapter(resultAdapter);

        // result history container
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayout.setStackFromEnd(true);
        linearLayout.setReverseLayout(true);
        recvHistories.setLayoutManager(linearLayout);
        recvHistories.setHasFixedSize(true);


    }

    private void init(CharSequence str){
        if(containerEmpty.getVisibility() == View.VISIBLE)
            containerEmpty.setVisibility(View.GONE);
    }


    public void searchWithName(String name) {
        searchType = SEARCH_BY_NAME;
        containerHistory.setVisibility(View.GONE);
        containerResult.setVisibility(View.VISIBLE);
        Query query = RepositoryDataSource.getInstance().repositoriesByName(name);
        resultFBArray = new FirebaseArray(query);
        resultFBArray.setOnChangedListener(resultArrayListener);
    }

    private void searchWithCategory(Category category) {
        searchType = SEARCH_BY_CATEGORY;
        searchView.onActionViewExpanded();

        searchView.setQuery(category.getName(), false);
        containerHistory.setVisibility(View.GONE);
        containerResult.setVisibility(View.VISIBLE);
        Query query = RepositoryDataSource.getInstance().repositoriesByCategory(category);
        resultFBArray = new FirebaseArray(query);
        resultFBArray.setOnChangedListener(resultArrayListener);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            UserDataSource.getInstance().addSearchHistory(firebaseUser.getUid(),
                    new SearchHistory(category, Calendar.getInstance().getTime(), SEARCH_BY_CATEGORY));
        }
    }


    private void moveToDetailActivity(int position){
        if(searchType == SEARCH_BY_NAME){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if(firebaseUser != null) {
                UserDataSource.getInstance().addSearchHistory(firebaseUser.getUid(),
                        new SearchHistory(searchView.getQuery().toString(), Calendar.getInstance().getTime(), SEARCH_BY_NAME));
            }
        }
        Intent intent = RepositoryDetailActivity.getStartIntent(this, resultAdapter.getItem(position).getId());
        startActivity(intent);
    }


    @Override
    protected void loadData() {
        super.loadData();
        // start search with category
        Category category = Parcels.unwrap(getIntent().getParcelableExtra(SEARCH_BY_CATEGORY));
        if (category != null) {
            searchWithCategory(category);
        }
        Query query = UserDataSource.getInstance().getSearchHistories(FirebaseAuth.getInstance().getCurrentUser().getUid());
        resultHistoryFBArray = new FirebaseArray(query);
        resultHistoryFBArray.setOnChangedListener(resultHistoryArrayListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    /*
     * Repository Result Data Source change Listener
     */


    FirebaseArray.OnChangedListener resultArrayListener = new FirebaseArray.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            switch (type) {
                case ADDED:
                    // Category로 검색한 경우
                    if (searchType.equals(SEARCH_BY_CATEGORY)) {
                        String repositoryId = (String) resultFBArray.getItem(index).getValue();
                        RepositoryDataSource.getInstance().getRepositoryById(
                                repositoryId, new RepositoryDataSource.RepositoryListener() {
                                    @Override
                                    public void added(Repository repository) {
                                        repository.setId(repositoryId);
                                        resultAdapter.add(repository);
                                    }
                                    @Override
                                    public void empty() {

                                    }
                                });
                    } else {
                        // name으로 검색한 경우
                        Repository repository = resultFBArray.getItem(index).getValue(Repository.class);
                        repository.setId(resultFBArray.getItem(index).getKey());
                        resultAdapter.add(repository);
                    }
                    break;
                case CHANGED:
                    if (searchType.equals(SEARCH_BY_CATEGORY)) {
                        String repositoryId = (String) resultFBArray.getItem(index).getValue();
                        RepositoryDataSource.getInstance().getRepositoryById(repositoryId, new RepositoryDataSource.RepositoryListener() {
                            @Override
                            public void added(Repository repository) {
                                repository.setId(repositoryId);
                                resultAdapter.change(index, repository);
                            }

                            @Override
                            public void empty() {

                            }
                        });
                    } else {
                        resultAdapter.change(index, resultFBArray.getItem(index).getValue(Repository.class));
                    }
                case REMOVED:
                    resultAdapter.remove(index);
                    break;
                case MOVED:
                    resultAdapter.notifyItemMoved(oldIndex, index);
                    break;
                default:
                    throw new IllegalStateException("Incomplete case statement");
            }
        }

        @Override
        public void onDataChanged() {
            if(resultFBArray.getCount() == 0 && containerEmpty.getVisibility() == View.GONE){
                containerEmpty.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    FirebaseArray.OnChangedListener resultHistoryArrayListener = new FirebaseArray.OnChangedListener() {
        @Override
        public void onChildChanged(EventType type, int index, int oldIndex) {
            switch (type) {
                case ADDED:
                    SearchHistory searchHistory = resultHistoryFBArray.getItem(index).getValue(SearchHistory.class);
                    searchHistory.setId(resultHistoryFBArray.getItem(index).getKey());
                    searchHistoryAdapter.add(searchHistory);
                    break;
                case REMOVED:
                    searchHistoryAdapter.remove(index);
                    break;
            }
        }

        @Override
        public void onDataChanged() {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
