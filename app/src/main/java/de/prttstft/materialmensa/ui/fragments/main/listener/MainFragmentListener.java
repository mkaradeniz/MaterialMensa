package de.prttstft.materialmensa.ui.fragments.main.listener;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentListener {
    void addMeal(Meal meal);

    void onComplete();

    void restaurantClosed(int restaurant);
}
