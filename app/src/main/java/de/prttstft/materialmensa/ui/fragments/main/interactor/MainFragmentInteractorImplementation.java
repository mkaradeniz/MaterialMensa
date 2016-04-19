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
import static de.prttstft.materialmensa.R.string.activity_settings_preferences_lifestyle_default;
import static de.prttstft.materialmensa.R.string.activity_settings_preferences_lifestyle_key;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_LACTOSE_FREE;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_LOW_CALORIE;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_NONFAT;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGAN;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VEGETARIAN;
import static de.prttstft.materialmensa.extras.Constants.MealBadgeConstants.MEAL_BADGE_VITAL_FOOD;
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
    public static final String ACADEMICA_COUNTER_DESSERT = "Counter Dessert";
    public static final String ACADEMICA_DEFAULT_DESSERT = "Default Dessert";
    public static final String ACADEMICA_DEFAULT_MENU = "Default Menu";
    public static final String ACADEMICA_DISH = "Dish";
    public static final String ACADEMICA_GRILL = "Grill";
    public static final String ACADEMICA_PASTA = "Pasta";
    public static final String ACADEMICA_SIDE_DISH = "Side Dish";
    public static final String ACADEMICA_SOUPS = "Soups";
    public static final String ACADEMICA_WOK = "Wok";
    public static final String FORUM_DESSERT_COUNTER = "dessert-counter";
    public static final String FORUM_DISH = "dish";
    public static final String FORUM_DISH_DEFAULT = "dish-default";
    public static final String FORUM_DISH_GRILL = "dish-grill";
    public static final String FORUM_SIDE_DISH = "sidedish";
    public static final String OTHER_APPETIZER = "appetizer";
    public static final String OTHER_CLASSIC = "classic";
    public static final String OTHER_DESSERT = "dessert";
    public static final String OTHER_DESSERT_COUNTER = "dessert-counter";
    public static final String OTHER_DISH = "dish";
    public static final String OTHER_LAHMACUN = "lahmacun";
    public static final String OTHER_MAIN_COURSES = "maincourses";
    public static final String OTHER_OFFER = "offer";
    public static final String OTHER_SANDWICH = "sandwich";
    public static final String OTHER_WRAP = "wrap";
    public static final String PRICE_TYPE_WEIGHTED = "weighted";
    public static final String ROLE_DEFAULT = "not_set";
    public static final String ROLE_GUEST = "guest";
    public static final String ROLE_PREF = "prefRole";
    public static final String ROLE_STAFF = "staff";
    public static final String ROLE_STUDENT = "student";

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
                L("Error adding meal: " + e.toString());
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
        meal.setCustomDescription(getDescription(meal));

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

    private int getOrderNumber(Meal meal) {
        switch (meal.getRestaurant()) {
            case API_RESTAURANT_ACADEMICA:
                switch (meal.getSubcategoryEn()) {
                    case ACADEMICA_DEFAULT_MENU:
                        return 0;
                    case ACADEMICA_DISH:
                        return 1;
                    case ACADEMICA_PASTA:
                        return 2;
                    case ACADEMICA_WOK:
                        return 3;
                    case ACADEMICA_GRILL:
                        return 4;
                    case ACADEMICA_DEFAULT_DESSERT:
                        return 7;
                    case ACADEMICA_COUNTER_DESSERT:
                        return 8;
                    default:
                        switch (meal.getCategoryEn()) {
                            case ACADEMICA_SIDE_DISH:
                                return 6;
                            case ACADEMICA_SOUPS:
                                return 5;
                        }
                }
                break;
            case API_RESTAURANT_FORUM:
                switch (meal.getCategory()) {
                    case FORUM_DISH_DEFAULT:
                        return 0;
                    case FORUM_DISH:
                        return 1;
                    case FORUM_DISH_GRILL:
                        return 2;
                    case FORUM_SIDE_DISH:
                        return 3;
                    case FORUM_DESSERT_COUNTER:
                        return 4;
                }
                break;
            default:
                switch (meal.getCategory()) {
                    case OTHER_CLASSIC:
                        return 0;
                    case OTHER_DISH:
                        return 0;
                    case OTHER_WRAP:
                        return 0;
                    case OTHER_OFFER:
                        return 0;
                    case OTHER_DESSERT_COUNTER:
                        return 4;
                    case OTHER_SANDWICH:
                        return 1;
                    case OTHER_APPETIZER:
                        return 1;
                    case OTHER_LAHMACUN:
                        return 2;
                    case OTHER_MAIN_COURSES:
                        return 2;
                    case OTHER_DESSERT:
                        return 3;
                }
                break;
        }
        return 99;
    }

    private String getDescription(Meal meal) {
        String category = getAppContext().getString(R.string.item_meal_description_category_meal);
        String badge = getBadge(meal);
        switch (meal.getRestaurant()) {
            case API_RESTAURANT_ACADEMICA:
                switch (meal.getSubcategoryEn()) {
                    case ACADEMICA_COUNTER_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_DEFAULT_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_DEFAULT_MENU:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_GRILL:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_PASTA:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case ACADEMICA_WOK:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    default:
                        switch (meal.getCategoryEn()) {
                            case ACADEMICA_SIDE_DISH:
                                category = getAppContext().getString(R.string.item_meal_description_category_side_dish);
                                return getAppContext().getString(R.string.item_meal_description, badge, category);
                            case ACADEMICA_SOUPS:
                                category = getAppContext().getString(R.string.item_meal_description_category_soup);
                                return getAppContext().getString(R.string.item_meal_description, badge, category);
                        }
                }
                break;
            case API_RESTAURANT_FORUM:
                switch (meal.getCategory()) {
                    case FORUM_DESSERT_COUNTER:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case FORUM_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case FORUM_DISH_DEFAULT:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case FORUM_DISH_GRILL:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case FORUM_SIDE_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_side_dish);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                }
                break;
            default:
                switch (meal.getCategory()) {
                    case OTHER_APPETIZER:
                        category = getAppContext().getString(R.string.item_meal_description_category_appetizer);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_CLASSIC:
                        category = getAppContext().getString(R.string.item_meal_description_category_classic);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_DESSERT_COUNTER:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_LAHMACUN:
                        category = getAppContext().getString(R.string.item_meal_description_category_lahmacun);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_MAIN_COURSES:
                        category = getAppContext().getString(R.string.item_meal_description_category_main_course);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_OFFER:
                        category = getAppContext().getString(R.string.item_meal_description_category_offer);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_SANDWICH:
                        category = getAppContext().getString(R.string.item_meal_description_category_sandwich);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                    case OTHER_WRAP:
                        category = getAppContext().getString(R.string.item_meal_description_category_wrap);
                        return getAppContext().getString(R.string.item_meal_description, badge, category);
                }
                break;
        }
        return getAppContext().getString(R.string.item_meal_description, badge, category);
    }

    private String getBadge(Meal meal) {
        switch (meal.getBadge()) {
            case MEAL_BADGE_LACTOSE_FREE:
                return getAppContext().getString(R.string.item_meal_description_badge_lactose_free);
            case MEAL_BADGE_LOW_CALORIE:
                return getAppContext().getString(R.string.item_meal_description_badge_low_calorie);
            case MEAL_BADGE_NONFAT:
                return getAppContext().getString(R.string.item_meal_description_badge_non_fat);
            case MEAL_BADGE_VEGAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegan);
            case MEAL_BADGE_VEGETARIAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegetarian);
            case MEAL_BADGE_VITAL_FOOD:
                return getAppContext().getString(R.string.item_meal_description_badge_vital_food);
            default:
                return getAppContext().getString(R.string.empty_string);
        }
    }

    private String getPriceString(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String role = sharedPreferences.getString(ROLE_PREF, ROLE_DEFAULT);

        String priceGuests = round(meal.getPriceGuests());
        String priceStaff = round(meal.getPriceWorkers());
        String priceStudents = round(meal.getPriceStudents());

        switch (role) {
            case ROLE_STUDENT:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceStudents, getAppContext().getString(R.string.item_meal_price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceStudents, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
            case ROLE_STAFF:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceStaff, getAppContext().getString(R.string.item_meal_price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceStaff, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
            case ROLE_GUEST:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceGuests, getAppContext().getString(R.string.item_meal_price_string_single_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string_single, priceGuests, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
        }

        if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
            return getAppContext().getResources().getString(R.string.item_meal_price_string_all, getAppContext().getString(R.string.item_meal_price_string_weighted), priceStudents, priceStaff, priceGuests);
        } else {
            return getAppContext().getResources().getString(R.string.item_meal_price_string_all, getAppContext().getString(R.string.item_meal_price_string_fixed), priceStudents, priceStaff, priceGuests);
        }
    }

    private boolean filterMeal(Meal meal) {
        return (filterAdditives(meal) || filterAllergens(meal) || filterLifestyle(meal));
    }

    private boolean filterAdditives(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        Set<String> additives = sharedPreferences.getStringSet(getAppContext().getString(R.string.activity_settings_preferences_additives_key), new HashSet<String>());

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (additives.contains(meal.getAllergens().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterAllergens(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        Set<String> allergens = sharedPreferences.getStringSet(getAppContext().getString(R.string.activity_settings_preferences_allergens_key), new HashSet<String>());

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (allergens.contains(meal.getAllergens().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterLifestyle(Meal meal) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String lifestyle = sharedPreferences.getString(getAppContext().getString(activity_settings_preferences_lifestyle_key), getAppContext().getString(activity_settings_preferences_lifestyle_default));

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