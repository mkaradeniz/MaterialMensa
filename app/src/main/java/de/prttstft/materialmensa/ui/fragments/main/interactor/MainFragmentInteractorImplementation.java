package de.prttstft.materialmensa.ui.fragments.main.interactor;

import org.joda.time.DateTime;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.model.Restaurant;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Utilities.L;

public class MainFragmentInteractorImplementation implements MainFragmentInteractor {
    @Override
    public void onCreate(final MainFragmentListener listener, final int page, final int restaurant) {
        test(listener, restaurant);

        Observable<Meal> observable = MensaAPI.mensaAPI.getAcademica(getDateString(page), getRestaurantString(restaurant)).flatMap(new Func1<List<Meal>, Observable<Meal>>() {
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
                listener.addMeal(meal);
            }
        });
    }

    private void test(final MainFragmentListener listener, int restaurant) {
        Observable<Restaurant> restaurantObservable = MensaAPI.mensaAPI.getRestaurantStatus(getRestaurantString(restaurant))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        restaurantObservable.subscribe(new Observer<Restaurant>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                L("Error: " + e);
            }

            @Override
            public void onNext(Restaurant restaurant) {
                if (restaurant.getMensaAcademicaPaderborn() != null) {
                    if (restaurant.getMensaAcademicaPaderborn().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_ACADEMICA);
                    }
                } else if (restaurant.getMensaForumPaderborn() != null) {
                    if (restaurant.getMensaForumPaderborn().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_FORUM);
                    }
                } else if (restaurant.getCafete() != null) {
                    if (restaurant.getCafete().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_CAFETE);
                    }
                } else if (restaurant.getMensula() != null) {
                    if (restaurant.getMensula().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_MENSULA);
                    }
                } else if (restaurant.getOneWaySnack() != null) {
                    if (restaurant.getOneWaySnack().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK);
                    }
                } else if (restaurant.getGrillCafe() != null) {
                    if (restaurant.getGrillCafe().getStatus().equals("Zurzeit geschlossen")) {
                        listener.restaurantClosed(NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE);
                    }
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
            case NAVIGATION_DRAWER_RESTAURANT_ACADEMICA:
                return API_RESTAURANT_ACADEMICA;
            case NAVIGATION_DRAWER_RESTAURANT_FORUM:
                return API_RESTAURANT_FORUM;
            case NAVIGATION_DRAWER_RESTAURANT_CAFETE:
                return API_RESTAURANT_CAFETE;
            case NAVIGATION_DRAWER_RESTAURANT_MENSULA:
                return API_RESTAURANT_MENSULA;
            case NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK:
                return API_RESTAURANT_ONE_WAY_SNACK;
            case NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE:
                return API_RESTAURANT_GRILL_CAFE;
            case NAVIGATION_DRAWER_RESTAURANT_CAMPUS_DOENER:
                return API_RESTAURANT_CAMPUS_DOENER;
            default:
                return API_RESTAURANT_ACADEMICA;
        }
    }
}