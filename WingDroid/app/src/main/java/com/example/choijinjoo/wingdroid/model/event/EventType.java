package com.example.choijinjoo.wingdroid.model.event;

/**
 * Created by choijinjoo on 2017. 8. 6..
 */

public enum EventType {
    ISSUE(101), PUSH(102), RELEASE(103), NONE(-1);
    public int value;

    EventType(int value) {
        this.value = value;
    }

    public static EventType toEnum(int type){
        switch (type) {
            case 101:
                return ISSUE;
            case 102:
                return PUSH;
            case 103:
                return RELEASE;
            default:
                return NONE;
        }
    }
}
