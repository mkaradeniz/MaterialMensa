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
    private static final String HIDE_FILTERED_DEFAULT = getAppContext().getString(R.string.activity_settings_preferences_hide_filtered_default);
    private static final String HIDE_FILTERED_PREF = getAppContext().getString(R.string.activity_settings_preferences_hide_filtered_key);
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

    public static boolean getHideFiltered() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return sharedPreferences.getBoolean(HIDE_FILTERED_PREF, Boolean.valueOf(HIDE_FILTERED_DEFAULT));
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
        int tabCount = Integer.valueOf(sharedPreferences.getString(TABS_PREF, TABS_DEFAULT));

        if (tabCount < 1) {
            return 1;
        } else if (tabCount > 15) {
            return 15;
        } else {
            return tabCount;
        }
    }
}