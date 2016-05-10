package de.prttstft.materialmensa.extras;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import de.prttstft.materialmensa.R;

import static de.prttstft.materialmensa.MyApplication.getAppContext;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_LEVEL_FIVE_VEGAN;

public class UserSettings {
    private static final String ADDITIVES_PREF = getAppContext().getString(R.string.activity_settings_preferences_additives_key);
    private static final String ALLERGENS_PREF = getAppContext().getString(R.string.activity_settings_preferences_allergens_key);
    private static final String DEFAULT_RESTAURANT_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_default_restaurant_default);
    private static final String DEFAULT_RESTAURANT_PREF = getAppContext().getString(R.string.activity_settings_preferences_default_restaurant_key);
    private static final String HIDE_FILTERED_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_hide_filtered_default);
    private static final String HIDE_FILTERED_PREF = getAppContext().getString(R.string.activity_settings_preferences_hide_filtered_key);
    private static final String IMAGES_IN_MAIN_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_images_in_main_default);
    private static final String IMAGES_IN_MAIN_PREF = getAppContext().getString(R.string.activity_settings_preferences_images_in_main_key);
    private static final String LANGUAGE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_language_default);
    private static final String LANGUAGE_PREF = getAppContext().getString(R.string.activity_settings_preferences_language_key);
    private static final String LIFESTYLE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_lifestyle_default);
    private static final String LIFESTYLE_PREF = getAppContext().getString(R.string.activity_settings_preferences_lifestyle_key);
    private static final String ROLE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_role_default);
    private static final String ROLE_PREF = getAppContext().getString(R.string.activity_settings_preferences_role_key);

    private UserSettings() {

    }


    public static Set<String> getAdditives() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getStringSet(ADDITIVES_PREF, new HashSet<String>());
    }

    public static Set<String> getAllergens() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getStringSet(ALLERGENS_PREF, new HashSet<String>());
    }

    public static int getDefaultRestaurant() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return Integer.valueOf(sharedPreferences.getString(DEFAULT_RESTAURANT_PREF, DEFAULT_RESTAURANT_DEFAULT));
    }

    public static boolean getHideFiltered() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return getLifestyle().equals(LIFESTYLE_LEVEL_FIVE_VEGAN) || sharedPreferences.getBoolean(HIDE_FILTERED_PREF, Boolean.valueOf(HIDE_FILTERED_DEFAULT));
    }

    public static boolean getImagesInMainView() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getBoolean(IMAGES_IN_MAIN_PREF, Boolean.valueOf(IMAGES_IN_MAIN_DEFAULT));
    }

    public static String getLanguage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String language = sharedPreferences.getString(LANGUAGE_PREF, LANGUAGE_DEFAULT);

        if (language.equals(LANGUAGE_DEFAULT)) {
            return Utilities.getSystemLanguageShort();
        } else {
            return language;
        }
    }

    public static String getLifestyle() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getString(LIFESTYLE_PREF, LIFESTYLE_DEFAULT);
    }

    public static String getRole() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getString(ROLE_PREF, ROLE_DEFAULT);
    }
}