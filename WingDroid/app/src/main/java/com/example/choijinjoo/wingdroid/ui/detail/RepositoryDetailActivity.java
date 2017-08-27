package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissLinearLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.TagRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.model.eventbus.RepoBookMarkEvent;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.example.choijinjoo.wingdroid.ui.simulate.LottieActivity;

import org.apmem.tools.layouts.FlowLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class RepositoryDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.imgvBookMark)
    ImageView imgvBookMark;
    @BindView(R.id.imgvShare)
    ImageView imgvShare;
    @BindView(R.id.imgvPreview)
    ImageView imgvPreview;
    @BindView(R.id.txtvName)
    TextView txtvName;
    @BindView(R.id.txtvDescription)
    TextView txtvDescription;
    @BindView(R.id.btnIssue)
    LinearLayout btnIssue;
    @BindView(R.id.txtvIssue)
    TextView txtvIssueCount;
    @BindView(R.id.txtvStar)
    TextView txtvStar;
    @BindView(R.id.txtvInnerStar)
    TextView txtvInnerStar;
    @BindView(R.id.txtvAuthor)
    TextView txtvAuthor;
    @BindView(R.id.txtvWatch)
    TextView txtvWatch;
    @BindView(R.id.txtvFork)
    TextView txtvFork;
    @BindView(R.id.imgvGithub)
    ImageView imgvGithub;
    @BindView(R.id.recvSimmilarLibs)
    RecyclerView recvSimmilarLibs;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.txtvSimulate)
    TextView txtvSimulate;
    @BindView(R.id.btnSimulate)
    LinearLayout btnSimulate;
    @BindView(R.id.imgvSimulate)
    ImageView imgvSimulate;
    @BindView(R.id.elasticDragDismissLinearLayout)
    ElasticDragDismissLinearLayout elasticDragDismissLinearLayout;


    TagRepository tagRepository;
    ReleatedReposAdapter simmilarsAdapter;
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
        repositories.add(tagRepository = new TagRepository(getBaseContext()));
        recvSimmilarLibs.setLayoutManager(new GridLayoutManager(getBaseContext(), 3, LinearLayoutManager.VERTICAL, false));
        simmilarsAdapter = new ReleatedReposAdapter(getBaseContext(), position -> moveToDetailActivity(simmilarsAdapter.getItem(position)));
        recvSimmilarLibs.setAdapter(simmilarsAdapter);
        imgvBookMark.setOnClickListener(this);
        imgvShare.setOnClickListener(this);
        String repoId = getIntent().getStringExtra("repoId");
        loadRepository(repoId);
    }

    private void loadRepository(String repoId) {
        disposables.add(RepositoryRepository.getInstance(getBaseContext()).getRepositoryById(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setRepoItem));
    }

    private void setRepoItem(Repository item) {
        repository = item;
        Glide.with(getBaseContext())
                .load(repository.getImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgvPreview);
        txtvName.setText(repository.getName());
        txtvDescription.setText(repository.getDescription());
        txtvAuthor.setText("by. " + repository.getAuthor());
        btnIssue.setOnClickListener(it -> showWebViewActivity(repository.getIssueUrl()));
        imgvGithub.setOnClickListener(it -> showWebViewActivity(repository.getGit()));
        txtvStar.setText(repository.getFormattedStarString());
        txtvInnerStar.setText(repository.getFormattedStarString());
        txtvIssueCount.setText(Integer.toString(repository.getIssue()));

        imgvBookMark.setSelected(repository.getBookmark());

        txtvWatch.setText(String.valueOf(repository.getWatch()));
        txtvFork.setText(String.valueOf(repository.getFormattedForkString()));

        btnSimulate.setEnabled(repository.isSimulate());
        txtvSimulate.setEnabled(repository.isSimulate());
        imgvSimulate.setEnabled(repository.isSimulate());
        if (repository.isSimulate())
            btnSimulate.setOnClickListener(this);

        flowLayout.removeAllViews();
        for (Tag tag : repository.getTags()) {
            TextView txtvTag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tag, null, false).findViewById(R.id.txtvTag);
            txtvTag.setText(tag.toString());
            flowLayout.addView(txtvTag);
        }

        disposables.add(RepositoryRepository.getInstance(getBaseContext()).getRelatedRepo(repository)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setRelatedRepoItems));
        addClick();

        elasticDragDismissLinearLayout.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                finish();
                overridePendingTransition(0,0);

            }
        });
    }

    private void setRelatedRepoItems(List<Repository> items) {
        simmilarsAdapter.setItems(items);
    }

    private void showWebViewActivity(String url) {
        Intent intent = WebViewAcitivty.getStartIntent(this, url);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void addClick() {
        for (Tag tag : repository.getTags()) {
            tag.click();
            tagRepository.updateTag(tag);
        }
        repository.click();
        RepositoryRepository.getInstance(getBaseContext()).addClick(repository);
    }


    private void moveToDetailActivity(Repository repository) {
        startActivity(RepositoryDetailActivity.getStartIntent(RepositoryDetailActivity.this, repository.getId()));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvBookMark:
                repository.clickBookmark();
                RepositoryRepository.getInstance(getBaseContext()).addBookmark(repository);
                break;

            case R.id.btnSimulate:
                Intent intent = LottieActivity.getStartIntent(this, repository.getName());
                startActivity(intent);
                break;

            case R.id.imgvShare:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, repository.getGit());
                Intent chooser = Intent.createChooser(intent1, "Share with your partner! ");
                startActivity(chooser);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RepoBookMarkEvent event) {
        reloadBookmark(repository.getId());
    }

    private void reloadBookmark(String repoId) {
        disposables.add(RepositoryRepository.getInstance(getBaseContext()).getRepositoryById(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repository -> {
                    this.repository = repository;
                    imgvBookMark.setSelected(repository.getBookmark());
                }));

    }


}

