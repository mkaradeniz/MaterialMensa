package de.prttstft.materialmensa.constants;

import de.prttstft.materialmensa.BuildConfig;

public interface APIConstants {

    // General

    String API_ACCESS = "access2.php";
    String API_BASE_URL = "http://www.studentenwerk-pb.de/fileadmin/shareddata/";
    String API_IDENTITY = BuildConfig.API_KEY;

    String API_AMPERSAND = "&";
    String API_QUERY = "?";

    String API_PARAM_DATE = "date=";
    String API_PARAM_ID = "id=";
    String API_PARAM_OPENING_STATUS = "getopeningstatus=1";
    String API_PARAM_RESTAURANT = "restaurant=";


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
