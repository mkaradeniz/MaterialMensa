package de.prttstft.materialmensa.ui.fragments.settings

import android.os.Bundle
import android.preference.PreferenceCategory
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import de.prttstft.materialmensa.R
import de.prttstft.materialmensa.extras.UtilitiesKt

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.user_settings)

        if (!UtilitiesKt.arePlayServicesInstalled(activity)) {
            val socialFeaturesSwitchPreference = findPreference(resources.getString(R.string.activity_settings_preferences_show_social_key)) as SwitchPreference
            val generalPreferenceCategory = findPreference(resources.getString(R.string.activity_settings_preference_category_general_key)) as PreferenceCategory

            generalPreferenceCategory.removePreference(socialFeaturesSwitchPreference)
        }
    }
}