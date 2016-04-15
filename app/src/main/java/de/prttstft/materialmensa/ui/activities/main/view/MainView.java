package de.prttstft.materialmensa.ui.activities.main.view;

import de.prttstft.materialmensa.model.Meal;

public interface MainView {
    void addMeal(Meal meal);

    void showProgress();

    void hideProgress();
}
