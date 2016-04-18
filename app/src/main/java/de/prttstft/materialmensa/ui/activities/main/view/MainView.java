package de.prttstft.materialmensa.ui.activities.main.view;

public interface MainView {
    void restaurantClosed(int restaurant, String openingTime);

    void restaurantOpen(int restaurant, String closingTime);
}