package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.ui.base.BaseAdapter;

import java.util.ArrayList;

import static com.example.choijinjoo.wingdroid.model.event.Event.EVENT_COMMIT;
import static com.example.choijinjoo.wingdroid.model.event.Event.EVENT_RELEASE;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class EventAdapter extends BaseAdapter<Event, EventViewHolder> {
    EventClickListener listener;


    public interface EventClickListener {
        void clicked(int position);
        boolean longClicked(int position);
    }

    public EventAdapter(Context context, EventClickListener listener) {
        super(context, new ArrayList<>());
        this.listener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        EventViewHolder viewHolder =  null;
        switch (viewType){
            case EVENT_COMMIT:
                view = LayoutInflater.from(context).inflate(R.layout.item_commit, parent, false);
                viewHolder = new PushViewHolder(context, view,listener);
                break;
            case EVENT_RELEASE:
                view = LayoutInflater.from(context).inflate(R.layout.item_release, parent, false);
                viewHolder = new ReleaseViewHolder(context, view,listener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }
}
