package com.example.newsten;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtil {

    static final String WEATHER_BASE_URL = "https://api.weatherbit.io/v2.0/forecast/daily/";

    static final Type WEATHER_ARRAY_LIST_CLASS_TYPE = ArrayList.class; // (new ArrayList<Weather>()).getClass()

    public static Api getRetrofitApi(String apiType){
        Gson gson;
        Retrofit retrofit = null;

        if(apiType.equals("weather")){
            gson = new GsonBuilder()
                    .registerTypeAdapter(WEATHER_ARRAY_LIST_CLASS_TYPE, new WeatherJsonDeserializer())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(WEATHER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofit.create(Api.class);
    }
}
