package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */
@DatabaseTable(tableName = "tag")
@Parcel(value = Parcel.Serialization.BEAN)
public class Tag {
    public final static String ID_FIELD_NAME = "id";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD_NAME)
    String id;
    @DatabaseField
    String name;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
