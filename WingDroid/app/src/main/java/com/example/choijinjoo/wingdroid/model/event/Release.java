package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Util;

import org.parceler.Parcel;

import java.util.Calendar;
import java.util.Date;

import static com.example.choijinjoo.wingdroid.ui.news.EventAdapter.EVENT_RELEASE;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */
@Parcel
public class Release implements IEvent {
    Integer id;
    String action;
    String url;
    String tag_name;
    boolean draft;
    User author;
    Date createdAt;
    Date publishedAt;
    Repository repository;
    String body;

    public Release() {
        this.author = new User("jinjoo");
        this.body = "this is release message";
        this.repository = new Repository("awesome text");
        this.createdAt = Calendar.getInstance().getTime();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getTag_name() { return tag_name; }

    public void setTag_name(String tag_name) { this.tag_name = tag_name; }

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

    /*
     * IEvent method
     */

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + author.getName();
    }

    @Override
    public String getMessage() {
        return body;
    }

    @Override
    public String getEventInfoString() {
        return "Released by " + author.getName() + Util.getElapsedDateString(createdAt);
    }

    //FIXME MOCK DATA
    @Override
    public String getMainUrl() {
        return "https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type";
    }

    @Override
    public int getViewType() { return EVENT_RELEASE; }
}
