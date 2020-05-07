package com.example.newsten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;


public class CoronaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.corona_fragment,container,false);

        final TextView countryTv = view.findViewById(R.id.country_tv);
        final TextView deathsTv = view.findViewById(R.id.deaths_tv);
        final TextView confirmedTv = view.findViewById(R.id.confirm_tv);
        final TextView recvoryTv= view.findViewById(R.id.recover_tv);
        final TextView activeTv = view.findViewById(R.id.active_tv);

        CoronaViewModel coronaViewModel = ViewModelProviders.of(this).get(CoronaViewModel.class);
coronaViewModel.getCoronaList().observe(getViewLifecycleOwner(), new Observer<List<CoronaInfo>>() {
    @Override
    public void onChanged(List<CoronaInfo> coronaInfos) {


        CoronaInfo coronaInfo = coronaInfos.get(coronaInfos.size()-1);
        countryTv.setText(coronaInfo.getCountry());
        deathsTv.setText(coronaInfo.getDeath());
        confirmedTv.setText(coronaInfo.getConfirmed());
        recvoryTv.setText(coronaInfo.getRecovered());
        activeTv.setText(coronaInfo.getActive());

    }
});


        return view;
    }
}
