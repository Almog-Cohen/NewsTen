package com.example.newsten;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiCorona {

@GET("israel/status/confirmed")
    Call<List<CoronaInfo>> getCoronaDetails();

}


