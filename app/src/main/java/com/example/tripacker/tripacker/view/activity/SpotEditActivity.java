package com.example.tripacker.tripacker.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/28/16.
 */
public class SpotEditActivity extends AppCompatActivity implements AsyncCaller {
    private SpotEntity spotEntity = new SpotEntity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> stuff = bundle.getStringArrayList("spotId");

        //get setSpotID
        spotEntity.setSpotId(stuff.get(0));
        spotEntity.setName(stuff.get(1));
        spotEntity.setAddress(stuff.get(2));
        spotEntity.setGeo_latitude(stuff.get(3));
        spotEntity.setGeo_longitude(stuff.get(4));


        //set EditText pre-update
        EditText spotName = (EditText) findViewById(R.id.spotName_edit);
        spotName.setText(spotEntity.getName());

        EditText spotAddres = (EditText) findViewById(R.id.spotAddress_edit);
        spotAddres.setText(spotEntity.getAddress());

        EditText spotinfo = (EditText) findViewById(R.id.spotInfo_edit);
        spotinfo.setText(spotEntity.getAddress());

        //Put request
        Button editSpot = (Button) findViewById(R.id.editSpot);
        editSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContent(spotEntity.getSpotId());
            }
        });
    }

    private void updateContent(String spotId) {

        EditText NewSpotName = (EditText) findViewById(R.id.spotName_edit);
        EditText NewspotAddres = (EditText) findViewById(R.id.spotAddress_edit);

        String name = NewSpotName.getText().toString();
        String categoryId = "11";
        String cityId = "1";
        String address = NewspotAddres.getText().toString();
        String geoLatitude = spotEntity.getGeo_latitude();
        String geoLongitude = spotEntity.getGeo_longitude();
        String description = "update test";
        String img="/res/drawable/tie";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("spotName", name));
        nameValuePairs.add(new BasicNameValuePair("categoryId", categoryId));
        nameValuePairs.add(new BasicNameValuePair("cityId", cityId));
        nameValuePairs.add(new BasicNameValuePair("address", address));
        nameValuePairs.add(new BasicNameValuePair("geoLatitude", geoLatitude));
        nameValuePairs.add(new BasicNameValuePair("geoLongitude", geoLongitude));
        nameValuePairs.add(new BasicNameValuePair("description", description));
        nameValuePairs.add(new BasicNameValuePair("img", img));


        APIConnection.SetAsyncCaller(this, getApplicationContext());
        APIConnection.editSpot(spotId, nameValuePairs);
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        String  response = result.toString();

        try {
//            JSONObject finalResult = new JSONObject(tokener);
            Log.e("SpotEdit Put result------>", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
