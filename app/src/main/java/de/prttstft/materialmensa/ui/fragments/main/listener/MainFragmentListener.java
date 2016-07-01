package de.prttstft.materialmensa.ui.fragments.main.listener;

import de.prttstft.materialmensa.model.Meal;

public interface MainFragmentListener {
    void addMeal(Meal meal);

    void connectionError();

    void filteredMeal();

    void onComplete();

    void sendMealScore(Meal meal);

    void sendUserVote(Meal meal);
}