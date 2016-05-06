package com.example.tripacker.tripacker.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotCreateActivity extends ActionBarActivity implements AsyncCaller {

    private static final String TAG = "SpotCreateActivity";

    ProgressDialog progress;
    //View Element
    EditText spotName;
    EditText spotAddress;
    EditText spotDescription;
    Button button_showMap;

    //Spot on Google maps
    private GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;
    private LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;

    //Spot Model
    private SpotEntity newspot = new SpotEntity();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_create);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        //Set View
        setView();

        //Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCreate);

        // Getting a reference to the map and search by address

        googleMap = mapFragment.getMap();
        button_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location
                EditText etLocation = (EditText) findViewById(R.id.startDate);

                // Getting user input location
                String location = etLocation.getText().toString();

                new GeocoderTask().execute(location);
            }
        });

        //Getting Current location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS}, 10);
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        Button button_getCurrentGps = (Button) findViewById(R.id.showCurrentLocation);
        button_getCurrentGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentLocation();
                Toast.makeText(getApplicationContext(), "get Current Location", Toast.LENGTH_LONG).show();
            }
        });

        //Upload Image Feature
        Button button_uploadImage = (Button) findViewById(R.id.uploadImage);
        button_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadImage = new Intent(getApplicationContext(), UploadImageActivity.class);
                startActivity(uploadImage);
            }
        });
    }

    private void setView() {
        spotName = (EditText) findViewById(R.id.tripNameInput);
        spotAddress = (EditText) findViewById(R.id.startDate);
        spotDescription = (EditText) findViewById(R.id.endDate);
        button_showMap = (Button) findViewById(R.id.showSpotMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            sendContent();
            setResult(200, null);
//            finish();
        }

        if (id == android.R.id.home) {
            setResult(400, null);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendContent() {
        String name = spotName.getText().toString();
        String tags = "history";
        String categoryId = "11";
        String cityId = "1";
        String address = spotAddress.getText().toString();
        String geoLatitude = newspot.getGeo_latitude();
        String geoLongitude = newspot.getGeo_longitude();
        String description = spotDescription.getText().toString();
        String img="/res/drawable/tie";

        //Validate Input
        if(validateInput(spotName) && validateInput(spotAddress) && validateInput(spotDescription)){

            //Spot Entity
            newspot.setName(name);
            newspot.setAddress(address);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("spotName", name));
            nameValuePairs.add(new BasicNameValuePair("categoryId", categoryId));
            nameValuePairs.add(new BasicNameValuePair("tags", tags));
            nameValuePairs.add(new BasicNameValuePair("cityId", cityId));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("geoLatitude", geoLatitude));
            nameValuePairs.add(new BasicNameValuePair("geoLongitude", geoLongitude));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            nameValuePairs.add(new BasicNameValuePair("img", img));

            try{
                APIConnection.SetAsyncCaller( this , getApplicationContext());
                APIConnection.createSpot(nameValuePairs);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {

        }
    }

    private void onCreateSpotSuccess() {
        finish();
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        String  response = result.toString();
        Log.e(TAG + "Spot Post result------>", response);

        try {
            JSONTokener tokener = new JSONTokener(response);
            JSONObject finalResult = new JSONObject(tokener);
            String message = finalResult.getString("success");
            if(message.equals("true")){
                onCreateSpotSuccess();
            }
            Toast.makeText(getApplicationContext(), "create spots success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showCurrentLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Add a marker in spot and move the camera
        LatLng spot_gps = new LatLng(location.getLatitude(), location.getLongitude());
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );

        //set the spot model
        newspot.setGeo_latitude(String.format("%s", location.getLatitude()));
        newspot.setGeo_longitude(String.format("%s", location.getLongitude()));

        googleMap.addMarker(new MarkerOptions().position(spot_gps).title(message));
        float zoomLevel = (float) 12.0;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spot_gps, zoomLevel));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return;
        }

        if (location != null) {
            message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    //check Input
    private boolean validateInput(EditText etText) {
        String text = etText.getText().toString();
        if (text.isEmpty()){
            etText.setError("Input must not be empty!");
            return false;
        }
        return true;
    }

    //Google Map Task
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{
        @Override
        protected List<Address> doInBackground(String... locationName){
            Geocoder coder = new Geocoder(getBaseContext());
            List<Address> address = null;

            try {
                address = coder.getFromLocationName(locationName[0],1);
                if (address==null) {
                    return null;
                }
                Log.e("current location is: ---****",address.get(0).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return address;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            // Clears all the existing markers on the map
            googleMap.clear();

            Address location=addresses.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            newspot.setGeo_latitude(String.format("%s", location.getLatitude()));
            newspot.setGeo_longitude(String.format("%s", location.getLongitude()));

            Log.e("location.getLatitude------", String.format("%s", location.getLatitude()));
            Log.e("location.getLongitude------", String.format("%s", location.getLongitude()));

            String addressText = String.format("%s, %s",
                    location.getMaxAddressLineIndex() > 0 ? location.getAddressLine(0) : "",
                    location.getCountryName());

            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(addressText);
            googleMap.addMarker(markerOptions);

            float zoomLevel = (float) 12.0;
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }

    //Location Listener class
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(getApplicationContext(), "provider status changed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "privider enabled by user", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "privider disabled by user", Toast.LENGTH_LONG).show();
        }
    }

}
