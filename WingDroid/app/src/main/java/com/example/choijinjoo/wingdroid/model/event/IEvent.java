package com.example.choijinjoo.wingdroid.model.event;

/**
 * Created by choijinjoo on 2017. 8. 9..
 */

public interface IEvent {
    String getRepoAndAuthorName();
    String getMessage();
    String getEventInfoString();
    String getMainUrl();
    int getViewType();
}
