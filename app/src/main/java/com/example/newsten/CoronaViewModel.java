package com.example.newsten;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoronaViewModel extends ViewModel {

    private MutableLiveData<List<CoronaInfo>> coronaList;

    public LiveData<List<CoronaInfo>> getCoronaList() {
            if (coronaList == null){
                coronaList = new MutableLiveData<>();
                loadCoronaList();
            }
            return coronaList;

    }

    private void loadCoronaList(){

        ApiCorona apiCorona = ApiCoronaUtill.getRetrofitApi();

        Call<List<CoronaInfo>> call = apiCorona.getCoronaDetails();

        call.enqueue(new Callback<List<CoronaInfo>>() {
            @Override
            public void onResponse(Call<List<CoronaInfo>> call, Response<List<CoronaInfo>> response) {
                coronaList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CoronaInfo>> call, Throwable t) {

            }
        });

    }
}
