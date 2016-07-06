package de.prttstft.materialmensa.ui.fragments.settings.interactor

import android.content.SharedPreferences

interface SettingsFragmentInteractor {
    fun onSharedPreferenceChange(sharedPreferences: SharedPreferences, key: String?)
}