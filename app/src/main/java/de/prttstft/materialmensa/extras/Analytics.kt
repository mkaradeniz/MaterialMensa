package de.prttstft.materialmensa.extras

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import de.prttstft.materialmensa.MyApplication

open class Analytics() {

    companion object {

        // Events

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

        @JvmStatic fun mealAddedToDatabase() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_added_to_database", bundle)
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

        @JvmStatic fun mealFiltered() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_filtered", bundle)
        }

        @JvmStatic fun mealServed() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_served", bundle)
        }

        @JvmStatic fun mealServedSocial() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_served_social", bundle)
        }

        @JvmStatic fun mealShared() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meal_shared", bundle)
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

        @JvmStatic fun mealsShared() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            val bundle = Bundle()

            analytics.logEvent("meals_shared", bundle)
        }


        // User Properties

        @JvmStatic fun setUserPropertyDefaultRestaurantAcademica() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "academica")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantBistroHotspot() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "bistro_hotspot")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantCafete() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "cafete")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantForum() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "forum")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantGrillCafe() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "grill_cafe")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantMensula() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "mensula")
        }

        @JvmStatic fun setUserPropertyDefaultRestaurantOneWaySnack() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("default_restaurant", "one_way_snack")
        }

        @JvmStatic fun setUserPropertyHideFilteredFalse() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("hide_filtered", "false")
        }

        @JvmStatic fun setUserPropertyHideFilteredTrue() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("hide_filtered", "true")
        }

        @JvmStatic fun setUserPropertyImagesInMainViewFalse() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("images_in_main_view", "false")
        }

        @JvmStatic fun setUserPropertyImagesInMainViewTrue() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("images_in_main_view", "true")
        }

        @JvmStatic fun setUserPropertyLanguageEnglish() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "english")
        }

        @JvmStatic fun setUserPropertyLanguageGerman() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "german")
        }

        @JvmStatic fun setUserPropertyLanguageSystemEnglishOrOther() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "system_english_or_other")
        }

        @JvmStatic fun setUserPropertyLanguageSystemGerman() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("language", "system_german")

        }

        @JvmStatic fun setUserPropertyLifestyleMeat() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "meat")
        }

        @JvmStatic fun setUserPropertyLifestyleLevelFiveVegan() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "level_five_vegan")
        }

        @JvmStatic fun setUserPropertyLifestyleVegan() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "vegan")
        }

        @JvmStatic fun setUserPropertyLifestyleVegetarian() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("lifestyle", "vegetarian")
        }

        @JvmStatic fun setUserPropertyRoleGuest() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "guest")
        }

        @JvmStatic fun setUserPropertyRoleStaff() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "staff")
        }

        @JvmStatic fun setUserPropertyRoleStudent() {
            val analytics: FirebaseAnalytics
            analytics = FirebaseAnalytics.getInstance(MyApplication.getAppContext())

            analytics.setUserProperty("role", "student")
        }
    }
}