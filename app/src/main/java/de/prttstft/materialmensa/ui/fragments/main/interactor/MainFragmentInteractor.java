package de.prttstft.materialmensa.ui.fragments.main.interactor;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;

public interface MainFragmentInteractor {
    void downvoteMeal(Meal meal);

    void getMeals(MainFragmentListener listener, int page, int restaurant);

    void getSocialData(MainFragmentListener listener, List<Meal> meals);

    void upvoteMeal(Meal meal);
}