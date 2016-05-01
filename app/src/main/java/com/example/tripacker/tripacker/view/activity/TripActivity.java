package com.example.tripacker.tripacker.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.TripTabsViewPagerAdapter;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.view.fragment.TripListPageFragment;
import com.example.tripacker.tripacker.view.fragment.TripMapPageFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TripActivity extends ActionBarActivity implements  AdapterView.OnItemClickListener{
    // Runner IO for calling external APIs



    private ListView trip_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        trip_listView = (ListView) findViewById(R.id.triplistview);

        renderTrip(null);

    }


    public void renderTrip(ArrayList<TripEntity> TripEntities) {
        // Construct the data source
        ArrayList<TripEntity> arrayOfTrips = new ArrayList<TripEntity>();
        // Create the adapter to convert the array to views
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(this, arrayOfTrips);
        // Attach the adapter to a ListView
        trip_listView.setAdapter(adapter);


        // Add item to adapter

        try {

           JSONObject js_trip1 = new JSONObject();
            js_trip1.put("name", "San Diego Trip");
            js_trip1.put("gmt_create", "04/10/2015");
            TripEntity newTrip1 = new TripEntity(js_trip1);
            js_trip1.toString();
            adapter.add(newTrip1);

            JSONObject js_trip2 = new JSONObject();
            js_trip2.put("name", "SFMA");
            js_trip2.put("gmt_create", "06/12/2015");
            TripEntity newTrip2 = new TripEntity(js_trip2);
            js_trip2.toString();
            adapter.add(newTrip2);

            JSONObject js_trip3 = new JSONObject();
            js_trip3.put("name", "Stanford University");
            js_trip3.put("gmt_create", "08/12/2015");
            TripEntity newTrip3 = new TripEntity(js_trip3);
            adapter.add(newTrip3);

            JSONObject js_trip4 = new JSONObject();
            js_trip4.put("name", "NASA Research Park");
            js_trip4.put("gmt_create", "12/12/2015");
            TripEntity newTrip4 = new TripEntity(js_trip4);
            adapter.add(newTrip4);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        trip_listView.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        TripEntity item = (TripEntity) adapter.getItemAtPosition(position);
        Toast.makeText(this, "CLICK: " + item, Toast.LENGTH_SHORT).show();

        Intent tripIntent = new Intent(this, TripViewActivity.class);
        //tripIntent.putExtra("trip_id", item.getUserId());
        startActivity(tripIntent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if( id == android.R.id.home){
            this.finish();
            return true;
        }

        if( id == R.id.action_search){
            Log.e("Tripview", "search");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
