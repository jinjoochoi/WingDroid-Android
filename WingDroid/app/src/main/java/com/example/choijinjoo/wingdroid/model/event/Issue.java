package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Utils;

import org.parceler.Parcel;

import java.util.Calendar;
import java.util.Date;

import static com.example.choijinjoo.wingdroid.ui.news.EventAdapter.EVENT_ISSUE;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Issue implements IEvent {
    Integer id;
    String action;
    User sender;
    String title;
    String body;
    String htmlUrl;
    String state;
    String label;
    Integer comments;
    Integer number;
    Repository repository;
    String repoName;
    Date createdAt;
    Date updatedAt;

    public Issue() {}

    public Issue(String action, String title, String body) {
        this.action = action;
        this.title = title;
        this.body = body;
        this.repoName = "TextSurface";
        this.createdAt = Calendar.getInstance().getTime();
        this.number = 99;
        this.sender = new User("jinjoo");
        this.repository = new Repository("TextSurface");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action;}

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Repository getRepository() { return repository; }

    public void setRepository(Repository repository) { this.repository = repository; }

    public String getRepoName() { return repoName; }

    public void setRepoName(String repoName) { this.repoName = repoName; }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /*
     * IEvent method
     */

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + sender.getName();
    }

    @Override
    public String getMessage() {
        return body;
    }

    @Override
    public String getEventInfoString() {
        return state + " by " + sender.getName() + Utils.getElapsedDateString(createdAt);
    }

    @Override
    public int getViewType() { return EVENT_ISSUE; }

    //FIXME MOCK DATA
    @Override
    public String getMainUrl() {
        return "https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type";
    }

    public String getIssueNumber(){
        return String.format("#%d", number);
    }
}
