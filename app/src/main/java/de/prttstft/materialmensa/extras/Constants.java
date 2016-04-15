package de.prttstft.materialmensa.extras;

import de.prttstft.materialmensa.BuildConfig;

public interface Constants {
    // General.
    String LOGTAG = "LOGTAG";
    String TODAY = "Today";
    String TOMORROW = "Tomorrow";

    String ARG_PAGE = "ARG_PAGE";
    String ARG_RESTAURANT = "ARG_RESTAURANT";

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
        String API_PARAM_DATE = "date=";
        String API_PARAM_ID = "id=";

        // Restaurants

        String API_RESTAURANT_ACADEMICA = "mensa-academica-paderborn";
        String API_RESTAURANT_FORUM = "mensa-forum-paderborn";
        String API_RESTAURANT_CAFETE = "cafete";
        String API_RESTAURANT_MENSULA = "mensula";
        String API_RESTAURANT_ONE_WAY_SNACK = "one-way-snack";
        String API_RESTAURANT_GRILL_CAFE = "grill-cafe";
        String API_RESTAURANT_CAMPUS_DOENER = "campus-doener";
        String API_RESTAURANT_HAMM = "mensa-hamm";
        String API_RESTAURANT_LIPPSTADT = "mensa-lippstadt";
        String API_RESTAURANT_BISTRO_HOTSPOT = "bistro-hotspot";

        String API_GET = API_ACCESS + API_QUERY + API_PARAM_ID + API_IDENTITY + API_AMPERSAND + API_PARAM_DATE + API_AMPERSAND + API_PARAM_RESTAURANT;
    }

    interface MealBadgeConstants {
        String MEAL_BADGE_VEGETARIAN = "vegetarian";
        String MEAL_BADGE_VEGAN = "vegan";
        String MEAL_BADGE_LACTOSE_FREE = "lactose-free";
        String MEAL_BADGE_NONFAT = "nonfat";
    }

    interface NavigationDrawerConstants {
        int NAVIGATION_DRAWER_RESTAURANT_ACADEMICA = 0;
        int NAVIGATION_DRAWER_RESTAURANT_FORUM = 1;
        int NAVIGATION_DRAWER_RESTAURANT_CAFETE = 2;
        int NAVIGATION_DRAWER_RESTAURANT_MENSULA = 3;
        int NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK = 4;
        int NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE = 5;
        int NAVIGATION_DRAWER_RESTAURANT_CAMPUS_DOENER = 6;
    }
}