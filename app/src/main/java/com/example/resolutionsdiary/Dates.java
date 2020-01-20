package com.example.resolutionsdiary;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Dates {
    public static long getYesterdayMidnight() {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.DAY_OF_MONTH, calStart.get(Calendar.DAY_OF_MONTH) -1 );
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 1);


        return calStart.getTimeInMillis();

    }
    public static long getTomdayMidnight() {
        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(new Date());
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        return calEnd.getTimeInMillis();
    }
    public static long getMonthEnd(int month) {
        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(new Date());
        calEnd.set(Calendar.MONTH,month);
        calEnd.set(Calendar.DAY_OF_MONTH,1);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        return calEnd.getTimeInMillis();
    }
    public static long getMonthStart(int month) {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(new Date());
        calStart.set(Calendar.MONTH,month-1);
        calStart.set(Calendar.DAY_OF_MONTH,1);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        return calStart.getTimeInMillis();
    }
    public static int getMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        return Integer.valueOf(dateFormat.format(new Date()));
    }
    public static String monthToString(int month, Context context) {
        Log.e(TAG, "monthToString: " + month );
        return context.getResources().getStringArray(R.array.months)[month -1];
    }
    public static String dateDebug_toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("kk:mm - dd.MM");
        return dateFormat.format(date);
    }
    public static String date_toString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM");
        return dateFormat.format(date);
    }
    public static boolean isToday(long date) {
        return date > getYesterdayMidnight() && date < getTomdayMidnight();
    }
}
