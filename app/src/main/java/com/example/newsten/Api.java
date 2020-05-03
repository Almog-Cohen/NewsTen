package com.example.newsten;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api{
//    @GET(".")
//    Call<ArrayList<News>> getArticles(@Query("country") String country,
//                                         @Query("apiKey") String apiKey);

    @GET(".")
    Call<ArrayList<Weather>> getForecasts(@Query("lat") String lat,
                                          @Query("lon") String lon,
                                          @Query("days") String days,
                                          @Query("key") String key);

}
