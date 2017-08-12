package com.example.choijinjoo.wingdroid.tools;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class Utils {
    public static String getElapsedDateString(Date date) {

        Date d1 = Calendar.getInstance().getTime();
        Date d2 = date;

        long diff = d1.getTime() - d2.getTime();

        if (diff / (24 * 60 * 60 * 1000) > 0)
            return String.format("%d days ago", diff / (24 * 60 * 60 * 1000));
        if (diff / (60 * 60 * 1000) % 24 > 0)
            return String.format("%d hours ago", diff / (60 * 60 * 1000) % 24);
        if (diff / (60 * 1000) % 60 > 0)
            return String.format("%d minutes ago", diff / (60 * 1000) % 60);
        if (diff / 1000 % 60 > 0)
            return String.format(" %d seconds ago", diff / 1000 % 60);

        return "";
    }

    public static String getStarString(int star) {
        if (star > 1000 && ((star - (star / 1000) * 1000) / 100) == 0)
            return String.format("%dk", star / 1000);
        else
            return star > 1000 ? String.format("%d.%dk", star / 1000, (star - (star / 1000) * 1000) / 100) : String.valueOf(star);
    }
}
