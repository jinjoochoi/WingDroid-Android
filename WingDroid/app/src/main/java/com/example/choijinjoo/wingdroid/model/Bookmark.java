package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 21..
 */

@DatabaseTable(tableName = "bookmark")
@Parcel(value = Parcel.Serialization.BEAN)
public class Bookmark {
    public final static String ID_FIELD = "bookmark_id";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD)
    String id;
    @DatabaseField(foreign = true)
    Repository repository;

    public Bookmark() {}

    public Bookmark(Repository repository) {
        this.repository = repository;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
