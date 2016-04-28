package com.example.tripacker.tripacker.view.activity;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotCreateActivity extends AppCompatActivity implements AsyncCaller {

    ProgressDialog progress;
    //Spot on Google maps
    private GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng latLng;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_create);

        Bundle bundle = getIntent().getExtras();
//        getLocationFromAddress("1121 Homer Cmn, San Jose, CA");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
//        getSupportActionBar().setElevation(0);

//        EditText inputName = (EditText) findViewById(R.id.spotNameInput);
//        Spot spot = new Spot();
//        spot.setName(inputName.getText().toString());

        // Post request to add a spot
        Button button_addSpot = (Button) findViewById(R.id.addSpot);
        button_addSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendContent();
            }
        });

        //Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCreate);
        Button button_showMap = (Button) findViewById(R.id.showSpotMap);// Getting a reference to the map
        googleMap = mapFragment.getMap();

        button_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location
                EditText etLocation = (EditText) findViewById(R.id.spotAddress);

                // Getting user input location
                String location = etLocation.getText().toString();

                new GeocoderTask().execute(location);
            }
        });

    }

    private void sendContent() {
        String name = "San Jose Museum";
        String tags = "history";
        String categoryId = "11";
        String cityId = "1";
        String address = "131 Homer Rd.";
        String geoLatitude = "-28.12345";
        String geoLongitude = "25.12345";
        String description = "This is a good place";
        String img="/res/drawable/tie";

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
            Log.e("Create Spots set caller", "-------> create spot 1");
            APIConnection.SetAsyncCaller( this , getApplicationContext());
            Log.e("Post request", "-------> create spot 2");
            APIConnection.createSpot(nameValuePairs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result){
        String  response = result.toString();
//        JSONTokener tokener = new JSONTokener(response);

        try {
//            JSONObject finalResult = new JSONObject(tokener);
            Log.e("Spot Post result------>", response);
            Toast.makeText(getApplicationContext(), "create spots success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,1);
            if (address==null) {
                return;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();
            Log.e("location is: ---****",location.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

            String addressText = String.format("%s, %s",
                    location.getMaxAddressLineIndex() > 0 ? location.getAddressLine(0) : "",
                    location.getCountryName());

            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(addressText);

            googleMap.addMarker(markerOptions);

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
