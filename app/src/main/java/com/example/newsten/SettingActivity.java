package com.example.newsten;

import android.app.Activity;
import android.os.Bundle;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content,new MyPrefSettingFragment()).commit();
    }
}
