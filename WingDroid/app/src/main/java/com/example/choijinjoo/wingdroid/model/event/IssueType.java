package com.example.choijinjoo.wingdroid.model.event;

/**
 * Created by choijinjoo on 2017. 8. 6..
 */

public enum IssueType {
    OPEN(1), CLOSE(2), REOPEN(3), NONE(-1);
    public int value;

    IssueType(int value) {
        this.value = value;
    }

    public String toString() {
        switch (value) {
            case 1:
                return "Opened";
            case 2:
                return "Closed";
            case 3:
                return "Reopend";
            default:
                return "";
        }
    }

    public static IssueType toEnum(int type){
        switch (type) {
            case 1:
                return OPEN;
            case 2:
                return CLOSE;
            case 3:
                return REOPEN;
            default:
                return NONE;
        }
    }
}
