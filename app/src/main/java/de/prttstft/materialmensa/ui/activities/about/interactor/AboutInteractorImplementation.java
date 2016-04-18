package de.prttstft.materialmensa.ui.activities.about.interactor;

import de.prttstft.materialmensa.items.LibraryItems;
import de.prttstft.materialmensa.model.Library;
import de.prttstft.materialmensa.ui.activities.about.listener.AboutListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.extras.Utilities.L;

public class AboutInteractorImplementation implements AboutInteractor {

    @Override
    public void getLibraries(final AboutListener listener) {
        Observable<Library> observable = Observable.from(LibraryItems.getLibs())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Library>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                L("Error observing libraries: " + e);
            }

            @Override
            public void onNext(Library library) {
                listener.addLibrary(library);
            }
        });
    }
}