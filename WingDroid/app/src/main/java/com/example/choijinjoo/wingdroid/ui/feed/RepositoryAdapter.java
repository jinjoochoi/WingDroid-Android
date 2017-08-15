package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class RepositoryAdapter extends FirebaseRecyclerAdapter<Repository,RepositoryViewHolder> {
    RepositoryClickedListener listener;
    Context context;

    public RepositoryAdapter(Class<Repository> modelClass, int modelLayout, Class<RepositoryViewHolder> viewHolderClass, Query ref, Context context, RepositoryClickedListener listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);
        RepositoryViewHolder viewHolder =  new RepositoryViewHolder(context, view);
        view.setOnClickListener(it -> listener.clicked(viewHolder.getSafeAdapterPosition()));
        return viewHolder;
    }

    @Override
    protected void populateViewHolder(RepositoryViewHolder viewHolder, Repository model, int position) {
        viewHolder.bindData(model);
    }

    public interface RepositoryClickedListener{
        void clicked(int position);
    }

}
