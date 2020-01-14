package com.example.resolutionsdiary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Dates {
    public static Date getYesterdayMidnight() {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.DAY_OF_MONTH, calStart.get(Calendar.DAY_OF_MONTH) -1 );
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 1);
        Date midnightYesterday = calStart.getTime();

        return midnightYesterday;

    }
    public static Date getTomdayMidnight() {
        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(new Date());
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        Date midnightTonight = calEnd.getTime();
        return midnightTonight;
    }
    public static String dateDebug_toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("kk:mm - dd.MM");
        return dateFormat.format(date);
    }
    public static String date_toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM");
        return dateFormat.format(date);
    }
}
