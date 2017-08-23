package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Utils;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import java.util.Date;

import static com.example.choijinjoo.wingdroid.model.event.Event.EVENT_RELEASE;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */
@DatabaseTable(tableName = "release")
@Parcel(value = Parcel.Serialization.BEAN)
public class Release implements IEvent {
    public final static String ID_FIELD = "release_id";
    public final static String UPDATEDAT_FIELD = "updated_at_id";
    public final static String PUBLISHEAT_FIELD = "published_at_id";

    @SerializedName("html_url")
    @DatabaseField(id = true, unique = true, columnName = ID_FIELD)
    Integer id;
    @DatabaseField
    String url;
    @SerializedName("tag_name")
    @DatabaseField
    String tagName;
    @DatabaseField
    boolean draft;
    @DatabaseField(foreign = true)
    User author;
    @SerializedName("created_at")
    @DatabaseField
    Date createdAt;
    @SerializedName("updated_at")
    @DatabaseField(columnName = UPDATEDAT_FIELD)
    Date updatedAt;
    @SerializedName("published_at")
    @DatabaseField
    Date publishedAt;
    @DatabaseField
    String body;
    @DatabaseField
    boolean isRead;


    @DatabaseField(foreign = true)
    Repository repository;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getTagName() { return tagName; }

    public void setTagName(String tagName) { this.tagName = tagName; }

    public boolean isDraft() { return draft; }

    public void setDraft(boolean draft) { this.draft = draft; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt ; }

    public Date getPublishedAt() { return publishedAt; }

    public void setPublishedAt(Date publishedAt) { this.publishedAt = publishedAt; }

    public Repository getRepository() { return repository; }

    public void setRepository(Repository repository) { this.repository = repository; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    public Date getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }


    /*
     * IEvent method
     */

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + author.getLogin() == null? "" : author.getLogin();
    }

    @Override
    public String getMessage() {
        return body;
    }

    @Override
    public String getEventInfoString() {
        return "Released by " + author.getLogin() + "  "+Utils.getElapsedDateString(createdAt);
    }

    @Override
    public String getMainUrl() {
        return url;
    }

    @Override
    public Integer getViewType() { return EVENT_RELEASE; }

    @Override
    public Long getLongTypeDate() {
        return updatedAt.getTime();
    }

    @Override
    public Boolean isRead() { return isRead; }

    @Override
    public void setRead(Boolean read) { isRead = read; }

}
