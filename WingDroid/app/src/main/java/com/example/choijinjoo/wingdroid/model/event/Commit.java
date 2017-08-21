package com.example.choijinjoo.wingdroid.model.event;

import com.example.choijinjoo.wingdroid.model.Committer;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import static com.example.choijinjoo.wingdroid.ui.news.EventAdapter.EVENT_COMMIT;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */
@DatabaseTable(tableName = "commit")
@Parcel(value = Parcel.Serialization.BEAN)
public class Commit implements IEvent{
    public final static String ID_FIELD = "commit_id";
    public final static String TIME_STAMP = "timestamp";

    @DatabaseField(id = true, unique = true, columnName = ID_FIELD)
    String id;
    @DatabaseField
    String message;
    @DatabaseField(columnName = TIME_STAMP)
    String timestamp;
    @DatabaseField
    String url;
    @DatabaseField(foreign = true)
    Committer committer;
    @DatabaseField(foreign = true)
    User author;


    @DatabaseField(foreign = true)
    Repository repository;


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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

    @Override
    public String getRepoAndAuthorName() {
        return repository.getName() + " / " + repository.getAuthor();
    }

    @Override
    public String getEventInfoString() {
        return "Committed by " + committer.getName()+" " + committer.getDate();
    }

    @Override
    public String getMainUrl() {
        return url;
    }

    @Override
    public int getViewType() {
        return EVENT_COMMIT;
    }
}
