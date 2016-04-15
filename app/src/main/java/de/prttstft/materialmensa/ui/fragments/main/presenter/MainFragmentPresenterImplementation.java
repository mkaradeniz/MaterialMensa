package de.prttstft.materialmensa.ui.fragments.main.presenter;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractor;
import de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation;
import de.prttstft.materialmensa.ui.fragments.main.listener.MainFragmentListener;
import de.prttstft.materialmensa.ui.fragments.main.view.MainFragmentView;

public class MainFragmentPresenterImplementation implements MainFragmentPresenter, MainFragmentListener {
    private MainFragmentInteractor interactor;
    private MainFragmentView view;

    public MainFragmentPresenterImplementation(MainFragmentView view) {
        this.view = view;
        interactor = new MainFragmentInteractorImplementation();
    }

    @Override
    public void onCreate(int page, int restaurant) {
        if (view != null) {
            view.showProgress();
        }

        interactor.onCreate(this, page, restaurant);
    }

    @Override
    public void addMeal(Meal meal) {
        if (view != null) {
            view.addMeal(meal);
            view.hideProgress();
        }
    }
}
