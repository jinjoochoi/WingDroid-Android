package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */
@Parcel(value = Parcel.Serialization.BEAN)
public class SearchHistory {
    private String id;
    private String search;
    private Date insertedAt;
    private Category category;
    private String type;

    public SearchHistory() {}

    public SearchHistory(String search, Date insertedAt, String type) {
        this.search = search;
        this.insertedAt = insertedAt;
        this.type = type;
    }

    public SearchHistory(Category category, Date insertedAt, String type) {
        this.category = category;
        this.insertedAt = insertedAt;
        this.type = type;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSearch() { return search; }

    public void setSearch(String search) { this.search = search; }

    public Date getInsertedAt() { return insertedAt; }

    public void setInsertedAt(Date insertedAt) { this.insertedAt = insertedAt; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }
}
