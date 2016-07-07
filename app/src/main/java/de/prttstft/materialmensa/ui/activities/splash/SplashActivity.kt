package de.prttstft.materialmensa.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import de.prttstft.materialmensa.extras.Analytics
import de.prttstft.materialmensa.extras.UserSettings
import de.prttstft.materialmensa.extras.Utilities
import de.prttstft.materialmensa.extras.UtilitiesKt
import de.prttstft.materialmensa.ui.activities.main.MainActivity
import de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UtilitiesKt.arePlayServicesInstalled(this)) {
            setUpWithSocialFeatures()
        } else {
            setUpWithoutSocialFeatures()
        }
    }


    private fun setUpWithSocialFeatures() {
        auth = FirebaseAuth.getInstance()

        if (auth?.currentUser != null) {
            setUpUserProperties()

            launchMainActivity()
        } else {
            auth!!.signInAnonymously().addOnCompleteListener(this, { task ->
                if (!task.isSuccessful) {
                    Timber.e(task.exception?.message)
                } else {
                    setUpUserProperties()

                    launchMainActivity()
                }
            })
        }
    }

    private fun setUpWithoutSocialFeatures() {
        UserSettings.disableSocialFeatures()

        launchMainActivity()
    }


    private fun launchMainActivity() {
        val startMainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(startMainActivityIntent)
        finish()
    }

    private fun setUpUserProperties() {
        if (auth?.currentUser != null) {
            when (UserSettings.getDefaultRestaurant()) {
                0 -> Analytics.setUserPropertyDefaultRestaurantAcademica()
                1 -> Analytics.setUserPropertyDefaultRestaurantForum()
                2 -> Analytics.setUserPropertyDefaultRestaurantCafete()
                3 -> Analytics.setUserPropertyDefaultRestaurantGrillCafe()
                4 -> Analytics.setUserPropertyDefaultRestaurantOneWaySnack()
                5 -> Analytics.setUserPropertyDefaultRestaurantBistroHotspot()
                6 -> Analytics.setUserPropertyDefaultRestaurantMensula()
            }

            when (UserSettings.getHideFiltered()) {
                true -> Analytics.setUserPropertyHideFilteredTrue()
                false -> Analytics.setUserPropertyHideFilteredFalse()
            }

            when (UserSettings.getImagesInMainView()) {
                true -> Analytics.setUserPropertyImagesInMainViewTrue()
                false -> Analytics.setUserPropertyImagesInMainViewFalse()
            }

            when (UserSettings.getLanguageExact()) {
                "de" -> Analytics.setUserPropertyLanguageGerman()
                "en" -> Analytics.setUserPropertyLanguageEnglish()
                "not_set" ->
                    when (Utilities.getSystemLanguageShort()) {
                        "de" -> {
                            Analytics.setUserPropertyLanguageSystemGerman()
                        }
                        else -> {
                            Analytics.setUserPropertyLanguageSystemEnglishOrOther()
                        }
                    }

            }

            when (UserSettings.getLifestyle()) {
                MainFragmentInteractorImplementation.LIFESTYLE_NOT_SET -> Analytics.setUserPropertyLifestyleMeat()
                MainFragmentInteractorImplementation.LIFESTYLE_VEGETARIAN -> Analytics.setUserPropertyLifestyleVegetarian()
                MainFragmentInteractorImplementation.LIFESTYLE_VEGAN -> Analytics.setUserPropertyLifestyleVegan()
                MainFragmentInteractorImplementation.LIFESTYLE_LEVEL_FIVE_VEGAN -> Analytics.setUserPropertyLifestyleLevelFiveVegan()
            }

            when (UserSettings.getRole()) {
                MainFragmentInteractorImplementation.ROLE_GUEST -> Analytics.setUserPropertyRoleGuest()
                MainFragmentInteractorImplementation.ROLE_STAFF -> Analytics.setUserPropertyRoleStaff()
                MainFragmentInteractorImplementation.ROLE_STUDENT -> Analytics.setUserPropertyRoleStudent()
            }
        }
    }
}