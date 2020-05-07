package com.example.newsten;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String WEATHER_FRAGMENT_TAG = "weather_fragment";
    final String CORONA_FRAGMENT_TAG = "corona_fragment";
    boolean isCorona = true;
    AlarmManager alarmManager;

    final int SETTING_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.news_container, new NewsFragment(), NEWS_FRAGMENT_TAG);
        transaction.add(R.id.weather_container, new WeatherFragment(), WEATHER_FRAGMENT_TAG);
        transaction.commit();

        final Button coronaBtn = findViewById(R.id.corona_btn);
        coronaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorona) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.weather_container, new CoronaFragment(), CORONA_FRAGMENT_TAG).commit();
                    isCorona = false;
                    coronaBtn.setText("Switch news");


                } else if (!isCorona) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.weather_container, new WeatherFragment(), WEATHER_FRAGMENT_TAG).commit();
                    isCorona = true;
                    coronaBtn.setText("Switch corona");


                }

            }
        });

    }


    // Creating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Options selected in our menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingActivity.class), SETTING_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_REQUEST) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            int time = 0;
            if (!sp.getString("list_preference", "").equals(""))
                time = Integer.parseInt(sp.getString("list_preference", ""));

            String category = sp.getString("list_preference_categories", "");
            String country = sp.getString("list_preference_countries", "");
            if (country.equals(""))
                country = "us";

            Intent intent = new Intent(MainActivity.this, NotifcationReciver.class);
            intent.putExtra("time", time);
            intent.putExtra("category_notif", category);
            intent.putExtra("country", country);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            if (time != 0) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000, pendingIntent);


            }
        }
    }

}
