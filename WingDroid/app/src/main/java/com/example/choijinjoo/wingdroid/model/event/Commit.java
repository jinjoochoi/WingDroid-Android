package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.User;

import org.parceler.Parcel;

import io.realm.CommitRealmProxy;
import io.realm.RealmObject;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */

@Parcel(implementations = { CommitRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Commit.class })
public class Commit extends RealmObject{
    String id;
    String message;
    String timestamp;
    String url;
    User commiter;

    public Commit() {
        this.message = "this is Commit message";
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public User getCommiter() { return commiter; }

    public void setCommiter(User commiter) { this.commiter = commiter; }
}
