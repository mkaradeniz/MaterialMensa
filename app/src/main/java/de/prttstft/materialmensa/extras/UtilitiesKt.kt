package de.prttstft.materialmensa.extras

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import de.prttstft.materialmensa.model.Meal
import timber.log.Timber


open class UtilitiesKt() {

    companion object {

        @JvmStatic fun sortMeals(meals: List<Meal>): MutableList<Meal> {
            return meals.sorted().toMutableList()
        }

        @JvmStatic fun arePlayServicesInstalled(context: Context): Boolean {
            val apiAvailability = GoogleApiAvailability.getInstance()
            val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)
            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    Timber.e("Social features not supported. (Google Play Services not installed or up-to-date.")
                } else {
                    Timber.e("This device is not supported. (API Level < 19)")
                }
                return false
            }
            return true
        }

    }
}