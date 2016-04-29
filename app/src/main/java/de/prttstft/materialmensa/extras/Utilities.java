package de.prttstft.materialmensa.extras;

import android.util.Log;

import java.util.Locale;

import static de.prttstft.materialmensa.constants.GeneralConstants.LOGTAG;

public class Utilities {

    private Utilities() {

    }

    public static void L(String input) {
        Log.d(LOGTAG, input);
    }

    public static String addLeadingZero(int n, int places) {
        String placesPattern = "%0" + places + "d";
        return String.format(Locale.GERMAN, placesPattern, n);
    }

    public static String getSystemLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }
}