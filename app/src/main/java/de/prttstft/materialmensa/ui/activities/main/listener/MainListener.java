package de.prttstft.materialmensa.ui.activities.main.listener;

public interface MainListener {
    void restaurantClosed(int restaurant, String openingTime);

    void restaurantOpen(int restaurant, String closingTime);
}
