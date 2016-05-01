package com.example.tripacker.tripacker.view.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotViewActivity extends AppCompatActivity implements AsyncCaller,OnMapReadyCallback {

    private static final String TAG = "SpotViewActivity";

    //GetSpotAPI - // TODO: 4/11/16
    private static final String SendSpot_URL = "";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    private static final String GetSpot_URL = "";
    ProgressDialog progress;

    private GoogleMap mMap;

    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;

    private Location spotLocation = new Location("SpotLocation");
    private SpotEntity spotEntity = new SpotEntity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_view);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> stuff = bundle.getStringArrayList("spotId");
        spotEntity.setSpotId(stuff.get(0));
        Log.e("SpotID is ----> ", stuff.get(0));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        //get Request
        getContent(stuff.get(0));


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

      //Google Map showing spot info
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_spot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getApplicationContext(), SpotEditActivity.class);

                // bundle data to the spot edit activity
                ArrayList<String> spot_info = new ArrayList<String>();

                spot_info.add(spotEntity.getSpotId());
                spot_info.add(spotEntity.getName());
                spot_info.add(spotEntity.getAddress());
                spot_info.add(spotEntity.getGeo_latitude());
                spot_info.add(spotEntity.getGeo_longitude());

                Log.e("get SpotID in SpotView:>", spotEntity.getSpotId());

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("spotId", spot_info);

                mainInten.putExtras(bundle);
                startActivity(mainInten);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if( id == android.R.id.home){
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void getContent(String spotId) {
        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        String spotid = "2";

        try{
            APIConnection.SetAsyncCaller(this, getApplicationContext());

            APIConnection.getSpotDetail(spotId, nameValuePairs);

        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }
    }


    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // clear the progress indicator
            if (progress != null)
            {
                progress.dismiss();
            }
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            Toast.makeText(getApplicationContext(),
                    "Response is ready: " + response,
                    Toast.LENGTH_LONG).show();
            Log.i("get response", "RESPONSE = " + response);
            //
            // my old json code was here. this is where you will parse it.
            //
            showSpot();
        }
    };

    private void showSpot() {
        TextView spotName_show = (TextView) findViewById(R.id.spotName_show);

    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        String  response = result.toString();
        Log.e("Spot Get Detail result------>", response);

        JSONTokener tokener = new JSONTokener(response);
        JSONObject finalResult = new JSONObject(tokener);

        // Set spot infor mation to view
        String spot_name = finalResult.getString("spotName");
        TextView tv_spotName = (TextView) findViewById(R.id.spotName_show);
        spotEntity.setName(spot_name);
        tv_spotName.setText(spot_name);

        String spot_address = finalResult.getString("address");
        TextView tv_spotaddress = (TextView) findViewById(R.id.spotAddress_show);
        spotEntity.setAddress(spot_address);
        tv_spotaddress.setText(spot_address);

        String spot_info = finalResult.getString("description");
        TextView tv_spotInfo = (TextView) findViewById(R.id.spot_show3);
        tv_spotInfo.setText(spot_info);

        String geoLati = finalResult.getString("geoLatitude");
        String geoLong = finalResult.getString("geoLongitude");
        spotEntity.setGeo_longitude(geoLong);
        spotEntity.setGeo_latitude(geoLati);
        spotLocation.setLatitude(Double.parseDouble(geoLati));
        spotLocation.setLongitude(Double.parseDouble(geoLong));
        onMapReady(mMap);
        Log.e("Spot geo set------>", "Lat" + geoLati + "  " + "Long" + geoLong);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return;
        }

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng spot_gps = new LatLng(spotLocation.getLatitude(), spotLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(spot_gps).title(spotEntity.getName()));
        float zoomLevel = (float) 12.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spot_gps, zoomLevel));
    }

    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return;
        }

        if (location != null) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
