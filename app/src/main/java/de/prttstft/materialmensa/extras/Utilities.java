package de.prttstft.materialmensa.extras;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import static de.prttstft.materialmensa.extras.Constants.LOGTAG;
import static de.prttstft.materialmensa.extras.Constants.TODAY;
import static de.prttstft.materialmensa.extras.Constants.YESTERDAY;

public class Utilities {

    // Easy Logging. Only when debugging.
    public static void L(String input) {
        Log.d(LOGTAG, input);
    }

    // Short Toast.
    public static void t(Context context, String input) {
        Toast.makeText(context, input, Toast.LENGTH_SHORT).show();
    }

    // Long Toast.
    public static void T(Context context, String input) {
        Toast.makeText(context, input, Toast.LENGTH_LONG).show();
    }

    // Format long to system time format.
    private static String longToTime(Context context, long input) {
        Format dateFormat = android.text.format.DateFormat.getTimeFormat(context);
        String pattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
        Date time = new Date(input);
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(time);
    }

    // Format long to system date format.
    private static String longToDate(Context context, long input) {
        Format dateFormat = android.text.format.DateFormat.getDateFormat(context);
        String pattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
        Date date = new Date(input);
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    // Check whether date is today.
    private static boolean isToday(DateTime dateTime) {
        LocalDate today = new LocalDate();
        return today.equals(dateTime.toLocalDate());
    }

    // Check whether date was yesterday.
    private static boolean isYesterday(DateTime dateTime) {
        LocalDate yesterday = new LocalDate().minusDays(1);
        return yesterday.equals(dateTime.toLocalDate());
    }

    // Get day of week if date is not today nor yesterday.
    private static String getDayString(DateTime dateTime) {
        String outputString;
        if (isToday(dateTime)) {
            outputString = TODAY;
        } else if (isYesterday(dateTime)) {
            outputString = YESTERDAY;
        } else {
            outputString = dateTime.dayOfWeek().getAsShortText();
        }

        return outputString;
    }

    // Get smart date string (today, yesterday, weekday, absolute date).
    public static String getSmartDateString(Context context, long date) {
        DateTime dateTime = new DateTime(date);
        String outputString;

        DateTime now = new DateTime();
        int days = Days.daysBetween(dateTime, now).getDays();

        if (isToday(dateTime)) {
            outputString = longToTime(context, date);
        } else if (days < 7) {
            outputString = getDayString(dateTime);
        } else {
            outputString = longToDate(context, date);
        }

        return outputString;
    }
}
