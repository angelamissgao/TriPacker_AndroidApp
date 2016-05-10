package com.example.tripacker.tripacker.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Check Detailed information about one Spot
 */
public class SpotViewActivity extends AppCompatActivity implements AsyncCaller, OnMapReadyCallback {

    private static final String TAG = "SpotViewActivity";
    private ProgressDialog progress;
    private GoogleMap mMap;
    private TextView tv_spotName, tv_spotaddress, tv_spotInfo;

    private Location spotLocation = new Location("SpotLocation");
    private SpotEntity spotEntity = new SpotEntity();
    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // clear the progress indicator
            if (progress != null) {
                progress.dismiss();
            }
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            Toast.makeText(getApplicationContext(),
                    "Response is ready: " + response,
                    Toast.LENGTH_LONG).show();
            Log.i("get response", "RESPONSE = " + response);
            showSpot();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_view);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> stuff = bundle.getStringArrayList("spotId");
        spotEntity.setSpotId(stuff.get(0));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        //get Request
        getContent(stuff.get(0));

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
                spot_info.add(spotEntity.getDescription());

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
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        if (id == R.id.action_search) {
            Log.e("Spot view", "search");
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void getContent(String spotId) {
        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        try {
            APIConnection.SetAsyncCaller(this, getApplicationContext());
            APIConnection.getSpotDetail(spotId, nameValuePairs);

        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }
    }

    private void showSpot() {
        TextView spotName_show = (TextView) findViewById(R.id.spotName_show);
    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        String response = result.toString();
        Log.e("Spot Get Detail result------>", response);

        JSONTokener tokener = new JSONTokener(response);
        JSONObject finalResult = new JSONObject(tokener);

        setView(finalResult);
        onMapReady(mMap);

    }

    private void setView(JSONObject finalResult) {

        tv_spotName = (TextView) findViewById(R.id.spotName_show);
        tv_spotaddress = (TextView) findViewById(R.id.spotAddress_show);
        tv_spotInfo = (TextView) findViewById(R.id.spot_show3);

        // Set spot information to view
        try {
            String spot_name = finalResult.getString("spotName");
            spotEntity.setName(spot_name);
            tv_spotName.setText(spot_name);

            String spot_address = finalResult.getString("address");
            spotEntity.setAddress(spot_address);
            tv_spotaddress.setText(spot_address);

            String spot_info = finalResult.getString("description");
            spotEntity.setDescription(spot_info);
            tv_spotInfo.setText(spot_info);

            String geoLati = finalResult.getString("geoLatitude");
            String geoLong = finalResult.getString("geoLongitude");
            spotEntity.setGeo_longitude(geoLong);
            spotEntity.setGeo_latitude(geoLati);
            spotLocation.setLatitude(Double.parseDouble(geoLati));
            spotLocation.setLongitude(Double.parseDouble(geoLong));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in spot and move the camera
        LatLng spot_gps = new LatLng(spotLocation.getLatitude(), spotLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(spot_gps).title(spotEntity.getName()));
        float zoomLevel = (float) 12.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spot_gps, zoomLevel));
    }

}
