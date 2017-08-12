package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import io.realm.ImageRealmProxy;
import io.realm.RealmObject;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(implementations = { ImageRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Image.class })
public class Image extends RealmObject{
    Integer id;
    String url;

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
