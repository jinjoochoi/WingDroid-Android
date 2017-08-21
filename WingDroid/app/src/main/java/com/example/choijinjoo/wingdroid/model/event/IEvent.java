package com.example.choijinjoo.wingdroid.model.event;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */

public interface IEvent {
    String getRepoAndAuthorName();
    String getMessage();
    String getEventInfoString();
    String getMainUrl();
    Long getLongTypeDate();
    int getViewType();
    boolean isRead();
    void setRead(boolean read);
}
