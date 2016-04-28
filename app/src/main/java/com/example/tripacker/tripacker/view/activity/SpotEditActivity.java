package com.example.tripacker.tripacker.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.json.JSONException;

/**
 * Created by angelagao on 4/28/16.
 */
public class SpotEditActivity extends AppCompatActivity implements AsyncCaller {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_edit);

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {

    }
}
