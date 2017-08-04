package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel
public class News {
    Integer id;
    Integer type;
    Issue issue;
    Repository repository;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}