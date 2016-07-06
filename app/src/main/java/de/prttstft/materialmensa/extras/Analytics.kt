package de.prttstft.materialmensa.extras

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import de.prttstft.materialmensa.MyApplication
import timber.log.Timber

open class Analytics() {

    companion object {

        @JvmStatic fun activityAboutViewed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("activity_about", bundle)
        }

        @JvmStatic fun activityDetailsViewed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("activity_details", bundle)
        }

        @JvmStatic fun activityMainViewed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("activity_main", bundle)
        }

        @JvmStatic fun activitySettingsViewed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("activity_settings", bundle)
        }

        @JvmStatic fun mealdownvoted() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_downvoted", bundle)
        }

        @JvmStatic fun mealdownvoteRemoved() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_downvote_removed", bundle)
        }

        @JvmStatic fun mealAddedToDatabase() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_added_to_database", bundle)
        }

        @JvmStatic fun mealFiltered() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_filtered", bundle)
        }

        @JvmStatic fun mealShared() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_shared", bundle)
        }

        @JvmStatic fun mealsShared() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meals_shared", bundle)
        }

        @JvmStatic fun mealUpvoted() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_upvoted", bundle)
        }

        @JvmStatic fun mealUpvoteRemoved() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_upvote_removed", bundle)
        }

        @JvmStatic fun setUserLanguageEnglish() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            Timber.d("English")

            analytics.setUserProperty("language", "english")
        }

        @JvmStatic fun setUserLanguageGerman() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "german")
        }

        @JvmStatic fun setUserLanguageSystem() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "system")
        }

        @JvmStatic fun setUserLifestyleMeat() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "meat")
        }

        @JvmStatic fun setUserLifestyleLevelFiveVegan() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "level_five_vegan")
        }

        @JvmStatic fun setUserLifestyleVegan() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "vegan")
        }

        @JvmStatic fun setUserLifestyleVegetarian() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "vegetarian")
        }

        @JvmStatic fun setUserRoleGuest() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "guest")
        }

        @JvmStatic fun setUserRoleStaff() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "staff")
        }

        @JvmStatic fun setUserRoleStudent() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "student")
        }

        @JvmStatic fun socialMealServed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_shared", bundle)
        }
    }
}