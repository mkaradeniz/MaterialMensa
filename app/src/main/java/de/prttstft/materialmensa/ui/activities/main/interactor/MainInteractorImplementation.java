package de.prttstft.materialmensa.ui.activities.main.interactor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Restaurant;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.MyApplication.getAppContext;
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
                L("Error getting restaurant status: " + e);
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
            String status = restaurant.getMensaAcademicaPaderborn().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_ACADEMICA, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_ACADEMICA, getOpeningTime(status));
            }
        } else if (restaurant.getMensaForumPaderborn() != null) {

            String status = restaurant.getMensaForumPaderborn().getStatus();
            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_FORUM, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_FORUM, getOpeningTime(status));
            }
        } else if (restaurant.getCafete() != null) {
            String status = restaurant.getCafete().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_CAFETE, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_CAFETE, getOpeningTime(status));
            }
        } else if (restaurant.getMensula() != null) {
            String status = restaurant.getMensula().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_MENSULA, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_MENSULA, getOpeningTime(status));
            }
        } else if (restaurant.getOneWaySnack() != null) {
            String status = restaurant.getOneWaySnack().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_ONE_WAY_SNACK, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_ONE_WAY_SNACK, getOpeningTime(status));
            }
        } else if (restaurant.getGrillCafe() != null) {
            String status = restaurant.getGrillCafe().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_GRILL_CAFE, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_GRILL_CAFE, getOpeningTime(status));
            }
        }
    }

    private boolean restaurantOpen(String status) {
        return !(status.startsWith(RESTAURANT_STATUS_CLOSED) || status.startsWith(RESTAURANT_STATUS_CLOSED_DE) || status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPENS) || status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPENS_DE));
    }

    private String getClosingTime(String status) {
        Pattern pattern = Pattern.compile("(\\D*)(\\d{1,2})(\\D*)(\\d{0,2})(\\D*)");
        Matcher matcher = pattern.matcher(status);

        if (matcher.find()) {
            int hours = 0;
            int minutes;

            if (matcher.group(4).equals("")) {
                try {
                    minutes = Integer.parseInt(matcher.group(2));
                    return getAppContext().getString(R.string.drawer_main_restaurant_status_closing_time, Utilities.addLeadingZero(hours, 2), Utilities.addLeadingZero(minutes, 2));
                } catch (NumberFormatException ignored) {
                }
            } else {
                try {
                    hours = Integer.parseInt(matcher.group(2));
                    minutes = Integer.parseInt(matcher.group(4));
                    return getAppContext().getString(R.string.drawer_main_restaurant_status_closing_time, Utilities.addLeadingZero(hours, 2), Utilities.addLeadingZero(minutes, 2));
                } catch (NumberFormatException ignored) {

                }
            }
        }
        return null;
    }

    private String getOpeningTime(String status) {
        Pattern pattern = Pattern.compile("(\\D*)(\\d{1,2})(\\D*)(\\d{0,2})(\\D*)");
        Matcher matcher = pattern.matcher(status);

        if (matcher.find()) {
            int hours;
            int minutes;

            try {
                hours = Integer.parseInt(matcher.group(2));
                minutes = Integer.parseInt(matcher.group(4));
                return getAppContext().getString(R.string.drawer_main_restaurant_status_opening_time, Utilities.addLeadingZero(hours, 2), Utilities.addLeadingZero(minutes, 2));
            } catch (NumberFormatException ignored) {

            }
        }
        return null;
    }
}