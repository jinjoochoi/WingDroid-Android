package com.example.choijinjoo.wingdroid.model.converter;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
    @Override
    public RealmList<T> createCollection() {
        return new RealmList<T>();
    }
}