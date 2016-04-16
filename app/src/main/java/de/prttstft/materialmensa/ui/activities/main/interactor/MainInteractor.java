package de.prttstft.materialmensa.ui.activities.main.interactor;

import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;

public interface MainInteractor {
    void getRestaurantStatus(MainListener listener, int restaurant);
}
