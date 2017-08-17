package com.example.choijinjoo.wingdroid.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;


import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2017. 8. 7..
 */

public abstract class BaseBottomSheetDialog extends BottomSheetDialog {
    protected Context context;

    abstract protected int getLayoutId();
    abstract protected void initLayout();

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(view);
        ButterKnife.bind(this,view);
    }
}
