package de.prttstft.materialmensa.ui.fragments.settings.interactor

import android.content.SharedPreferences
import de.prttstft.materialmensa.extras.Analytics
import de.prttstft.materialmensa.extras.UserSettings.*
import de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.*

class SettingsFragmentInteractorImplementation() : SettingsFragmentInteractor {
    override fun onSharedPreferenceChange(sharedPreferences: SharedPreferences, key: String?) {
        when (key) {
            LANGUAGE_PREF ->
                when (sharedPreferences.getString(key, "")) {
                    "not_set" -> Analytics.setUserLanguageSystem()
                    "en" -> Analytics.setUserLanguageEnglish()
                    "de" -> Analytics.setUserLanguageGerman()
                }
            LIFESTYLE_PREF ->
                when (sharedPreferences.getString(key, "")) {
                    LIFESTYLE_NOT_SET -> Analytics.setUserLifestyleMeat()
                    LIFESTYLE_VEGETARIAN -> Analytics.setUserLifestyleVegetarian()
                    LIFESTYLE_VEGAN -> Analytics.setUserLifestyleVegan()
                    LIFESTYLE_LEVEL_FIVE_VEGAN -> Analytics.setUserLifestyleLevelFiveVegan()
                }
            ROLE_PREF ->
                when (sharedPreferences.getString(key, "")) {
                    ROLE_GUEST -> Analytics.setUserRoleGuest()
                    ROLE_STAFF -> Analytics.setUserRoleStaff()
                    ROLE_STUDENT -> Analytics.setUserRoleStudent()
                }
        }
    }
}