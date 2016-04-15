package de.prttstft.materialmensa.ui.activities.main.interactor;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.network.MensaAPI;
import de.prttstft.materialmensa.ui.activities.main.listener.MainListener;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static de.prttstft.materialmensa.extras.Utilities.L;

public class MainInteractorImplementation implements MainInteractor {
    @Override
    public void onCreate(final MainListener listener) {
        Observable<Meal> observable = MensaAPI.mensaAPI.getAcademica().flatMap(new Func1<List<Meal>, Observable<Meal>>() {
            @Override
            public Observable<Meal> call(List<Meal> iterable) {
                return Observable.from(iterable);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Meal>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                L("Error: " + e.toString());
            }

            @Override
            public void onNext(Meal meal) {
                listener.addMeal(meal);
            }
        });
    }
}