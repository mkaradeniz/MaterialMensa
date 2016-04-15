package de.prttstft.materialmensa.ui.fragments.main.view;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentView {
    void addMeal(Meal meal);

    void hideEmpty();

    void hideProgress();

    void restaurantClosed(int restaurant);

    void showProgress();
}
