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

public class ReleaseViewHolder extends EventViewHolder{
    private Event news;
    @BindView(R.id.txtvTag)
    TextView txtvTag;

    public ReleaseViewHolder(Context context, View itemView, EventAdapter.EventClickListener listener) {
        super(context, itemView, listener);
    }

    @Override
    public void bindData(Event item) {
        this.news = item;
        super.bindData(item);
        txtvTag.setText(item.getRelease().getTagName());
    }

    @Override
    protected IEvent getEvent() {
        return news.getRelease();
    }
}
