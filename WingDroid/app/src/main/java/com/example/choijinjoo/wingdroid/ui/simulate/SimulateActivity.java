package com.example.choijinjoo.wingdroid.ui.simulate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public abstract class SimulateActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.txtvTitle) TextView txtvTitle;
    @BindView(R.id.imgvBack) ImageView imgvBack;

    @Override
    protected void initLayout() {
        txtvTitle.setText(getToolbarTitle());
        imgvBack.setOnClickListener(this);
    }

    abstract public String getToolbarTitle();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgvBack:
                finish();
                break;
        }
    }
}
