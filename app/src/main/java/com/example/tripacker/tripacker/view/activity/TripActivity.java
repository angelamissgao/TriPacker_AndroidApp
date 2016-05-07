package com.example.tripacker.tripacker.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.view.fragment.TripListPageFragment;
import com.example.tripacker.tripacker.view.fragment.TripMapPageFragment;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends ActionBarActivity implements AsyncCaller,AdapterView.OnItemClickListener{
    // Runner IO for calling external APIs
    private ListView trip_listView;
    private String TAG = "TripActivity";
    private ArrayList<TripEntity> arrayOfTrips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //get user ID
        String user_id = UserSessionManager.getSingleInstance(this).getUserDetails().get("uid");
        Log.e(TAG + "user_id is:? = ", user_id);

        //Get Trips by UserID
        getContent(Integer.parseInt(user_id));

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        trip_listView = (ListView) findViewById(R.id.triplistview);

        renderTrip(null);

    }


    public void renderTrip(ArrayList<TripEntity> TripEntities) {
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(this, arrayOfTrips);
        trip_listView.setAdapter(adapter);
        trip_listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        TripEntity item = (TripEntity) adapter.getItemAtPosition(position);
        Toast.makeText(this, "CLICK: " + item, Toast.LENGTH_SHORT).show();

        ArrayList<Integer> trip_info = new ArrayList<>();
        TripEntity tripEntity = arrayOfTrips.get(position);
        trip_info.add(tripEntity.getTrip_id());
        Intent tripIntent = new Intent(this, TripViewActivity.class);

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("tripId", trip_info);
        tripIntent.putExtras(bundle);

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

    public void getContent(int uid) {
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            APIConnection.SetAsyncCaller(this, getApplicationContext());
            APIConnection.getTripsByOwner(uid, nameValuePairs);

        }
        catch (Exception e)
        {
            Log.e("GetTripException", e.toString());
        }
    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        String response = result.toString();
        JSONTokener tokener = new JSONTokener(response);
        Log.e( TAG + "Get Gall Trips------>", response);

        try {
            JSONObject finalResult = new JSONObject(tokener);
            JSONArray Trips = finalResult.getJSONArray("tripList");
            for(int i = 0; i < Trips.length(); i++ ) {
                JSONObject childJSONObject = Trips.getJSONObject(i);
                TripEntity tripEntity = new TripEntity(childJSONObject);

//                //get image
//                int postion = (int)(Math.random() * imagesource.length);
//                int img_main = imagesource[postion];
//                tripEntity.setImage_local(img_main);

                arrayOfTrips.add(tripEntity);
            }
            renderTrip(arrayOfTrips);
            Log.e("TripFragment render list", arrayOfTrips.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
