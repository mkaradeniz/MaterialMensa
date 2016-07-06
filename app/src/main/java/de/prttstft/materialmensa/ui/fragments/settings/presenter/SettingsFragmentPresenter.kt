package de.prttstft.materialmensa.ui.fragments.settings.presenter

import android.content.SharedPreferences

interface SettingsFragmentPresenter {
    fun onSharedPreferenceChange(sharedPreferences: SharedPreferences, key: String?)
}