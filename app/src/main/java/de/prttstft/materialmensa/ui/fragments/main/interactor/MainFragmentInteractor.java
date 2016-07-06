package de.prttstft.materialmensa.ui.fragments.main.interactor;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;

public interface MainFragmentInteractor {
    void downvoteMeal(Meal meal);

    void getMeals(MainFragmentListener listener, int page, int restaurant);

    void upvoteMeal(Meal meal);
}