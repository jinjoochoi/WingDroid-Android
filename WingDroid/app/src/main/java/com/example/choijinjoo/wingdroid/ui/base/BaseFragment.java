package com.example.choijinjoo.wingdroid.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.choijinjoo.wingdroid.dao.BaseRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();

    protected abstract void initLayout();

    protected void loadData(){}

    protected List<BaseRepository> repositories = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().setBackgroundColor(Color.WHITE);
        ButterKnife.bind(this, view);
        initLayout();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(BaseRepository repository : repositories){
            repository.release();
        }
    }
}
