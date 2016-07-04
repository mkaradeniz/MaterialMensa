package de.prttstft.materialmensa.ui.fragments.main.view;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentView {
    void addMeal(Meal meal);

    void onCompleted();

    void hideConnectionError();

    void hideEmpty();

    void hideFiltered();

    void hideProgress();

    void sendSocialData(Meal meal);

    void showConnectionError();

    void showEmpty();

    void showFiltered();

    void showProgress();
}
