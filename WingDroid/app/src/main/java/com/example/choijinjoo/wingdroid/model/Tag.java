package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.TagRealmProxy;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(implementations = {TagRealmProxy.class})
public class Tag extends RealmObject{
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
