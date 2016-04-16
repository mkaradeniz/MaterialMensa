package de.prttstft.materialmensa.ui.fragments.main.interactor;

import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;

public interface MainFragmentInteractor {
    void getMeals(MainFragmentListener listener, int page, int restaurant);
}