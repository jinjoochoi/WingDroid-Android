package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */
@DatabaseTable(tableName = "committer")
@Parcel(value = Parcel.Serialization.BEAN)
public class Committer {
    public static final String DATE_FIELD = "date";
    @DatabaseField(generatedId = true)
    Integer id;
    @DatabaseField
    String name;
    @DatabaseField(columnName = DATE_FIELD)
    Date date;

    public Committer() {}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
