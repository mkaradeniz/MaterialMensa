package de.prttstft.materialmensa.ui.fragments.main.interactor;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.MyApplication.getAppContext;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGAN;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGETARIAN;
import static de.prttstft.materialmensa.extras.Constants.PreferencesConstants.PREFERENCE_VALUE_LIFESTYLE_NOT_SET;
import static de.prttstft.materialmensa.extras.Constants.PreferencesConstants.PREFERENCE_VALUE_LIFESTYLE_VEGAN;
import static de.prttstft.materialmensa.extras.Constants.PreferencesConstants.PREFERENCE_VALUE_LIFESTYLE_VEGETARIAN;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Utilities.L;

public class MainFragmentInteractorImplementation implements MainFragmentInteractor {
    @Override
    public void getMeals(final MainFragmentListener listener, final int page, final int restaurant) {
        Observable<Meal> observable = MensaAPI.mensaAPI.getMeals(getDateString(page), getRestaurantString(restaurant)).flatMap(new Func1<List<Meal>, Observable<Meal>>() {
            @Override
            public Observable<Meal> call(List<Meal> iterable) {
                return Observable.from(iterable);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Meal>() {
            @Override
            public void onCompleted() {
                listener.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                L("Error: " + e.toString());
            }

            @Override
            public void onNext(Meal meal) {
                if (!filterMeal(meal)) {
                    listener.addMeal(meal);
                } else {
                    listener.filteredMeal();
                }
            }
        });
    }

    // Private Methods

    private String getDateString(int page) {
        String pattern = "yyyy-MM-dd";
        return new DateTime().plusDays(page).toString(pattern);
    }

    private String getRestaurantString(int restaurant) {
        switch (restaurant) {
            case RESTAURANT_ID_ACADEMICA:
                return API_RESTAURANT_ACADEMICA;
            case RESTAURANT_ID_FORUM:
                return API_RESTAURANT_FORUM;
            case RESTAURANT_ID_CAFETE:
                return API_RESTAURANT_CAFETE;
            case RESTAURANT_ID_MENSULA:
                return API_RESTAURANT_MENSULA;
            case RESTAURANT_ID_ONE_WAY_SNACK:
                return API_RESTAURANT_ONE_WAY_SNACK;
            case RESTAURANT_ID_GRILL_CAFE:
                return API_RESTAURANT_GRILL_CAFE;
            case RESTAURANT_ID_CAMPUS_DOENER:
                return API_RESTAURANT_CAMPUS_DOENER;
            default:
                return API_RESTAURANT_ACADEMICA;
        }
    }

    //position

    private boolean filterMeal(Meal meal) {
        return (filterAdditives(meal) || filterAllergens(meal) || filterLifestyle(meal));
    }

    private boolean filterAdditives(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        Set<String> additives = sharedPreferences.getStringSet(getAppContext().getString(R.string.sp_pref_additives), new HashSet<String>());

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (additives.contains(meal.getAllergens().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterAllergens(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        Set<String> allergens = sharedPreferences.getStringSet(getAppContext().getString(R.string.sp_pref_allergens), new HashSet<String>());

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (allergens.contains(meal.getAllergens().get(i).substring(1))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterLifestyle(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String lifestyle = sharedPreferences.getString(getAppContext().getString(R.string.sp_pref_lifestyle), "0");

        if (!lifestyle.equals(PREFERENCE_VALUE_LIFESTYLE_NOT_SET)) {
            switch (lifestyle) {
                case PREFERENCE_VALUE_LIFESTYLE_VEGETARIAN:
                    if ((!meal.getBadges().contains(MEAL_BADGE_VEGETARIAN)) && (!meal.getBadges().contains(MEAL_BADGE_VEGAN))) {
                        return true;
                    }
                    break;
                case PREFERENCE_VALUE_LIFESTYLE_VEGAN:
                    if (!meal.getBadges().contains(MEAL_BADGE_VEGAN)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
