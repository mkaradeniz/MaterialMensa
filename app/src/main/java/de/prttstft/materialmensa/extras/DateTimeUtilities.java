package de.prttstft.materialmensa.extras;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

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

    // Check whether date is today.
    private static boolean isToday(DateTime dateTime) {
        LocalDate today = new LocalDate();
        return today.equals(dateTime.toLocalDate());
    }

    // Check whether date is tomorrow.
    private static boolean isTomorrow(DateTime dateTime) {
        LocalDate tomorrow = new LocalDate().plusDays(1);
        return tomorrow.equals(dateTime.toLocalDate());
    }

    // Get day of week if date is not today nor tomorrow.
    public static String getDayString(DateTime dateTime) {
        String language = Utilities.getSystemLanguage();
        String outputString;

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
            outputString = dateTime.dayOfWeek().getAsShortText();
        }

        return outputString;
    }

    // Get date string.
    public static String getDateString(int page) {
        return new DateTime().plusDays(page).toString(DateTimeUtilities.DATE_PATTERN);
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
                outputString = "on " + dateTime.dayOfWeek().getAsText();
            }
        }

        return outputString;
    }
}

/*
String day = DateTimeUtilities.getDateString(getArguments().getInt(ARG_PAGE));

        L(DateTimeUtilities.getDayString(DateTime.parse(day)));
 */