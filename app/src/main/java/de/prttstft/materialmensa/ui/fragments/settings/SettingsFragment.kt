package de.prttstft.materialmensa.ui.fragments.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceCategory
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.preference.SwitchPreference
import de.prttstft.materialmensa.R
import de.prttstft.materialmensa.extras.UtilitiesKt
import de.prttstft.materialmensa.ui.fragments.settings.presenter.SettingsFragmentPresenter
import de.prttstft.materialmensa.ui.fragments.settings.presenter.SettingsFragmentPresenterImplementation

class SettingsFragment : PreferenceFragment() {
    lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    lateinit var presenter: SettingsFragmentPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SettingsFragmentPresenterImplementation()

        setUpPreferences()
        setUpPreferencesListener()
    }


    private fun setUpPreferences() {
        addPreferencesFromResource(R.xml.user_settings)

        if (!UtilitiesKt.arePlayServicesInstalled(activity)) {
            val socialFeaturesSwitchPreference = findPreference(resources.getString(R.string.activity_settings_preferences_show_social_key)) as SwitchPreference
            val generalPreferenceCategory = findPreference(resources.getString(R.string.activity_settings_preference_category_general_key)) as PreferenceCategory

            generalPreferenceCategory.removePreference(socialFeaturesSwitchPreference)
        }
    }

    private fun setUpPreferencesListener() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)

        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            presenter.onSharedPreferenceChange(sharedPreferences, key)
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
    }


}