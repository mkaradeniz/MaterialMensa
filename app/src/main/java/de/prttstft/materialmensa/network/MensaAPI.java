package de.prttstft.materialmensa.network;

import java.util.List;

import de.prttstft.materialmensa.model.Meal;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_BASE_URL;
import static de.prttstft.materialmensa.extras.Constants.APIConstants.API_BASIC_GET_ACADEMICA;

public interface MensaAPI {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    MensaAPI mensaAPI = retrofit.create(MensaAPI.class);

    @GET(API_BASIC_GET_ACADEMICA)
    Observable<List<Meal>> getAcademica();
}