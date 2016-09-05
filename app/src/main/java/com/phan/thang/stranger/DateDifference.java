package com.phan.thang.stranger;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thang on 28.08.2016.
 */
public class DateDifference {

    public static String getTimeLeft(String date){
        String difference = "";

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        String dateTimeNow = today.monthDay + "-" + today.month + "-" + today.year + " " + today.format("%H:%M:%S");

        String dt1 = date;
        String dt2 = dateTimeNow;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date addedDate = format.parse(dt1);
            Date nowDate = format.parse(dt2);

            difference = DateDifference.getDiff(addedDate, nowDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return difference;
    }
    public static String getTimeLeft2(String date){
        String difference = "";

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        String dateTimeNow = today.monthDay + "-" + today.month + "-" + today.year + " " + today.format("%H:%M:%S");

        String dt1 = date;
        String dt2 = dateTimeNow;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date addedDate = format.parse(dt1);
            Date nowDate = format.parse(dt2);

            difference = DateDifference.getDiffFormattedToAddDate(addedDate, nowDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return difference;
    }

    public static String getDiff(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(elapsedHours == 0){
            return elapsedMinutes + " minutes" + " ago";
        }else{
            return elapsedHours + "h " + elapsedMinutes + "minutes" + " ago";
        }
    }
    public static String getDiffFormattedToAddDate(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;


        return elapsedDays + "-" + elapsedHours + "-" + elapsedMinutes;

    }
}
