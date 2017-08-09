package com.example.choijinjoo.wingdroid.model;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.content.res.AppCompatResources;

import com.example.choijinjoo.wingdroid.R;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel
public class Tag {
    Integer id;
    String name;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "#" + name;
    }

    /*
     * Custom method
     */
}
