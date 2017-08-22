package com.example.choijinjoo.wingdroid.model.event;

import org.parceler.Parcel;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Event {
    Integer id;
    Integer type;
    Release release;
    Commit commit;

    public Event() {}


    public Event(Commit commit) {
        this.commit = commit;
    }

    public Event(Release release) {
        this.release = release;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        if (release != null)
            return EVENT_RELEASE;
        else
            return EVENT_COMMIT;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    /*
     * Custom Method
     */

    public IEvent getEvent() {
        if (commit != null)
            return commit;
        if (release != null)
            return release;
        return null;
    }

    public static final int EVENT_ALL = 100;
    public static final int EVENT_RELEASE = 103;
    public static final int EVENT_COMMIT = 104;
}