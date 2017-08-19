package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.TagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.source.remote.firebase.UserDataSource;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.j256.ormlite.dao.Dao;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class RepositoryDetailActivity extends BaseActivity implements View.OnClickListener, Dao.DaoObserver{
    @BindView(R.id.imgvBookMark) ImageView imgvBookMark;
    @BindView(R.id.imgvShare) ImageView imgvShare;
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.txtvDescription) TextView txtvDescription;
    @BindView(R.id.btnIssue) LinearLayout btnIssue;
    @BindView(R.id.txtvIssueCount) TextView txtvIssueCount;
    @BindView(R.id.txtvStar) TextView txtvStar;
    @BindView(R.id.txtvAuthor) TextView txtvAuthor;
    @BindView(R.id.imgvGithub) ImageView imgvGithub;
    @BindView(R.id.recvSimmilarLibs) RecyclerView recvSimmilarLibs;
    RepositoryRepository repositoryRepository;
    TagRepository tagRepository;
    SimmilarsAdapter simmilarsAdapter;
    Repository repository;


    public static Intent getStartIntent(Context context, String repoId) {
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra("repoId", repoId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_repository_detail;
    }

    @Override
    protected void initLayout() {
        repositoryRepository = new RepositoryRepository(this);
        tagRepository = new TagRepository(this);
        String repoId = getIntent().getStringExtra("repoId");
        loadData(repoId);

        recvSimmilarLibs.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        simmilarsAdapter = new SimmilarsAdapter(this, position -> moveToDetailActivity(simmilarsAdapter.getItem(position)));
        recvSimmilarLibs.setAdapter(simmilarsAdapter);
        imgvBookMark.setOnClickListener(this);
    }

    protected void loadData(String repoId) {
        super.loadData();
        repository = repositoryRepository.getRepositoryById(repoId);
        Glide.with(this).load(repository.getImage()).into(imgvPreview);
        txtvName.setText(repository.getName());
        txtvDescription.setText(repository.getDescription());
        txtvAuthor.setText("by. " + repository.getAuthor());
        btnIssue.setOnClickListener(it -> showWebViewActivity(repository.getIssueUrl()));
        imgvGithub.setOnClickListener(it -> showWebViewActivity(repository.getGit()));
        txtvStar.setText(repository.getFormattedStarString());
        txtvIssueCount.setText(Integer.toString(repository.getIssue()));
        imgvBookMark.setSelected(repository.getBookmark());
        addClick();
    }

    private void showWebViewActivity(String url) {
        Intent intent = WebViewAcitivty.getStartIntent(this,url);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private void addClick(){
        for(Tag tag : repository.getTags()) {
            tag.click();
            tagRepository.updateTag(tag);
        }
    }


    private void moveToDetailActivity(Repository repository) {
        startActivity(RepositoryDetailActivity.getStartIntent(RepositoryDetailActivity.this, repository.getId()));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvBookMark:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if(!repository.getBookmark()) {
                        UserDataSource.getInstance().addBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid(), repository.getId());
                    }
                    else {
                        UserDataSource.getInstance().deleteBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid(), repository.getId());
                    }
                }
                repository.clickBookmark();
                repositoryRepository.updateRepository(repository);
                break;
        }

    }

    @Override
    public void onChange() {
        loadData(repository.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        repositoryRepository.registerDaoObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        repositoryRepository.unregisterDaoObserver(this);

    }
}

