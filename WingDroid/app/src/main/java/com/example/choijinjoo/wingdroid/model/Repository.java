package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel
public class Repository {
    Integer id;
    String name;
    String author;
    String git;
    String description;
    Integer watch;
    Integer star;
    Integer fork;
    Integer issue;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWatch() {
        return watch;
    }

    public void setWatch(Integer watch) {
        this.watch = watch;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getFork() {
        return fork;
    }

    public void setFork(Integer fork) {
        this.fork = fork;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }
}
