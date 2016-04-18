package de.prttstft.materialmensa.ui.activities.main.interactor;

import de.prttstft.materialmensa.model.Restaurant;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAMPUS_DOENER;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Constants.RestaurantStatusConstants.RESTAURANT_STATUS_CLOSED;
import static de.prttstft.materialmensa.extras.Constants.RestaurantStatusConstants.RESTAURANT_STATUS_CLOSED_DE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantStatusConstants.RESTAURANT_STATUS_STARTS_WITH_OPENS;
import static de.prttstft.materialmensa.extras.Constants.RestaurantStatusConstants.RESTAURANT_STATUS_STARTS_WITH_OPENS_DE;
import static de.prttstft.materialmensa.extras.Utilities.L;

public class MainInteractorImplementation implements MainInteractor {

    @Override
    public void getRestaurantStatus(final MainListener listener, int restaurant) {
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
                sendRestaurantStatus(listener, restaurant);
            }
        });
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

    private void sendRestaurantStatus(MainListener listener, Restaurant restaurant) {
        if (restaurant.getMensaAcademicaPaderborn() != null) {
            if (restaurantOpen(restaurant.getMensaAcademicaPaderborn().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_ACADEMICA);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_ACADEMICA);
            }
        } else if (restaurant.getMensaForumPaderborn() != null) {
            if (restaurantOpen(restaurant.getMensaForumPaderborn().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_FORUM);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_FORUM);
            }
        } else if (restaurant.getCafete() != null) {
            if (restaurantOpen(restaurant.getCafete().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_CAFETE);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_CAFETE);
            }
        } else if (restaurant.getMensula() != null) {
            if (restaurantOpen(restaurant.getMensula().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_MENSULA);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_MENSULA);
            }
        } else if (restaurant.getOneWaySnack() != null) {
            if (restaurantOpen(restaurant.getOneWaySnack().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_ONE_WAY_SNACK);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_ONE_WAY_SNACK);
            }
        } else if (restaurant.getGrillCafe() != null) {
            if (restaurantOpen(restaurant.getGrillCafe().getStatus())) {
                listener.restaurantOpen(RESTAURANT_ID_GRILL_CAFE);
            } else {
                listener.restaurantClosed(RESTAURANT_ID_GRILL_CAFE);
            }
        }
    }

    private boolean restaurantOpen(String status) {
        return !(status.startsWith(RESTAURANT_STATUS_CLOSED) || status.startsWith(RESTAURANT_STATUS_CLOSED_DE) || status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPENS) || status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPENS_DE));
    }
}