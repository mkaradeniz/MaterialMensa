package de.prttstft.materialmensa.constants;

import de.prttstft.materialmensa.BuildConfig;

public class APIConstants {

    // General
    public static final String API_ACCESS = "access2.php";
    public static final String API_BASE_URL = "http://www.studentenwerk-pb.de/fileadmin/shareddata/";
    public static final String API_IDENTITY = BuildConfig.API_KEY;

    public static final String API_AMPERSAND = "&";
    public static final String API_QUERY = "?";

    public static final String API_PARAM_DATE = "date=";
    public static final String API_PARAM_ID = "id=";
    public static final String API_PARAM_OPENING_STATUS = "getopeningstatus=1";
    public static final String API_PARAM_RESTAURANT = "restaurant=";


    // Restaurants
    public static final String API_RESTAURANT_ACADEMICA = "mensa-academica-paderborn";
    public static final String API_RESTAURANT_BISTRO_HOTSPOT = "bistro-hotspot";
    public static final String API_RESTAURANT_CAFETE = "cafete";
    public static final String API_RESTAURANT_CAMPUS_DOENER = "campus-doener";
    public static final String API_RESTAURANT_FORUM = "mensa-forum-paderborn";
    public static final String API_RESTAURANT_GRILL_CAFE = "grill-cafe";
    public static final String API_RESTAURANT_HAMM = "mensa-hamm";
    public static final String API_RESTAURANT_LIPPSTADT = "mensa-lippstadt";
    public static final String API_RESTAURANT_MENSULA = "mensula";
    public static final String API_RESTAURANT_ONE_WAY_SNACK = "one-way-snack";

    public static final String API_GET_MEALS = API_ACCESS + API_QUERY + API_PARAM_ID + API_IDENTITY + API_AMPERSAND + API_PARAM_DATE + API_AMPERSAND + API_PARAM_RESTAURANT;
    public static final String API_GET_RESTAURANT_STATUS = API_ACCESS + API_QUERY + API_PARAM_ID + API_IDENTITY + API_AMPERSAND + API_PARAM_OPENING_STATUS + API_AMPERSAND + API_PARAM_RESTAURANT;


    // Meal Badges
    public static final String MEAL_BADGE_LACTOSE_FREE = "lactose-free";
    public static final String MEAL_BADGE_LOW_CALORIE = "low-calorie";
    public static final String MEAL_BADGE_NONFAT = "nonfat";
    public static final String MEAL_BADGE_VEGAN = "vegan";
    public static final String MEAL_BADGE_VEGETARIAN = "vegetarian";
    public static final String MEAL_BADGE_VITAL_FOOD = "vital-food";
}