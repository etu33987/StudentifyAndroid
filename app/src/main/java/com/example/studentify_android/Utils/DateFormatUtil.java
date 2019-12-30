package com.example.studentify_android.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateFormatUtil {

    public static Date getDateFormated(String dateString) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date myDate = df.parse(dateString);
            return myDate;
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
