package de.prttstft.materialmensa.ui.fragments.main.interactor;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;

import java.math.BigDecimal;
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
import static de.prttstft.materialmensa.extras.Constants.PRICE_TYPE_WEIGHTED;
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
                    listener.addMeal(prepareMeal(meal));
                } else {
                    listener.filteredMeal();
                }
            }
        });
    }

    // Private Methods

    private Meal prepareMeal(Meal meal) {
        meal.setPriceString(getPriceString(meal));
        meal.setOrderNumber(getOrderNumber(meal));

        return meal;
    }

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

    private int getOrderNumber(Meal meal) {
        switch (meal.getRestaurant()) {
            case "mensa-academica-paderborn":
                switch (meal.getSubcategoryEn()) {
                    case "Default Menu":
                        return 0;
                    case "Dish":
                        return 1;
                    case "Pasta":
                        return 2;
                    case "Wok":
                        return 3;
                    case "Grill":
                        return 4;
                    case "Default Dessert":
                        return 7;
                    case "Counter Dessert":
                        return 8;
                    default:
                        switch (meal.getCategoryEn()) {
                            case "Side Dish":
                                return 6;
                            case "Soups":
                                return 5;
                        }
                }
                break;
            case "mensa-forum-paderborn":
                switch (meal.getCategory()) {
                    case "dish-default":
                        return 0;
                    case "dish":
                        return 1;
                    case "dish-grill":
                        return 2;
                    case "sidedish":
                        return 3;
                    case "dessert-counter":
                        return 4;
                }
                break;
            default:
                switch (meal.getCategory()) {
                    case "classic":
                        return 0;
                    case "dish":
                        return 0;
                    case "wrap":
                        return 0;
                    case "offer":
                        return 0;
                    case "dessert-counter":
                        return 4;
                    case "sandwich":
                        return 1;
                    case "appetizer":
                        return 1;
                    case "lahmacun":
                        return 2;
                    case "maincourses":
                        return 2;
                    case "dessert":
                        return 3;
                }
                break;
        }
        return 99;
    }

    private String getPriceString(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String role = sharedPreferences.getString("prefRole", "1");

        String priceGuests = round(meal.getPriceGuests());
        String priceStaff = round(meal.getPriceWorkers());
        String priceStudents = round(meal.getPriceStudents());

        switch (role) {
            case "2":
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceStudents, getAppContext().getString(R.string.price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceStudents, getAppContext().getString(R.string.price_string_fixed));
                }
            case "3":
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceStaff, getAppContext().getString(R.string.price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceStaff, getAppContext().getString(R.string.price_string_fixed));
                }
            case "4":
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceGuests, getAppContext().getString(R.string.price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.price_string_single, priceGuests, getAppContext().getString(R.string.price_string_fixed));
                }
        }

        if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
            return getAppContext().getResources().getString(R.string.price_string_all, getAppContext().getString(R.string.price_string_weighted), priceStudents, priceStaff, priceGuests);
        } else {
            return getAppContext().getResources().getString(R.string.price_string_all, getAppContext().getString(R.string.price_string_fixed), priceStudents, priceStaff, priceGuests);
        }
    }

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

    private String round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}