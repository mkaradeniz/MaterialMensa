package de.prttstft.materialmensa.ui.activities.launch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import de.prttstft.materialmensa.extras.Analytics
import de.prttstft.materialmensa.extras.UserSettings
import de.prttstft.materialmensa.extras.Utilities
import de.prttstft.materialmensa.extras.UtilitiesKt
import de.prttstft.materialmensa.ui.activities.main.MainActivity
import de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.*
import timber.log.Timber

class LaunchActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpFirebase()
        setUpUserProperties()
    }


    private fun setUpFirebase() {
        if (UtilitiesKt.arePlayServicesInstalled(this)) {
            auth = FirebaseAuth.getInstance()

            authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentUser
                if (user != null) {
                    launchMainActivity()
                    finish()
                } else {
                    auth!!.signInAnonymously().addOnCompleteListener(this, { task ->
                        if (!task.isSuccessful) {
                            Timber.e(task.exception?.message)

                            UserSettings.disableSocialFeatures()

                            launchMainActivity()
                            finish()
                        }
                    })

                }
            }
        } else {
            UserSettings.disableSocialFeatures()

            launchMainActivity()
            finish()
        }
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
                LIFESTYLE_NOT_SET -> Analytics.setUserPropertyLifestyleMeat()
                LIFESTYLE_VEGETARIAN -> Analytics.setUserPropertyLifestyleVegetarian()
                LIFESTYLE_VEGAN -> Analytics.setUserPropertyLifestyleVegan()
                LIFESTYLE_LEVEL_FIVE_VEGAN -> Analytics.setUserPropertyLifestyleLevelFiveVegan()
            }

            when (UserSettings.getRole()) {
                ROLE_GUEST -> Analytics.setUserPropertyRoleGuest()
                ROLE_STAFF -> Analytics.setUserPropertyRoleStaff()
                ROLE_STUDENT -> Analytics.setUserPropertyRoleStudent()
            }

        }
    }


    public override fun onStart() {
        super.onStart()

        if (auth != null) {
            auth!!.addAuthStateListener(authStateListener!!)
        }
    }

    public override fun onStop() {
        super.onStop()

        if (authStateListener != null) {
            auth!!.removeAuthStateListener(authStateListener!!)
        }
    }


    private fun launchMainActivity() {
        val startMainActivityIntent = Intent(this@LaunchActivity, MainActivity::class.java)
        startActivity(startMainActivityIntent)
    }
}
