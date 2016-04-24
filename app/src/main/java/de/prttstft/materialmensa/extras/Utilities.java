package de.prttstft.materialmensa.extras;

import android.util.Log;

import java.util.Locale;
import java.util.Random;

import de.prttstft.materialmensa.R;

import static de.prttstft.materialmensa.constants.GeneralConstants.LOGTAG;

public class Utilities {

    private Utilities() {

    }

    // Easy Logging. Only when debugging.
    public static void L(String input) {
        Log.d(LOGTAG, input);
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