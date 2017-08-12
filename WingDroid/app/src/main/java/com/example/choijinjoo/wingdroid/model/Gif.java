package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import io.realm.GifRealmProxy;
import io.realm.RealmObject;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(implementations = { GifRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Gif.class })
public class Gif extends RealmObject{
    Integer id;
    String url;

    public Gif() { }

    public Gif(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
