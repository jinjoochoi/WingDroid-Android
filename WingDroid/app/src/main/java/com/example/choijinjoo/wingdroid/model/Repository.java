package com.example.choijinjoo.wingdroid.model;

import com.example.choijinjoo.wingdroid.model.converter.GifListParcelConverter;
import com.example.choijinjoo.wingdroid.model.converter.ImageListParcelConverter;
import com.example.choijinjoo.wingdroid.model.converter.TagListParcelConverter;
import com.example.choijinjoo.wingdroid.tools.Utils;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RepositoryRealmProxy;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(implementations = { RepositoryRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Repository.class })
public class Repository extends RealmObject{
    Integer id;
    String name;
    String author;
    String git;
    String description;
    Integer watch;
    Integer star;
    Integer fork;
    Integer issue;
    RealmList<Image> images;
    RealmList<Gif> gifs;
    RealmList<Tag> tags;

    public Repository() {}

    public Repository(String name) {
        this.name = name;
    }

    public Repository(String name, RealmList<Gif> gifs, RealmList<Tag> tags, Integer star) {
        this.name = name;
        this.gifs = gifs;
        this.tags = tags;
        this.star = star;
        this.description = "this is description";
    }

    public Repository(String name, RealmList<Gif> gifs, Integer star) {
        this.name = name;
        this.gifs = gifs;
        this.star = star;
        this.description = "this is description";
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

    public RealmList<Image> getImages() {
        return images;
    }

    @ParcelPropertyConverter(ImageListParcelConverter.class)
    public void setImages(RealmList<Image> images) {
        this.images = images;
    }

    public RealmList<Gif> getGifs() {
        return gifs;
    }

    @ParcelPropertyConverter(GifListParcelConverter.class)
    public void setGifs(RealmList<Gif> gifs) {
        this.gifs = gifs;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    @ParcelPropertyConverter(TagListParcelConverter.class)
    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

    /*
     * Custom method
     */

    public String getFormattedStarString() {
        return Utils.getStarString(star);
    }
}
