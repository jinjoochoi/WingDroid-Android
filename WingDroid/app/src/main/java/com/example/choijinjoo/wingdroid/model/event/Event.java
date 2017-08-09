package com.example.choijinjoo.wingdroid.model.event;

import org.parceler.Parcel;

import static android.R.attr.value;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel
public class Event {
    Integer id;
    Integer type;
    Issue issue;
    Push push;
    Release release;

    public Event() {}

    public Event(Integer type, Push push) {
        this.type = type;
        this.push = push;
    }

    public Event(Integer type, Release release) {
        this.type = type;
        this.release = release;
    }

    public Event(Integer type, Issue issue) {
        this.type = type;
        this.issue = issue;
    }


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

    public Push getPush() { return push; }

    public void setPush(Push push) { this.push = push; }

    public Release getRelease() { return release; }

    public void setRelease(Release release) {
        this.release = release;
    }

    /*
     * Custom Method
     */

    public IEvent getEvent() {
        if (type == EventType.ISSUE.value)
            return issue;
        if (type == EventType.PUSH.value)
            return push;
        if (type == EventType.RELEASE.value)
            return release;
        return null;
    }
}