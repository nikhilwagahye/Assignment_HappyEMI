package com.assignmenthappyemi.retrofit;


import com.assignmenthappyemi.model.Temperature;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET(ApiConstants.GET_FORECAST_TEMPERATURE)
    Call<Temperature> getTemperatureData(@Query("key") String key, @Query("q") String queryCity, @Query("days") int days);
}
