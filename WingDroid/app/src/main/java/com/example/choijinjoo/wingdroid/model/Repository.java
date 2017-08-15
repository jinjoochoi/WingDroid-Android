package com.example.choijinjoo.wingdroid.model;

import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.tools.Utils;

import org.parceler.Parcel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Repository {
    String id;
    String name;
    String author;
    String git;
    String description;
    Integer watch;
    Integer star;
    Integer fork;
    Integer issue;
    String image;
    List<String> tags;
    Long createdAt;
    Long updatedAt;


    public Repository() {}

    public Repository(String name) {
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

    public String getImage() {
        return image;
    }

    public void setImages(String image) {
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    /*
     * Custom method
     */

    public String getFormattedStarString() {
        return Utils.getStarString(Long.valueOf(star));
    }

    public Date getCreatedAtDate() {
        Timestamp stamp = new Timestamp(createdAt);
        return new Date(stamp.getTime());
    }

    public Date getUpdatedAtDate() {
        Timestamp stamp = new Timestamp(updatedAt);
        return new Date(stamp.getTime());
    }

    public String getCreatedAtDateFormattedString() {
        Timestamp stamp = new Timestamp(createdAt);

        return StringUtils.toFormattedDateString(new Date(stamp.getTime()));
    }

    public String getUpdatedAtDateFormattedString() {
        Timestamp stamp = new Timestamp(updatedAt);
        return StringUtils.toFormattedDateString(new Date(stamp.getTime()));
    }

    public String getTagString(String tag){
        return "#" + tag;
    }

}
