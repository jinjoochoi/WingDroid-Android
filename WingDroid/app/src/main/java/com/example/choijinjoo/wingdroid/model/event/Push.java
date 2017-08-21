package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Utils;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */

@Parcel(value = Parcel.Serialization.BEAN)
public class Push implements IEvent {
    User pusher;
    Commit headCommit;
    Repository repository;
    String compare;
    Date createdAt;

    public Push() {}

    public User getPusher() { return pusher; }

    public void setPusher(User pusher) { this.pusher = pusher; }

    public Commit getHeadCommit() { return headCommit; }

    public void setHeadCommit(Commit headCommit) { this.headCommit = headCommit; }

    public Repository getRepository() { return repository; }

    public void setRepository(Repository repository) { this.repository = repository; }

    public String getCompare() { return compare; }

    public void setCompare(String compare) { this.compare = compare; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    /*
     * IEvent method
     */

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + pusher.getLogin();
    }

    @Override
    public String getMessage() {
        return headCommit.getMessage();
    }

    @Override
    public String getEventInfoString() {
        return  "Pushed by " + pusher.getLogin() + Utils.getElapsedDateString(createdAt);
    }

    //FIXME MOCK DATA
    @Override
    public String getMainUrl() {
        return "https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type";
    }

    @Override
    public int getViewType() { return 0; }
}
