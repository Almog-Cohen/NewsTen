package com.example.newsten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";

    AlarmManager alarmManager;

    final int SETTING_REQUEST = 1;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.news_container, new NewsFragment(), NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, new WeatherFragment(), WEATHER_FRAGMENT_TAG);
        /*transaction.add(R.id.weather_container,new ForecastFragment(),WEATHER_FRAGMENT_TAG);*/
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingActivity.class), SETTING_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_REQUEST) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            int time=Integer.parseInt(sp.getString("list_preference",""));
            Intent intent = new Intent(MainActivity.this,NotifcationReciver.class);
            intent.putExtra("time",time);
            Log.d("ALMOGIE","Hello"+time);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                if (time!=0) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pendingIntent);
                    Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
                    Log.d("ALMOGIE", "tiem");
                }
        }
    }
}
