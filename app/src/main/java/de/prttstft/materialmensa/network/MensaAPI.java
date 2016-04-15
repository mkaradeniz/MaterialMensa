package de.prttstft.materialmensa.network;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;
import de.prttstft.materialmensa.model.Restaurant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_BASE_URL;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_GET;

public interface MensaAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    MensaAPI mensaAPI = retrofit.create(MensaAPI.class);

    @GET(API_GET)
    Observable<List<Meal>> getAcademica(@Query("date") String page, @Query("restaurant") String restaurant);

    @GET("access2.php?id=karadeniz-android-o48Ken7eT&getopeningstatus=1&restaurant=")
    Observable<Restaurant> getRestaurantStatus(@Query("restaurant") String restaurant);
}