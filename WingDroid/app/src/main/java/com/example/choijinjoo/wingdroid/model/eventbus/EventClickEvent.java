package com.example.choijinjoo.wingdroid.model.eventbus;

import com.example.choijinjoo.wingdroid.model.event.Event;

/**
 * Created by choijinjoo on 2017. 8. 22..
 */

public class EventClickEvent {
    Event event;
    int position;

    public EventClickEvent(Event event, int position) {
        this.event = event;
        this.position = position;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
