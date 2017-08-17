package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.ui.base.BaseBottomSheetDialog;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 7..
 */

public class SelectSortCriteriaDialog extends BaseBottomSheetDialog implements View.OnClickListener{
    @BindView(R.id.txtvRecent) TextView txtvRecent;
    @BindView(R.id.txtvStar) TextView txtvStar;
    CriteriaSelectedListener listener;
    SortCriteria order_by;

    public interface CriteriaSelectedListener{
        void seleted(SortCriteria criteria);
    }

    public static SelectSortCriteriaDialog getInstance(Context context, SortCriteria order_by, CriteriaSelectedListener listener){
        return new SelectSortCriteriaDialog(context,order_by,listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_select_sort_criteria;
    }

    public SelectSortCriteriaDialog(@NonNull Context context, SortCriteria order_by, CriteriaSelectedListener listener) {
        super(context);
        this.listener = listener;
        this.order_by = order_by;
        initLayout();
    }

    @Override
    protected void initLayout() {
        txtvRecent.setOnClickListener(this);
        txtvStar.setOnClickListener(this);
        txtvStar.setSelected(order_by == SortCriteria.STAR);
        txtvRecent.setSelected(order_by == SortCriteria.RECENT);

        setCancelable(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtvRecent:
                txtvStar.setSelected(false);
                txtvRecent.setSelected(true);
                listener.seleted(SortCriteria.RECENT);
                break;
            case R.id.txtvStar:
                txtvRecent.setSelected(false);
                txtvStar.setSelected(true);
                listener.seleted(SortCriteria.STAR);
                break;
        }
    }
}
