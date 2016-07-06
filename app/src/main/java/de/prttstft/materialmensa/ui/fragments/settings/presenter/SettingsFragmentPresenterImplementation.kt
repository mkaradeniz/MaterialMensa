package de.prttstft.materialmensa.ui.fragments.settings.presenter

import android.content.SharedPreferences
import de.prttstft.materialmensa.ui.fragments.settings.interactor.SettingsFragmentInteractor
import de.prttstft.materialmensa.ui.fragments.settings.interactor.SettingsFragmentInteractorImplementation

class SettingsFragmentPresenterImplementation() : SettingsFragmentPresenter {
    lateinit var interactor: SettingsFragmentInteractor

    init {
        interactor = SettingsFragmentInteractorImplementation()
    }

    override fun onSharedPreferenceChange(sharedPreferences: SharedPreferences, key: String?) {
        interactor.onSharedPreferenceChange(sharedPreferences, key)
    }
}