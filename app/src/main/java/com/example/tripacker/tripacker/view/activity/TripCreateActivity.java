package com.example.tripacker.tripacker.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.json.JSONException;

/**
 * Created by angelagao on 4/29/16.
 */
public class TripCreateActivity extends AppCompatActivity implements AsyncCaller {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_create);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);
    }
    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {

    }
}
