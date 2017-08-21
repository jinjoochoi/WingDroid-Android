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
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.RTCategoryRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.SearchHistoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.j256.ormlite.dao.Dao;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import org.parceler.Parcels;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.choijinjoo.wingdroid.model.SearchHistory.SEARCH_TYPE_CATEGORY;
import static com.example.choijinjoo.wingdroid.model.SearchHistory.SEARCH_TYPE_TEXT;

/**
 * Created by choijinjoo on 2017. 8. 16..
 */

public class SearchResultActivity extends BaseActivity implements Dao.DaoObserver{

    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.containerHistory) LinearLayout containerHistory;
    @BindView(R.id.containerResult) RelativeLayout containerResult;
    @BindView(R.id.containerEmpty) RelativeLayout containerEmpty;
    @BindView(R.id.recvResults) RecyclerView recvResults;
    @BindView(R.id.recvHistories) RecyclerView recvHistories;
    @BindView(R.id.btnSearch) RelativeLayout btnSearch;
    @BindView(R.id.txtvNoSearchResult) TextView txtvNoSearchResult;

    String searchType = "";

    SearchResultAdapter resultAdapter;
    SearchHistoryAdapter searchHistoryAdapter;

    RTCategoryRepositoryRepository rtCategoryRepositoryRepository;
    SearchHistoryRepository searchHistoryRepository;
    RepositoryRepository repositoryRepository;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        return intent;
    }

    public static Intent getStartIntent(Context context, Category category) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SearchHistory.SEARCH_TYPE_CATEGORY, Parcels.wrap(category));
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
                .observeOn(AndroidSchedulers.mainThread())
                .filter(this::isEmpty)
                .map(c -> c.toString())
                .subscribe(this::searchWithText);


        // search history container
        searchHistoryAdapter = new SearchHistoryAdapter(this,
                new SearchHistoryAdapter.SearchHistoryListener() {
                    @Override
                    public void selected(int position) {
                        if (searchHistoryAdapter.getItem(position).getSearch().equals(SearchHistory.SEARCH_TYPE_CATEGORY))
                            searchWithText(searchHistoryAdapter.getItem(position).getSearch());
                        else {
                            searchWithText(searchHistoryAdapter.getItem(position).getSearch());
                        }
                    }

                    @Override
                    public void delete(int position) {
                        searchHistoryRepository.deleteSearchHistory(searchHistoryAdapter.getItem(position));
                    }
                });
        recvHistories.setAdapter(searchHistoryAdapter);
        btnSearch.setOnClickListener(it -> {
            searchView.onActionViewExpanded();
        });
        searchView.setOnQueryTextFocusChangeListener(((view, b) -> {
            if (!view.isFocused()) {
                containerHistory.setVisibility(View.VISIBLE);
                containerResult.setVisibility(View.GONE);
            }
        }));

        // result container
        resultAdapter = new SearchResultAdapter(this, this::moveToDetailActivity);
        recvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recvResults.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recvResults.setAdapter(resultAdapter);

        // result history container
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayout.setStackFromEnd(true);
        linearLayout.setReverseLayout(true);
        recvHistories.setLayoutManager(linearLayout);
        recvHistories.setHasFixedSize(true);


    }

    private boolean isEmpty(CharSequence c){
        if(StringUtils.isEmpty(c)) {
            containerHistory.setVisibility(View.VISIBLE);
        }
        return !StringUtils.isEmpty(c) && !searchType.equals(SearchHistory.SEARCH_TYPE_CATEGORY);
    }

    private void init(CharSequence str) {
        if (containerEmpty.getVisibility() == View.VISIBLE)
            containerEmpty.setVisibility(View.GONE);
    }


    private String textSearch;

    public void searchWithText(String text) {
        searchType = SEARCH_TYPE_TEXT;
        containerHistory.setVisibility(View.GONE);
        containerResult.setVisibility(View.VISIBLE);
        List<Repository> results = repositoryRepository.getRepositoryByText(text);
        if(results.size() == 0){
            containerEmpty.setVisibility(View.VISIBLE);
        }
        resultAdapter.setItems(results);
        this.textSearch = text;
    }

    private void searchWithCategory(Category category) {
        searchType = SearchHistory.SEARCH_TYPE_CATEGORY;
        searchView.onActionViewExpanded();

        searchView.setQuery(category.getName(), false);
        containerHistory.setVisibility(View.GONE);
        containerResult.setVisibility(View.VISIBLE);

        rtCategoryRepositoryRepository.getRepoForCategoryOrderByStar(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setSearchResultItems);

        addSearchHistory(category.getName(), SEARCH_TYPE_CATEGORY);
    }

    private void setSearchResultItems(List<Repository> items){
        if (items.size() == 0) {
            containerEmpty.setVisibility(View.VISIBLE);
        } else {
            containerEmpty.setVisibility(View.GONE);
            resultAdapter.setItems(items);
        }
    }

    private void addSearchHistory(String searchs, String searchType) {
        searchHistoryRepository.addSearchHistory(new SearchHistory(searchs, searchType));
    }


    private void moveToDetailActivity(int position) {
        if (searchType.equals(SEARCH_TYPE_TEXT)) {
            addSearchHistory(resultAdapter.getItem(position).getName(), textSearch);
        }
        Intent intent = RepositoryDetailActivity.getStartIntent(this, resultAdapter.getItem(position).getId());
        startActivity(intent);
    }


    @Override
    protected void loadData() {
        super.loadData();
        rtCategoryRepositoryRepository = new RTCategoryRepositoryRepository(this);
        repositoryRepository = new RepositoryRepository(this);
        searchHistoryRepository = new SearchHistoryRepository(this);

        // start search with category
        Category category = Parcels.unwrap(getIntent().getParcelableExtra(SEARCH_TYPE_CATEGORY));
        if (category != null) {
            searchWithCategory(category);
        }
        loadSearchHistories();
    }

    private void loadSearchHistories(){
        searchHistoryRepository.getSearchHistories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setSearchHistoryItems);
    }

    private void setSearchHistoryItems(List<SearchHistory> items){
        if(items.size() == 0){
            txtvNoSearchResult.setVisibility(View.VISIBLE);
        }else{
            txtvNoSearchResult.setVisibility(View.GONE);
        }
        searchHistoryAdapter.setItems(items);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        searchHistoryRepository.registerDaoObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchHistoryRepository.unregisterDaoObserver(this);

    }

    @Override
    public void onChange() {
        loadSearchHistories();
    }
}
