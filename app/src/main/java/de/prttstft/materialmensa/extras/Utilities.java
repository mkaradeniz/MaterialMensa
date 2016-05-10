package de.prttstft.materialmensa.extras;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;

import java.util.Locale;

import de.prttstft.materialmensa.MyApplication;

import static de.prttstft.materialmensa.constants.GeneralConstants.LOGTAG;

public class Utilities {
    private static final String LANGUAGE_DE = "Deutsch";
    private static final String LANGUAGE_DE_SHORT = "de";
    private static final String LANGUAGE_EN_SHORT = "en";


    private Utilities() {

    }


    public static void L(String input) {
        Log.d(LOGTAG, input);
    }

    public static String addLeadingZero(int n, int places) {
        String placesPattern = "%0" + places + "d";
        return String.format(Locale.GERMAN, placesPattern, n);
    }

    public static int convertToPx(int dp) {
        Resources resources = MyApplication.getAppContext().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static String getSystemLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public static String getSystemLanguageShort() {
        if (Locale.getDefault().getDisplayLanguage().equals(LANGUAGE_DE)) {
            return LANGUAGE_DE_SHORT;
        } else {
            return LANGUAGE_EN_SHORT;
        }
    }

    public static boolean onWifi() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }

        return false;
    }
}