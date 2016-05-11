package de.prttstft.materialmensa.ui.activities.main.presenter;

import de.prttstft.materialmensa.constants.RestaurantConstants;
import de.prttstft.materialmensa.ui.activities.main.interactor.MainInteractor;
import de.prttstft.materialmensa.ui.activities.main.interactor.MainInteractorImplementation;
import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;

public class MainPresenterImplementation implements MainPresenter, MainListener {
    private MainInteractor interactor;
    private MainView view;

    public MainPresenterImplementation(MainView view) {
        this.view = view;
        interactor = new MainInteractorImplementation();
    }

    @Override
    public void getRestaurantStatus() {
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_ACADEMICA);
        //interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_ATRIUM);
        //interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_BASILICA);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_CAFETE);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_FORUM);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_GRILL_CAFE);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_HOTSPOT);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_MENSULA);
        interactor.getRestaurantStatus(this, RestaurantConstants.RESTAURANT_ID_ONE_WAY_SNACK);
    }

    @Override
    public void restaurantOpen(int restaurant, String closingTime) {
        view.restaurantOpen(restaurant, closingTime);
    }

    @Override
    public void restaurantClosed(int restaurant, String openingTime) {
        view.restaurantClosed(restaurant, openingTime);
    }
}