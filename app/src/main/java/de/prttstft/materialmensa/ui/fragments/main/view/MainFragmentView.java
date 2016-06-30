package de.prttstft.materialmensa.ui.fragments.main.view;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentView {
    void addMeal(Meal meal);

    void onComplete();

    void hideConnectionError();

    void hideEmpty();

    void hideFiltered();

    void hideProgress();

    void showConnectionError();

    void showEmpty();

    void showFiltered();

    void showProgress();

    void updateMealWithScore(Meal meal);

    void updateMealWithVote(Meal meal);
}
