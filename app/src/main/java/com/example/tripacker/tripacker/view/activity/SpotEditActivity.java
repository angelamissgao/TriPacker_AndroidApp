package com.example.tripacker.tripacker.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Allow user to modify and edit one spot within this activity
 */
public class SpotEditActivity extends ActionBarActivity implements AsyncCaller {
    private static final String TAG = "SpotEditActivity";
    private SpotEntity spotEntity = new SpotEntity();
    private Spinner spinner1;
    private EditText addAttribute, spotName, spotAddres, spotinfo;
    private Button editSpot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_edit);

        //Set Section Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
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
        spotEntity.setDescription(stuff.get(5));

        setView();
    }

    private void setView() {
        spotName = (EditText) findViewById(R.id.spotName_edit);
        spotAddres = (EditText) findViewById(R.id.spotAddress_edit);
        spotinfo = (EditText) findViewById(R.id.spotInfo_edit);
        addAttribute = (EditText) findViewById(R.id.addSpotAttr);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        editSpot = (Button) findViewById(R.id.editSpot);

        //set EditText pre-update
        spotName.setText(spotEntity.getName());
        spotAddres.setText(spotEntity.getAddress());
        spotinfo.setText(spotEntity.getDescription());

        //Spinner
        addAttribute.setVisibility(View.INVISIBLE);
        spinner1.setPrompt("Add one attribute:");
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    addAttribute.setVisibility(View.VISIBLE);
                } else {
                    addAttribute.setVisibility(View.INVISIBLE);
                }
                spotEntity.setCategory_id(String.valueOf(position));
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + String.valueOf(position) + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(),
                        "Please select one!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Put request
        editSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContent(spotEntity.getSpotId());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            updateContent(spotEntity.getSpotId());
            setResult(200, null);
            finish();
        }

        if (id == android.R.id.home) {
            setResult(400, null);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateContent(String spotId) {
        EditText NewSpotName = (EditText) findViewById(R.id.spotName_edit);
        EditText NewspotAddres = (EditText) findViewById(R.id.spotAddress_edit);
        EditText NewspotDescription = (EditText) findViewById(R.id.spotInfo_edit);

        String name = NewSpotName.getText().toString();
        String categoryId = spotEntity.getCategory_id();
        String cityId = "1";
        String address = NewspotAddres.getText().toString();
        String geoLatitude = spotEntity.getGeo_latitude();
        String geoLongitude = spotEntity.getGeo_longitude();
        String description = NewspotDescription.getText().toString();
        String img = "/res/drawable/tie";

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
        String response = result.toString();

        try {
            Log.e("SpotEdit Put result------>", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}
