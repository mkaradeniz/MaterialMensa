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

import static de.prttstft.materialmensa.constants.APIConstants.API_BASE_URL;
import static de.prttstft.materialmensa.constants.APIConstants.API_GET_MEALS;
import static de.prttstft.materialmensa.constants.APIConstants.API_GET_RESTAURANT_STATUS;

public interface MensaAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    MensaAPI mensaAPI = retrofit.create(MensaAPI.class);

    @GET(API_GET_MEALS)
    Observable<List<Meal>> getMeals(@Query("date") String page, @Query("restaurant") String restaurant);

    @GET(API_GET_RESTAURANT_STATUS)
    Observable<Restaurant> getRestaurantStatus(@Query("restaurant") String restaurant);
}