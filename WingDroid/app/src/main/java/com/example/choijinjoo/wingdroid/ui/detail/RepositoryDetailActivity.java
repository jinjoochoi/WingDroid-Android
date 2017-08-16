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
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.source.remote.firebase.UserDataSource;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class RepositoryDetailActivity extends BaseActivity implements View.OnClickListener, ChildEventListener{
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
    SimmilarsAdapter simmilarsAdapter;
    Repository repository;
    Query query;


    public static Intent getStartIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra("repository", Parcels.wrap(repository));
        return intent;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_repository_detail;
    }

    @Override
    protected void initLayout() {
        repository = Parcels.unwrap(getIntent().getParcelableExtra("repository"));
        Glide.with(this)
                .load(repository.getImage())
                .into(imgvPreview);
        txtvName.setText(repository.getName());
        txtvDescription.setText(repository.getDescription());
        txtvAuthor.setText("by. " + repository.getAuthor());
        btnIssue.setOnClickListener(it -> showWebViewActivity(repository.getIssueUrl()));
        imgvGithub.setOnClickListener(it -> showWebViewActivity(repository.getGit()));
        txtvStar.setText(repository.getFormattedStarString());
        txtvIssueCount.setText(Integer.toString(repository.getIssue()));

        recvSimmilarLibs.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        simmilarsAdapter = new SimmilarsAdapter(this, position -> moveToDetailActivity(simmilarsAdapter.getItem(position)));
        recvSimmilarLibs.setAdapter(simmilarsAdapter);
        imgvBookMark.setOnClickListener(this);
    }


    private void showWebViewActivity(String url) {
        Intent intent = WebViewAcitivty.getStartIntent(this,url);
        startActivity(intent);
        overridePendingTransition(0,0);
    }


    private void moveToDetailActivity(Repository repository) {
        startActivity(RepositoryDetailActivity.getStartIntent(RepositoryDetailActivity.this, repository));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            query = UserDataSource.getInstance().getBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid());
            query.addChildEventListener(this);
        } else {
            imgvBookMark.setSelected(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgvBookMark:
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    showToastMessage(R.string.toast_message_no_auth);
                } else {
                    if(!imgvBookMark.isSelected())
                        UserDataSource.getInstance().addBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid(),repository.getId());
                    else
                        UserDataSource.getInstance().deleteBookmark(FirebaseAuth.getInstance().getCurrentUser().getUid(),repository.getId());
                }
                break;
        }
    }


    /*
     *  Repository DataReference change listener
     */

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        String repositoryId = (String)dataSnapshot.getValue();
        if(repositoryId.equals(repository.getId()))
            imgvBookMark.setSelected(true);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String repositoryId = (String)dataSnapshot.getValue();
        if(repositoryId.equals(repository.getId()))
            imgvBookMark.setSelected(false);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

