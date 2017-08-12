package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Utils;

import org.parceler.Parcel;

import java.util.Calendar;
import java.util.Date;

import io.realm.PushRealmProxy;
import io.realm.RealmObject;

import static com.example.choijinjoo.wingdroid.ui.news.EventAdapter.EVENT_PUSH;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */

@Parcel(implementations = { PushRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Push.class })
public class Push extends RealmObject implements IEvent {
    User pusher;
    Commit headCommit;
    Repository repository;
    String compare;
    Date createdAt;

    public Push() {
        this.pusher = new User("jinjoo");
        this.repository = new Repository("TextSurface");
        this.headCommit = new Commit();
        this.createdAt = Calendar.getInstance().getTime();
    }

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
        return repository.getName() + " / " + pusher.getName();
    }

    @Override
    public String getMessage() {
        return headCommit.getMessage();
    }

    @Override
    public String getEventInfoString() {
        return  "Pushed by " + pusher.getName() + Utils.getElapsedDateString(createdAt);
    }

    //FIXME MOCK DATA
    @Override
    public String getMainUrl() {
        return "https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type";
    }

    @Override
    public int getViewType() { return EVENT_PUSH; }
}
