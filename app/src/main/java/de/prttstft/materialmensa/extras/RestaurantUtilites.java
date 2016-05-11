package de.prttstft.materialmensa.extras;

import java.util.Random;

import de.prttstft.materialmensa.MyApplication;
import de.prttstft.materialmensa.R;

import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_HOTSPOT;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ONE_WAY_SNACK;

public class RestaurantUtilites {
    private RestaurantUtilites() {

    }


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

    public static int getRestaurantIcon(int restaurant) {
        switch (restaurant) {
            case RESTAURANT_ID_ACADEMICA:
                return R.drawable.ic_academica;
            case RESTAURANT_ID_FORUM:
                return R.drawable.ic_forum;
            case RESTAURANT_ID_CAFETE:
                return R.drawable.ic_cafete;
            case RESTAURANT_ID_MENSULA:
                return R.drawable.ic_mensula;
            case RESTAURANT_ID_ONE_WAY_SNACK:
                return R.drawable.ic_one_way_snack;
            case RESTAURANT_ID_GRILL_CAFE:
                return R.drawable.ic_grill_cafe;
            case RESTAURANT_ID_HOTSPOT:
                return R.drawable.ic_hotspot;
            default:
                return -1;
        }
    }

    public static String getRestaurantName(int restaurantId) {
        switch (restaurantId) {
            case RESTAURANT_ID_ACADEMICA:
                return MyApplication.getAppContext().getString(R.string.restaurant_mensa_academica_paderborn);
            case RESTAURANT_ID_FORUM:
                return MyApplication.getAppContext().getString(R.string.restaurant_mensa_forum_paderborn);
            case RESTAURANT_ID_CAFETE:
                return MyApplication.getAppContext().getString(R.string.restaurant_cafete);
            case RESTAURANT_ID_MENSULA:
                return MyApplication.getAppContext().getString(R.string.restaurant_mensula);
            case RESTAURANT_ID_ONE_WAY_SNACK:
                return MyApplication.getAppContext().getString(R.string.restaurant_one_way_snack);
            case RESTAURANT_ID_GRILL_CAFE:
                return MyApplication.getAppContext().getString(R.string.restaurant_grill_cafe);
            case RESTAURANT_ID_HOTSPOT:
                return MyApplication.getAppContext().getString(R.string.restaurant_bistro_hotspot);
            default:
                return MyApplication.getAppContext().getString(R.string.restaurant_mensa_academica_paderborn);
        }
    }
}