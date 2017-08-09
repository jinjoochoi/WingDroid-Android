package com.example.choijinjoo.wingdroid.model;

import org.parceler.Parcel;

import java.util.List;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(Parcel.Serialization.BEAN)
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
    List<Image> images;
    List<Gif> gifs;
    List<Tag> tags;

    public Repository() {}

    public Repository(String name) {
        this.name = name;
    }

    public Repository(String name, List<Gif> gifs, List<Tag> tags, Integer star) {
        this.name = name;
        this.gifs = gifs;
        this.tags = tags;
        this.star = star;
    }

    public Repository(String name, List<Gif> gifs, Integer star) {
        this.name = name;
        this.gifs = gifs;
        this.tags = tags;
        this.star = star;
    }

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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Gif> getGifs() {
        return gifs;
    }

    public void setGifs(List<Gif> gifs) {
        this.gifs = gifs;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /*
     * Custom method
     */

    public String getFormattedStarString() {
        return star > 1000 ? String.format("%d.%dk", star / 1000, (star - (star / 1000) * 1000) / 100) : String.valueOf(star);
    }
}
