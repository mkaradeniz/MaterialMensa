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
import timber.log.Timber;

import static de.prttstft.materialmensa.MyApplication.getAppContext;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_ATRIUM;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_BASILICA;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_BISTRO_HOTSPOT;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.constants.APIConstants.API_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ATRIUM;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_BASILICA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_HOTSPOT;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_ID_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_STATUS_STARTS_WITH_OPEN_DE;
import static de.prttstft.materialmensa.constants.RestaurantConstants.RESTAURANT_STATUS_STARTS_WITH_OPEN_EN;

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
                Timber.e(e, e.getMessage());
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
            case RESTAURANT_ID_HOTSPOT:
                return API_RESTAURANT_BISTRO_HOTSPOT;
            case RESTAURANT_ID_BASILICA:
                return API_RESTAURANT_BASILICA;
            case RESTAURANT_ID_ATRIUM:
                return API_RESTAURANT_ATRIUM;
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
        } else if (restaurant.getBistroHotspot() != null) {
            String status = restaurant.getBistroHotspot().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_HOTSPOT, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_HOTSPOT, getOpeningTime(status));
            }
        } /*else if (restaurant.getMensaBasilica() != null) {
            String status = restaurant.getMensaBasilica().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_BASILICA, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_BASILICA, getOpeningTime(status));
            }
        } else if (restaurant.getMensaAtrium() != null) {
            String status = restaurant.getMensaAtrium().getStatus();

            if (restaurantOpen(status)) {
                listener.restaurantOpen(RESTAURANT_ID_ATRIUM, getClosingTime(status));
            } else {
                listener.restaurantClosed(RESTAURANT_ID_ATRIUM, getOpeningTime(status));
            }
        }*/
    }

    private boolean restaurantOpen(String status) {
        return (status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPEN_DE) || status.startsWith(RESTAURANT_STATUS_STARTS_WITH_OPEN_EN));
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
                String suffix = getAppContext().getString(R.string.drawer_main_restaurant_status_opening_time_suffix);
                String sHours = Utilities.addLeadingZero(hours, 2);
                String sMinutes = Utilities.addLeadingZero(minutes, 2);

                return getAppContext().getString(R.string.drawer_main_restaurant_status_opening_time, sHours, sMinutes, suffix);
            } catch (NumberFormatException ignored) {

            }
        }
        return null;
    }
}