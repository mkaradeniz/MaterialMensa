package de.prttstft.materialmensa.extras;

import de.prttstft.materialmensa.BuildConfig;

public interface Constants {
    // General.
    String LOGTAG = "LOGTAG";

    // Date
    String TODAY = "Today";
    String TOMORROW = "Tomorrow";

    String USER_TYPE_STUDENT = "user_type_student";
    String USER_TYPE_STAFF = "user_type_staff";
    String USER_TYPE_GUEST = "user_type_guest";
    String PRICE_TYPE_FIXED = "fixed";
    String PRICE_TYPE_WEIGHTED = "weighted";

    interface APIConstants {
        // General

        String API_BASE_URL = "http://www.studentenwerk-pb.de/fileadmin/shareddata/";
        String API_ACCESS = "access2.php";
        String API_QUERY = "?";
        String API_IDENTITY = BuildConfig.API_KEY;
        String API_AMPERSAND = "&";
        String API_PARAM_RESTAURANT = "restaurant=";
        String API_PARAM_OPENING_STATUS = "getopeningstatus=1";
        String API_PARAM_DATE = "date=";
        String API_PARAM_ID = "id=";

        // Restaurants

        String API_RESTAURANT_ACADEMICA = "mensa-academica-paderborn";
        String API_RESTAURANT_BISTRO_HOTSPOT = "bistro-hotspot";
        String API_RESTAURANT_CAFETE = "cafete";
        String API_RESTAURANT_CAMPUS_DOENER = "campus-doener";
        String API_RESTAURANT_FORUM = "mensa-forum-paderborn";
        String API_RESTAURANT_GRILL_CAFE = "grill-cafe";
        String API_RESTAURANT_HAMM = "mensa-hamm";
        String API_RESTAURANT_LIPPSTADT = "mensa-lippstadt";
        String API_RESTAURANT_MENSULA = "mensula";
        String API_RESTAURANT_ONE_WAY_SNACK = "one-way-snack";

        String API_GET_MEALS = API_ACCESS + API_QUERY + API_PARAM_ID + API_IDENTITY + API_AMPERSAND + API_PARAM_DATE + API_AMPERSAND + API_PARAM_RESTAURANT;
        String API_GET_RESTAURANT_STATUS = API_ACCESS + API_QUERY + API_PARAM_ID + API_IDENTITY + API_AMPERSAND + API_PARAM_OPENING_STATUS + API_AMPERSAND + API_PARAM_RESTAURANT;
    }

    interface MealBadgeConstants {
        String MEAL_BADGE_LACTOSE_FREE = "lactose-free";
        String MEAL_BADGE_LOW_CALORIE = "low-calorie";
        String MEAL_BADGE_NONFAT = "nonfat";
        String MEAL_BADGE_VEGAN = "vegan";
        String MEAL_BADGE_VEGETARIAN = "vegetarian";
        String MEAL_BADGE_VITAL_FOOD = "vital-food";
    }

    interface RestaurantIdConstants {
        int RESTAURANT_ID_ACADEMICA = 0;
        int RESTAURANT_ID_FORUM = 1;
        int RESTAURANT_ID_CAFETE = 2;
        int RESTAURANT_ID_MENSULA = 3;
        int RESTAURANT_ID_ONE_WAY_SNACK = 4;
        int RESTAURANT_ID_GRILL_CAFE = 5;
        int RESTAURANT_ID_CAMPUS_DOENER = 6;
        int RESTAURANT_ID_HAMM = 7;
        int RESTAURANT_ID_LIPPSTADT = 8;
        int RESTAURANT_ID_HOTSPOT = 9;
    }

    interface PreferencesConstants {
        String PREFERENCE_VALUE_LIFESTYLE_NOT_SET = "not_set";
        String PREFERENCE_VALUE_LIFESTYLE_VEGETARIAN = "vegetarian";
        String PREFERENCE_VALUE_LIFESTYLE_VEGAN = "vegan";
    }

    interface RestaurantStatusConstants {
        String RESTAURANT_STATUS_CLOSED = "Currently closed";
        String RESTAURANT_STATUS_STARTS_WITH_CLOSES = "Closes";
        String RESTAURANT_STATUS_STARTS_WITH_OPENS = "Opens at";
        String RESTAURANT_STATUS_CLOSED_DE = "Zurzeit";
        String RESTAURANT_STATUS_STARTS_WITH_CLOSES_DE = "Closes";
        String RESTAURANT_STATUS_STARTS_WITH_OPENS_DE = "Geöffnet";
    }
}