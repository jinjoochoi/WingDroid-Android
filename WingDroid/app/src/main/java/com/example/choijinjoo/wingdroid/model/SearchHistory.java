package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */
@Parcel(value = Parcel.Serialization.BEAN)
@DatabaseTable(tableName = "searchHistory")
public class SearchHistory {
    public final static String ID_FIELD_NAME = "id";
    public final static String SEARCH_TYPE_CATEGORY = "search_type_category";
    public final static String SEARCH_TYPE_TEXT= "search_type_text";

    @DatabaseField(unique = true, columnName = ID_FIELD_NAME, generatedId = true)
    private Integer id;
    @DatabaseField
    private String search;
    @DatabaseField
    private String searchType;

    public SearchHistory() {}

    public SearchHistory(String search, String searchType) {
        this.search = search;
        this.searchType = searchType;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getSearch() { return search; }

    public void setSearch(String search) { this.search = search; }

    public String getSearchType() { return searchType; }

    public void setSearchType(String searchType) { this.searchType = searchType; }
}
