package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseBottomSheetDialog;
import com.example.choijinjoo.wingdroid.ui.news.EventAdapter;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 7..
 */

public class EventFilterDialog extends BaseBottomSheetDialog implements View.OnClickListener{
    @BindView(R.id.txtvPush) TextView txtvPush;
    @BindView(R.id.txtvRelease) TextView txtvRelease;
    FilterSelectedListener listener;
    public int order_by;

    public interface FilterSelectedListener{
        void seleted(int criteria);
    }

    public static EventFilterDialog getInstance(Context context, int order_by, FilterSelectedListener listener){
        return new EventFilterDialog(context,order_by,listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_event_filter;
    }

    public EventFilterDialog(@NonNull Context context, int order_by, FilterSelectedListener listener) {
        super(context);
        this.listener = listener;
        this.order_by = order_by;
        initLayout();
    }

    @Override
    protected void initLayout() {
        txtvPush.setOnClickListener(this);
        txtvRelease.setOnClickListener(this);
        txtvPush.setSelected(order_by == EventAdapter.EVENT_COMMIT);
        txtvRelease.setSelected(order_by == EventAdapter.EVENT_RELEASE);

        setCancelable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtvPush:
                txtvRelease.setSelected(false);
                txtvPush.setSelected(true);
                listener.seleted(EventAdapter.EVENT_COMMIT);
                break;
            case R.id.txtvRelease:
                txtvPush.setSelected(false);
                txtvRelease.setSelected(true);
                listener.seleted(EventAdapter.EVENT_RELEASE);
                break;
        }
    }
}
