package com.example.choijinjoo.wingdroid.model;

import com.example.choijinjoo.wingdroid.model.converter.TagListParcelConverter;
import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.tools.Utils;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */
@Parcel(value = Parcel.Serialization.BEAN)
@DatabaseTable(tableName = "repository")
public class Repository {
    public final static String ID_FIELD = "repo_id";
    public final static String NAME_FIELD = "repo_name";
    public final static String BOOKMARK_FIELD = "bookmarkedAt";
    public final static String STAR_FIELD = "star";

    @DatabaseField(unique = true, id = true, columnName = ID_FIELD)
    String id;
    @DatabaseField(columnName = NAME_FIELD)
    String name;
    @DatabaseField
    String author;
    @DatabaseField
    String git;
    @DatabaseField
    String description;
    @DatabaseField
    Integer watch;
    @DatabaseField(columnName = STAR_FIELD)
    Integer star;
    @DatabaseField
    Integer fork;
    @DatabaseField
    Integer issue;
    @DatabaseField
    @SerializedName("issue_url")
    String issueUrl;
    @DatabaseField
    String image;
    @DatabaseField
    Long createdAt;
    @DatabaseField
    Long updatedAt;
    @DatabaseField
    Integer simulatable;
    @DatabaseField
    boolean bookmark;
    @DatabaseField(columnName = BOOKMARK_FIELD)
    Date bookmarkedAt;
    @DatabaseField
    int clicks;

    @ParcelPropertyConverter(TagListParcelConverter.class)
    List<Tag> tags;
    Category category;


    public Repository() {
    }

    public Repository(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
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

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public Integer getSimulatable() {
        return simulatable;
    }

    public void setSimulatable(Integer simulatable) {
        this.simulatable = simulatable;
    }

    public boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public Date getBookmarkedAt() { return bookmarkedAt; }

    public void setBookmarkedAt(Date bookmarkedAt) { this.bookmarkedAt = bookmarkedAt; }

    public int getClicks() { return clicks; }

    public void setClicks(int clicks) { this.clicks = clicks; }

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

    public String getTagString(String tag) {
        return "#" + tag;
    }

    public void clickBookmark() {
        this.bookmark = !this.bookmark;
    }

}
