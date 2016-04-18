package de.prttstft.materialmensa.ui.activities.about.presenter;

import de.prttstft.materialmensa.model.Library;
import de.prttstft.materialmensa.ui.activities.about.interactor.AboutInteractor;
import de.prttstft.materialmensa.ui.activities.about.interactor.AboutInteractorImplementation;
import de.prttstft.materialmensa.ui.activities.about.listener.AboutListener;
import de.prttstft.materialmensa.ui.activities.about.view.AboutView;

public class AboutPresenterImplementation implements AboutPresenter, AboutListener {
    private AboutInteractor interactor;
    private AboutView view;

    public AboutPresenterImplementation(AboutView view) {
        this.view = view;
        interactor = new AboutInteractorImplementation();
    }

    @Override
    public void getLibraries() {
        interactor.getLibraries(this);
    }

    @Override
    public void addLibrary(Library library) {
        view.addLibrary(library);
    }
}
