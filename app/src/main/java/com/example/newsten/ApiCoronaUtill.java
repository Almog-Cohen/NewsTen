package com.example.newsten;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCoronaUtill {

    static String CORONA_BASE_URL= "https://api.covid19api.com/live/country/";

    public static ApiCorona getRetrofitApi(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(CORONA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiCorona apiCorona = retrofit.create(ApiCorona.class);
        return apiCorona;
    }
}
