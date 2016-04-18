package de.prttstft.materialmensa.ui.activities.main.presenter;

import de.prttstft.materialmensa.ui.activities.main.interactor.MainInteractor;
import de.prttstft.materialmensa.ui.activities.main.interactor.MainInteractorImplementation;
import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;

import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ONE_WAY_SNACK;

public class MainPresenterImplementation implements MainPresenter, MainListener {
    private MainInteractor interactor;
    private MainView view;

    public MainPresenterImplementation(MainView view) {
        this.view = view;
        interactor = new MainInteractorImplementation();
    }

    @Override
    public void getRestaurantStatus() {
        // There has to be a better way.
        interactor.getRestaurantStatus(this, RESTAURANT_ID_ACADEMICA);
        interactor.getRestaurantStatus(this, RESTAURANT_ID_FORUM);
        interactor.getRestaurantStatus(this, RESTAURANT_ID_CAFETE);
        interactor.getRestaurantStatus(this, RESTAURANT_ID_MENSULA);
        interactor.getRestaurantStatus(this, RESTAURANT_ID_ONE_WAY_SNACK);
        interactor.getRestaurantStatus(this, RESTAURANT_ID_GRILL_CAFE);
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