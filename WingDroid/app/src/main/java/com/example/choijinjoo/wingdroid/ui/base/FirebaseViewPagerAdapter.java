package com.example.choijinjoo.wingdroid.ui.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.choijinjoo.wingdroid.model.FBModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choijinjoo on 2017. 8. 15..
 */

public abstract class FirebaseViewPagerAdapter<T extends FBModel> extends FragmentStatePagerAdapter {
    Query ref;
    Class<T> clazz;
    protected List<T> datas;
    ChildEventListener childEventListener;

    public FirebaseViewPagerAdapter(FragmentManager fm, Query ref, Class<T> clazz){
        super(fm);
        this.ref = ref;
        this.clazz = clazz;
        this.datas = new ArrayList<>();
        init();
    }

    private void init(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                T data = dataSnapshot.getValue(clazz);
                data.setId(dataSnapshot.getKey());
                datas.add(data);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public void removeChildEventListener(){
        ref.removeEventListener(childEventListener);
    }

    public void addChildEventListener(){
        ref.addChildEventListener(childEventListener);
    }


    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }
}
