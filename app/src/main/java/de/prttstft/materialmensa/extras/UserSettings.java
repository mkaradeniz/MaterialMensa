package de.prttstft.materialmensa.extras;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import de.prttstft.materialmensa.R;

import static de.prttstft.materialmensa.MyApplication.getAppContext;

public class UserSettings {
    private static final String ADDITIVES_PREF = getAppContext().getString(R.string.activity_settings_preferences_additives_key);
    private static final String ALLERGENS_PREF = getAppContext().getString(R.string.activity_settings_preferences_allergens_key);
    private static final String LANGUAGE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_language_default);
    private static final String LANGUAGE_PREF = getAppContext().getString(R.string.activity_settings_preferences_language_key);
    private static final String LIFESTYLE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_lifestyle_default);
    private static final String LIFESTYLE_PREF = getAppContext().getString(R.string.activity_settings_preferences_lifestyle_key);
    private static final String ROLE_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_role_default);
    private static final String ROLE_PREF = getAppContext().getString(R.string.activity_settings_preferences_role_key);
    private static final String TABS_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_tabs_default);
    private static final String TABS_PREF = getAppContext().getString(R.string.activity_settings_preferences_tabs_key);


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

    public static String getLifestyle() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getString(LIFESTYLE_PREF, LIFESTYLE_DEFAULT);
    }

    public static String getRole() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getString(ROLE_PREF, ROLE_DEFAULT);
    }

    public static int getTabCount() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return Integer.valueOf(sharedPreferences.getString(TABS_PREF, TABS_DEFAULT));
    }
}