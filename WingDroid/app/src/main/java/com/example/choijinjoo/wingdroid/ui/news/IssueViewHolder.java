package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.model.event.IEvent;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class IssueViewHolder extends EventViewHolder{
    @BindView(R.id.txtvNumber) TextView txtvNumber;
    private Event news;

    public IssueViewHolder(Context context, View itemView, EventAdapter.EventClickListener listener ) {
        super(context, itemView,listener);
    }

    @Override
    public void bindData(Event item) {
        this.news = item;
        super.bindData(item);
        txtvNumber.setText(String.format("#%d",item.getIssue().getNumber()));
    }

    @Override
    protected IEvent getEvent() {
        return news.getIssue();
    }
}
