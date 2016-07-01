package de.prttstft.materialmensa.ui.fragments.main.presenter;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentPresenter {
    void downvoteMeal(Meal meal);

    void getMeals(int day, int restaurant);

    void getSocialData(List<Meal> meals);

    void upvoteMeal(Meal meal);
}
