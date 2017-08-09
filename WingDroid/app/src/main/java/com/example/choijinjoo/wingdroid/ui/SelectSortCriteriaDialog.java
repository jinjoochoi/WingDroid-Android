package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.os.Bundle;
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

    public interface CriteriaSelectedListener{
        void seleted(SortCriteria criteria);
    }

    public static SelectSortCriteriaDialog getInstance(Context context, CriteriaSelectedListener listener){
        return new SelectSortCriteriaDialog(context,listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_select_sort_criteria;
    }

    public SelectSortCriteriaDialog(@NonNull Context context, CriteriaSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void initLayout() {
        txtvRecent.setOnClickListener(this);
        txtvStar.setOnClickListener(this);
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
