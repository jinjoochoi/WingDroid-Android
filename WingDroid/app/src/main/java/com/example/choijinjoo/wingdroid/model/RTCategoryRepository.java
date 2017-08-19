package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 18..
 */

@Parcel(value = Parcel.Serialization.BEAN)
@DatabaseTable
public class RTCategoryRepository {
    public final static String REPO_ID_FIELD_NAME = "repository_id";
    public final static String CATEGORY_ID_FIELD_NAME = "category_id";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, columnName = REPO_ID_FIELD_NAME, foreignAutoRefresh = true)
    private Repository repository;
    @DatabaseField(foreign = true, columnName = CATEGORY_ID_FIELD_NAME, foreignAutoRefresh = true)
    private Category category;

    public RTCategoryRepository() {}

    public RTCategoryRepository(Repository repository, Category category) {
        this.repository = repository;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
