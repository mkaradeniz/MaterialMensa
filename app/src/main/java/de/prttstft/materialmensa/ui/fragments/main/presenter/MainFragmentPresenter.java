package de.prttstft.materialmensa.ui.fragments.main.presenter;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentPresenter {
    void downvoteMeal(Meal meal);

    void getMeals(int day, int restaurant);

    void upvoteMeal(Meal meal);
}