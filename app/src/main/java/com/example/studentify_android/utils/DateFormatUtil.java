package com.example.studentify_android.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static Date getDateFormated(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringForDate(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);

    }

    public static String getStringForDateTime(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return dateFormat.format(date);

    }
}
