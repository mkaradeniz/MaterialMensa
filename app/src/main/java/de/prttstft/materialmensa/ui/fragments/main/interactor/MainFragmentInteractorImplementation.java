package de.prttstft.materialmensa.ui.fragments.main.interactor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.DateTimeUtilities;
import de.prttstft.materialmensa.extras.UserSettings;
import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.MyApplication.getAppContext;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_LACTOSE_FREE;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_LOW_CALORIE;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_NONFAT;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_VEGAN;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_VEGETARIAN;
import static de.prttstft.materialmensa.constants.APIConstants.MEAL_BADGE_VITAL_FOOD;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_CAMPUS_DOENER;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Utilities.L;

public class MainFragmentInteractorImplementation implements MainFragmentInteractor {
    public static final String LIFESTYLE_LEVEL_FIVE_VEGAN = "level_five_vegan";
    public static final String LIFESTYLE_NOT_SET = "not_set";
    public static final String LIFESTYLE_VEGAN = "vegan";
    public static final String LIFESTYLE_VEGETARIAN = "vegetarian";
    public static final String ROLE_GUEST = "guest";
    public static final String ROLE_STAFF = "staff";
    public static final String ROLE_STUDENT = "student";
    private static final String ACADEMICA_COUNTER_DESSERT = "Counter Dessert";
    private static final String ACADEMICA_DEFAULT_DESSERT = "Default Dessert";
    private static final String ACADEMICA_DEFAULT_MENU = "Default Menu";
    private static final String ACADEMICA_DISH = "Dish";
    private static final String ACADEMICA_GRILL = "Grill";
    private static final String ACADEMICA_PASTA = "Pasta";
    private static final String ACADEMICA_SIDE_DISH = "Side Dish";
    private static final String ACADEMICA_SOUPS = "Soups";
    private static final String ACADEMICA_WOK = "Wok";
    private static final String FORUM_DESSERT = "dessert";
    private static final String FORUM_DESSERT_COUNTER = "dessert-counter";
    private static final String FORUM_DISH = "dish";
    private static final String FORUM_DISH_DEFAULT = "dish-default";
    private static final String FORUM_DISH_GRILL = "dish-grill";
    private static final String FORUM_SIDE_DISH = "sidedish";
    private static final String OTHER_APPETIZER = "appetizer";
    private static final String OTHER_CLASSIC = "classic";
    private static final String OTHER_DESSERT = "dessert";
    private static final String OTHER_DESSERT_COUNTER = "dessert-counter";
    private static final String OTHER_DISH = "dish";
    private static final String OTHER_LAHMACUN = "lahmacun";
    private static final String OTHER_MAIN_COURSES = "maincourses";
    private static final String OTHER_OFFER = "offer";
    private static final String OTHER_SANDWICH = "sandwich";
    private static final String OTHER_WRAP = "wrap";
    private static final String PRICE_TYPE_WEIGHTED = "weighted";

    @Override
    public void getMeals(final MainFragmentListener listener, final int page, final int restaurant) {
        Observable<Meal> observable = MensaAPI.mensaAPI.getMeals(DateTimeUtilities.getDateString(page), getRestaurantString(restaurant)).flatMap(new Func1<List<Meal>, Observable<Meal>>() {
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
                if (filterMeal(meal) && UserSettings.getHideFiltered()) {
                    listener.filteredMeal();
                } else {
                    listener.addMeal(prepareMeal(meal));
                }
            }
        });
    }


    // Private Methods

    private Meal prepareMeal(Meal meal) {
        meal.setPriceString(getPriceString(meal));
        meal.setOrderNumber(getOrderNumber(meal));
        meal.setCustomDescription(getDescription(meal));

        if (filterAdditives(meal) || filterAllergens(meal)) {
            meal.setFiltered(true);
        }

        return meal;
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
        String badgeE = getBadgeE(meal);
        String badgeR = getBadgeR(meal);
        String badgeS = getBadgeS(meal);

        switch (meal.getRestaurant()) {
            case API_RESTAURANT_ACADEMICA:
                switch (meal.getSubcategoryEn()) {
                    case ACADEMICA_COUNTER_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_DEFAULT_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_DEFAULT_MENU:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_GRILL:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_PASTA:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case ACADEMICA_WOK:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    default:
                        switch (meal.getCategoryEn()) {
                            case ACADEMICA_SIDE_DISH:
                                category = getAppContext().getString(R.string.item_meal_description_category_side_dish);
                                return getAppContext().getString(R.string.item_meal_description, badgeE, category);
                            case ACADEMICA_SOUPS:
                                category = getAppContext().getString(R.string.item_meal_description_category_soup);
                                return getAppContext().getString(R.string.item_meal_description, badgeE, category);
                        }
                }
                break;
            case API_RESTAURANT_FORUM:
                switch (meal.getCategory()) {
                    case FORUM_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case FORUM_DESSERT_COUNTER:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case FORUM_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case FORUM_DISH_DEFAULT:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case FORUM_DISH_GRILL:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case FORUM_SIDE_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_side_dish);
                        return getAppContext().getString(R.string.item_meal_description, badgeE, category);
                }
                break;
            default:
                switch (meal.getCategory()) {
                    case OTHER_APPETIZER:
                        category = getAppContext().getString(R.string.item_meal_description_category_appetizer);
                        return getAppContext().getString(R.string.item_meal_description, badgeE, category);
                    case OTHER_CLASSIC:
                        category = getAppContext().getString(R.string.item_meal_description_category_classic);
                        return getAppContext().getString(R.string.item_meal_description, badgeR, category);
                    case OTHER_DESSERT:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_DESSERT_COUNTER:
                        category = getAppContext().getString(R.string.item_meal_description_category_dessert);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_DISH:
                        category = getAppContext().getString(R.string.item_meal_description_category_meal);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_LAHMACUN:
                        category = getAppContext().getString(R.string.item_meal_description_category_lahmacun);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_MAIN_COURSES:
                        category = getAppContext().getString(R.string.item_meal_description_category_main_course);
                        return getAppContext().getString(R.string.item_meal_description, badgeR, category);
                    case OTHER_OFFER:
                        category = getAppContext().getString(R.string.item_meal_description_category_offer);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_SANDWICH:
                        category = getAppContext().getString(R.string.item_meal_description_category_sandwich);
                        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
                    case OTHER_WRAP:
                        category = getAppContext().getString(R.string.item_meal_description_category_wrap);
                        return getAppContext().getString(R.string.item_meal_description, badgeR, category);
                }
                break;
        }
        return getAppContext().getString(R.string.item_meal_description, badgeS, category);
    }

    private String getBadgeS(Meal meal) {
        switch (meal.getBadge()) {
            case MEAL_BADGE_LACTOSE_FREE:
                return getAppContext().getString(R.string.item_meal_description_badge_lactose_free_s);
            case MEAL_BADGE_LOW_CALORIE:
                return getAppContext().getString(R.string.item_meal_description_badge_low_calorie_s);
            case MEAL_BADGE_NONFAT:
                return getAppContext().getString(R.string.item_meal_description_badge_non_fat_s);
            case MEAL_BADGE_VEGAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegan_s);
            case MEAL_BADGE_VEGETARIAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegetarian_s);
            case MEAL_BADGE_VITAL_FOOD:
                return getAppContext().getString(R.string.item_meal_description_badge_vital_food_s);
            default:
                return getAppContext().getString(R.string.empty_string);
        }
    }

    private String getBadgeE(Meal meal) {
        switch (meal.getBadge()) {
            case MEAL_BADGE_LACTOSE_FREE:
                return getAppContext().getString(R.string.item_meal_description_badge_lactose_free_e);
            case MEAL_BADGE_LOW_CALORIE:
                return getAppContext().getString(R.string.item_meal_description_badge_low_calorie_e);
            case MEAL_BADGE_NONFAT:
                return getAppContext().getString(R.string.item_meal_description_badge_non_fat_e);
            case MEAL_BADGE_VEGAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegan_e);
            case MEAL_BADGE_VEGETARIAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegetarian_e);
            case MEAL_BADGE_VITAL_FOOD:
                return getAppContext().getString(R.string.item_meal_description_badge_vital_food_e);
            default:
                return getAppContext().getString(R.string.empty_string);
        }
    }

    private String getBadgeR(Meal meal) {
        switch (meal.getBadge()) {
            case MEAL_BADGE_LACTOSE_FREE:
                return getAppContext().getString(R.string.item_meal_description_badge_lactose_free_r);
            case MEAL_BADGE_LOW_CALORIE:
                return getAppContext().getString(R.string.item_meal_description_badge_low_calorie_r);
            case MEAL_BADGE_NONFAT:
                return getAppContext().getString(R.string.item_meal_description_badge_non_fat_r);
            case MEAL_BADGE_VEGAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegan_r);
            case MEAL_BADGE_VEGETARIAN:
                return getAppContext().getString(R.string.item_meal_description_badge_vegetarian_r);
            case MEAL_BADGE_VITAL_FOOD:
                return getAppContext().getString(R.string.item_meal_description_badge_vital_food_r);
            default:
                return getAppContext().getString(R.string.empty_string);
        }
    }

    private String getPriceString(Meal meal) {
        String role = UserSettings.getRole();

        String priceGuests = round(meal.getPriceGuests());
        String priceStaff = round(meal.getPriceWorkers());
        String priceStudents = round(meal.getPriceStudents());

        switch (role) {
            case ROLE_STUDENT:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStudents, getAppContext().getString(R.string.item_meal_price_string_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStudents, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
            case ROLE_STAFF:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStaff, getAppContext().getString(R.string.item_meal_price_string_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStaff, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
            case ROLE_GUEST:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceGuests, getAppContext().getString(R.string.item_meal_price_string_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceGuests, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
            default:
                if (meal.getPricetype().equals(PRICE_TYPE_WEIGHTED)) {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStudents, getAppContext().getString(R.string.item_meal_price_string_weighted));
                } else {
                    return getAppContext().getResources().getString(R.string.item_meal_price_string, priceStudents, getAppContext().getString(R.string.item_meal_price_string_fixed));
                }
        }
    }

    private boolean filterMeal(Meal meal) {
        return (filterAdditives(meal) || filterAllergens(meal) || filterLifestyle(meal));
    }

    private boolean filterAdditives(Meal meal) {
        Set<String> additives = UserSettings.getAdditives();

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (additives.contains(meal.getAllergens().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterAllergens(Meal meal) {
        Set<String> allergens = UserSettings.getAllergens();

        for (int i = 0; i < meal.getAllergens().size(); i++) {
            if (allergens.contains(meal.getAllergens().get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean filterLifestyle(Meal meal) {
        String lifestyle = UserSettings.getLifestyle();

        switch (lifestyle) {
            case LIFESTYLE_VEGETARIAN:
                if ((!meal.getBadges().contains(MEAL_BADGE_VEGETARIAN)) && (!meal.getBadges().contains(MEAL_BADGE_VEGAN))) {
                    return true;
                }
                break;
            case LIFESTYLE_VEGAN:
                if (!meal.getBadges().contains(MEAL_BADGE_VEGAN)) {
                    return true;
                }
                break;
            case LIFESTYLE_LEVEL_FIVE_VEGAN:
                return true;
        }
        return false;
    }

    private String round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}