package de.prttstft.materialmensa.extras;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import de.prttstft.materialmensa.R;

import static de.prttstft.materialmensa.constants.GeneralConstants.TODAY_DE;
import static de.prttstft.materialmensa.constants.GeneralConstants.TODAY_EN;
import static de.prttstft.materialmensa.constants.GeneralConstants.TOMORROW_DE;
import static de.prttstft.materialmensa.constants.GeneralConstants.TOMORROW_EN;

public class DateTimeUtilities {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String LOCALE_DE = "Deutsch";
    private static final String ON = "on ";

    private DateTimeUtilities() {

    }


    private static boolean isToday(DateTime dateTime) {
        LocalDate today = new LocalDate();
        return today.equals(dateTime.toLocalDate());
    }

    private static boolean isTomorrow(DateTime dateTime) {
        LocalDate tomorrow = new LocalDate().plusDays(1);
        return tomorrow.equals(dateTime.toLocalDate());
    }

    public static int getDayOfWeekInt(int page) {
        DateTime dateTime = DateTime.parse(getDateString(page));
        return dateTime.getDayOfWeek();
    }

    public static String getDateString(int page) {
        return new DateTime().plusDays(page).toString(DateTimeUtilities.DATE_PATTERN);
    }

    public static String getDayStringLong(int page) {
        String language = Utilities.getSystemLanguage();
        String outputString;
        DateTime dateTime = DateTime.parse(getDateString(page));

        if (isToday(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TODAY_DE;
            } else {
                outputString = TODAY_EN;
            }
        } else if (isTomorrow(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TOMORROW_DE;
            } else {
                outputString = TOMORROW_EN;
            }
        } else {
            outputString = dateTime.dayOfWeek().getAsText();
        }

        return outputString;
    }

    public static String getShareDayString(String dateString) {
        String language = Utilities.getSystemLanguage();
        String outputString;
        DateTime dateTime = DateTime.parse(dateString);

        if (isToday(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TODAY_DE;
            } else {
                outputString = TODAY_EN;
            }
        } else if (isTomorrow(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TOMORROW_DE;
            } else {
                outputString = TOMORROW_EN;
            }
        } else {
            if (language.equals(LOCALE_DE)) {
                outputString = dateTime.dayOfWeek().getAsText();
            } else {
                outputString = "on " + dateTime.dayOfWeek().getAsText();
            }
        }

        return outputString;
    }

    public static String getShareDayString(int page) {
        String language = Utilities.getSystemLanguage();
        String outputString;
        DateTime dateTime = DateTime.parse(getDateString(page));

        if (isToday(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TODAY_DE;
            } else {
                outputString = TODAY_EN;
            }
        } else if (isTomorrow(dateTime)) {
            if (language.equals(LOCALE_DE)) {
                outputString = TOMORROW_DE;
            } else {
                outputString = TOMORROW_EN;
            }
        } else {
            if (language.equals(LOCALE_DE)) {
                outputString = dateTime.dayOfWeek().getAsText();
            } else {
                outputString = ON + dateTime.dayOfWeek().getAsText();
            }
        }

        return outputString;
    }

    public static int getDateIcon(int page) {
        String dateString = getDateString(page);
        int dayString = Integer.valueOf(dateString.substring(8));

        switch (dayString) {
            case 1:
                return R.drawable.ic_calendar_black_1;
            case 2:
                return R.drawable.ic_calendar_black_2;
            case 3:
                return R.drawable.ic_calendar_black_3;
            case 4:
                return R.drawable.ic_calendar_black_4;
            case 5:
                return R.drawable.ic_calendar_black_5;
            case 6:
                return R.drawable.ic_calendar_black_6;
            case 7:
                return R.drawable.ic_calendar_black_7;
            case 8:
                return R.drawable.ic_calendar_black_8;
            case 9:
                return R.drawable.ic_calendar_black_9;
            case 10:
                return R.drawable.ic_calendar_black_10;
            case 11:
                return R.drawable.ic_calendar_black_11;
            case 12:
                return R.drawable.ic_calendar_black_12;
            case 13:
                return R.drawable.ic_calendar_black_13;
            case 14:
                return R.drawable.ic_calendar_black_14;
            case 15:
                return R.drawable.ic_calendar_black_15;
            case 16:
                return R.drawable.ic_calendar_black_16;
            case 17:
                return R.drawable.ic_calendar_black_17;
            case 18:
                return R.drawable.ic_calendar_black_18;
            case 19:
                return R.drawable.ic_calendar_black_19;
            case 20:
                return R.drawable.ic_calendar_black_20;
            case 21:
                return R.drawable.ic_calendar_black_21;
            case 22:
                return R.drawable.ic_calendar_black_22;
            case 23:
                return R.drawable.ic_calendar_black_23;
            case 24:
                return R.drawable.ic_calendar_black_24;
            case 25:
                return R.drawable.ic_calendar_black_25;
            case 26:
                return R.drawable.ic_calendar_black_26;
            case 27:
                return R.drawable.ic_calendar_black_27;
            case 28:
                return R.drawable.ic_calendar_black_28;
            case 29:
                return R.drawable.ic_calendar_black_29;
            case 30:
                return R.drawable.ic_calendar_black_30;
            case 31:
                return R.drawable.ic_calendar_black_31;
            default:
                return -1;
        }
    }
}