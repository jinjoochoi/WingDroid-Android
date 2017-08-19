package com.example.choijinjoo.wingdroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 18..
 */

@Parcel(value = Parcel.Serialization.BEAN)
@DatabaseTable
public class RTTagRepository {
    public final static String REPO_ID_FIELD_NAME = "repository_id";
    public final static String TAG_ID_FIELD_NAME = "tag_id";


    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, columnName = REPO_ID_FIELD_NAME)
    private Repository repository;
    @DatabaseField(foreign = true, columnName = TAG_ID_FIELD_NAME)
    private Tag tag;

    public RTTagRepository() {}

    public RTTagRepository(Repository repository, Tag tag) {
        this.repository = repository;
        this.tag = tag;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
