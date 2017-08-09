package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.event.IEvent;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class EventViewHolder extends BaseViewHolder<Event>{
    @BindView(R.id.container) RelativeLayout container;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.txtvTitle) TextView txtvTitle;
    @BindView(R.id.txtvIssueInfo) TextView txtvIssueInfo;
    EventAdapter.EventClickListener listener;

    public EventViewHolder(Context context, View itemView, EventAdapter.EventClickListener listener) {
        super(context, itemView);
        this.listener = listener;
    }

    @CallSuper
    @Override
    public void bindData(Event item) {
        IEvent event = getEvent();
        txtvName.setText(event.getRepoAndAuthorName());
        txtvTitle.setText(event.getMessage());
        txtvIssueInfo.setText(event.getEventInfoString());
        container.setOnClickListener(it -> listener.clicked(getSafeAdapterPosition()));
    }

    protected abstract IEvent getEvent();
}
