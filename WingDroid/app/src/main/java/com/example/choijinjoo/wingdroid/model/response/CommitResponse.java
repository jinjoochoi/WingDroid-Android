package com.example.choijinjoo.wingdroid.model.response;

import com.example.choijinjoo.wingdroid.model.User;
import com.example.choijinjoo.wingdroid.model.event.Commit;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class CommitResponse {
    Commit commit;
    User author;

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
