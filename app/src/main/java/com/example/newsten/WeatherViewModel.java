package com.example.newsten;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private static MutableLiveData<List<Weather>> forecastList;

    public MutableLiveData<List<Weather>> getForecastList(double lat, double lon) {
        if(forecastList == null){
            forecastList = new MutableLiveData<>();
            loadForecasts(lat, lon);
        }
        return forecastList;
    }

    private void loadForecasts(double lat, double lon) {
        Api api = ApiUtil.getRetrofitApi("weather");

        Call<ArrayList<Weather>> call = api.getForecasts(String.valueOf(lat), String.valueOf(lon), "14", "a3622afd0ff041b68c9c853f2e730e19");

        call.enqueue(new Callback<ArrayList<Weather>>() {
            @Override
            public void onResponse(Call<ArrayList<Weather>> call, Response<ArrayList<Weather>> response) {
                forecastList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Weather>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}
