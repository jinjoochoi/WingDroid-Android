package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.tools.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import java.util.Date;

import static com.example.choijinjoo.wingdroid.model.event.Event.EVENT_COMMIT;


/**
 * Created by choijinjoo on 2017. 8. 9..
 */
@DatabaseTable(tableName = "commit")
@Parcel(value = Parcel.Serialization.BEAN)
public class Commit implements IEvent{
    public final static String ID_FIELD = "commit_id";
    public final static String TIME_STAMP = "timestamp";
    public final static String DATE_FIELD = "date";

    @DatabaseField(id = true, unique = true, columnName = ID_FIELD)
    String url;
    @DatabaseField
    String message;
    @DatabaseField(columnName = TIME_STAMP)
    String timestamp;
    @DatabaseField(foreign = true)
    Committer committer;
    @DatabaseField(foreign = true)
    User author;
    @DatabaseField
    Date date;

    @DatabaseField(foreign = true)
    Repository repository;

    @DatabaseField
    boolean isRead;



    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public Committer getCommitter() { return committer; }

    public void setCommitter(Committer committer) { this.committer = committer; }

    public Repository getRepository() { return repository; }

    public void setRepository(Repository repository) { this.repository = repository; }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + repository.getAuthor();
    }

    @Override
    public String getEventInfoString() {
        return "Committed by " + committer.getName()+"  " + Utils.getElapsedDateString(committer.getDate());
    }

    @Override
    public String getMainUrl() {
        return url;
    }

    @Override
    public Integer getViewType() {
        return EVENT_COMMIT;
    }

    @Override
    public Long getLongTypeDate() {
        return committer.getDate().getTime();
    }

    @Override
    public Boolean isRead() { return isRead; }

    @Override
    public void setRead(Boolean read) { isRead = read; }

}
