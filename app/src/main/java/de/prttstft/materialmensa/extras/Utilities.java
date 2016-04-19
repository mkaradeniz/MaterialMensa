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
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;

import static de.prttstft.materialmensa.extras.Constants.LOGTAG;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_LACTOSE_FREE;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_NONFAT;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGAN;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGETARIAN;
import static de.prttstft.materialmensa.extras.Constants.TODAY;
import static de.prttstft.materialmensa.extras.Constants.TOMORROW;

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

    // Check whether date was yesterday.
    private static boolean isTomorrow(DateTime dateTime) {
        LocalDate tomorrow = new LocalDate().plusDays(1);
        return tomorrow.equals(dateTime.toLocalDate());
    }

    // Get day of week if date is not today nor tomorrow.
    public static String getDayString(DateTime dateTime) {
        String outputString;
        if (isToday(dateTime)) {
            outputString = TODAY;
        } else if (isTomorrow(dateTime)) {
            outputString = TOMORROW;
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

    // Get the badge resource id.
    public static Integer getBadge(Meal meal) {
        List<String> badges = meal.getBadges();
        if (badges.size() > 0) {
            switch (badges.get(0)) {
                case MEAL_BADGE_VEGETARIAN:
                    return R.drawable.ic_vegetarian;
                case MEAL_BADGE_VEGAN:
                    return R.drawable.ic_vegan;
                case MEAL_BADGE_NONFAT:
                    return R.drawable.ic_low_calorie;
                case MEAL_BADGE_LACTOSE_FREE:
                    return R.drawable.ic_lactose_free;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    // Get a random emoji resource id.
    public static int getRandomEmoji() {
        Random random = new Random();
        int index = random.nextInt(6);

        switch (index) {
            case 0:
                return R.drawable.emoji_1f622;
            case 1:
                return R.drawable.emoji_1f625;
            case 2:
                return R.drawable.emoji_1f627;
            case 3:
                return R.drawable.emoji_1f62d;
            case 4:
                return R.drawable.emoji_1f630;
            case 5:
                return R.drawable.emoji_1f64a;
            default:
                return R.drawable.emoji_1f622;
        }
    }

    // Get leading Zero
    public static String addLeadingZero(int n, int places) {
        String placesPattern = "%0" + places + "d";
        return String.format(Locale.GERMAN, placesPattern, n);
    }
}