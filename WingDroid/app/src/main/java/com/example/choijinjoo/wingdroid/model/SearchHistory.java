package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class SearchHistory {
    private String search;
    private Date insertedAt;

    public SearchHistory() {}

    public SearchHistory(String search, Date insertedAt) {
        this.search = search;
        this.insertedAt = insertedAt;
    }

    public String getSearch() { return search; }

    public void setSearch(String search) { this.search = search; }

    public Date getInsertedAt() { return insertedAt; }

    public void setInsertedAt(Date insertedAt) { this.insertedAt = insertedAt; }
}
