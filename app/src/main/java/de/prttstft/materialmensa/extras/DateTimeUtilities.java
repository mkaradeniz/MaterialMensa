package de.prttstft.materialmensa.extras;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import static de.prttstft.materialmensa.constants.GeneralConstants.TODAY;
import static de.prttstft.materialmensa.constants.GeneralConstants.TOMORROW;

public class DateTimeUtilities {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

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

    // Get date string.
    public static String getDateString(int page) {
        return new DateTime().plusDays(page).toString(DateTimeUtilities.DATE_PATTERN);
    }
}