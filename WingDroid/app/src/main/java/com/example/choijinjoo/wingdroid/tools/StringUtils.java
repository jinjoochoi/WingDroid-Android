package com.example.choijinjoo.wingdroid.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by choijinjoo on 2017. 8. 10..
 */

public class StringUtils {
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static String  toFormattedDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yy.MM.dd");
        return formatter.format(date).toString();
    }


    public static Long getLongFromFormattedDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date d = format.parse(date);
            return d.getTime();
        } catch (ParseException p) {

        }
        return (long)0;
    }

}
