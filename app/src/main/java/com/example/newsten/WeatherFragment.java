package com.example.newsten;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class WeatherFragment extends Fragment {

    final private String TAG = "WeatherFragment";
    final private int LOC_PERMISSION = 1;

    final private double DEFAULT_LAT = 32.109333;
    final private double DEFAULT_LON = 34.855499;

    private double lat = DEFAULT_LAT;
    private double lon = DEFAULT_LON;

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recycle_weather);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocationUpdates();


            } else {
                // Permission to access the location is missing. Show rationale and request permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOC_PERMISSION);


            }
        }
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOC_PERMISSION) {

            return;
        }
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getContext(), "Please approve permission", Toast.LENGTH_SHORT).show();
        } else getLocationUpdates();

    }

    private void updateWeatherList(double lat, double lon) {

        //setting up the model
        WeatherViewModel model = new ViewModelProvider(this).get(WeatherViewModel.class);

        model.getForecastList(lat, lon).observe(this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(List<Weather> weathers) {

                adapter = new WeatherAdapter(getActivity(), weathers);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    //Update the location in our weatherview fragment
    private void getLocationUpdates() {

        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(getContext());


        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location =locationResult.getLastLocation();
                lat = location.getLatitude();
                lon = location.getLongitude();

                updateWeatherList(lat,lon);


            }
        };

        LocationRequest request =LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);

        if (Build.VERSION.SDK_INT>=23 && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            client.requestLocationUpdates(request,callback,null);
        else if (Build.VERSION.SDK_INT<=22 && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            client.requestLocationUpdates(request,callback,null);

    }


}


