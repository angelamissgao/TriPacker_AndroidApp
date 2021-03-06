package com.example.tripacker.tripacker.view.activity;

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
import android.widget.ImageView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;
import com.example.tripacker.tripacker.view.adapter.TripTabsViewPagerAdapter;
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

public class TripViewActivity extends ActionBarActivity implements AsyncCaller {
    // Runner IO for calling external APIs

    TripMapPageFragment map_frag = new TripMapPageFragment();
    TripListPageFragment list_frag = new TripListPageFragment();
    //new added
    private String TAG = "TripViewActivity";
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private TripTabsViewPagerAdapter myViewPageAdapter;
    private TripEntity trip_info = new TripEntity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Integer> stuff = bundle.getIntegerArrayList("tripId");
        Log.e(TAG + "tripID-->", stuff.get(0).toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        // Define SlidingTabLayout (shown at top)
        // and ViewPager (shown at bottom) in the layout.
        // Get their instances.
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.trip_tab);
        viewPager = (ViewPager) findViewById(R.id.trip_view_pager);

        //Http Request to get Trip details with TripID
        getContent(stuff.get(0));

    }

    private void getContent(int tripId) {
        // the request
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            APIConnection.SetAsyncCaller(this, getApplicationContext());
            APIConnection.getTripDetail(tripId, nameValuePairs);

        } catch (Exception e) {
            Log.e("GetTripException", e.getMessage());
        }

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

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        if (id == R.id.action_search) {
            Log.e("Tripview", "search");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

        String response = result.toString();

        JSONTokener tokener = new JSONTokener(response);

        try {
            JSONObject finalResult = new JSONObject(tokener);
            String trip_name = finalResult.getString("tripName");
            String trip_id = finalResult.getString("tripId");
            String trip_beginDate = finalResult.getString("beginDate");
            String trip_endDate = finalResult.getString("endDate");
            String trip_ownerId = finalResult.getString("ownerId");
            String trip_ownerNickname = finalResult.getString("ownerNickname");

            JSONArray Spots = finalResult.getJSONArray("spots");
            ArrayList<SpotEntity> spotsOfTrip = new ArrayList<>();
            for (int i = 0; i < Spots.length(); i++) {
                JSONObject spoti = Spots.getJSONObject(i);
                SpotEntity spotEntity = new SpotEntity(spoti);
                spotsOfTrip.add(spotEntity);
            }

            trip_info.setName(trip_name);
            trip_info.setTrip_id(Integer.parseInt(trip_id));
            trip_info.setBeginDate(trip_beginDate);
            trip_info.setEndDate(trip_endDate);
            trip_info.setOwnerId(Integer.parseInt(trip_ownerId));
            trip_info.setSpots(spotsOfTrip);
            trip_info.setOwnername(trip_ownerNickname);

            Log.e("Get Trip detail------>", response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send data with intent to Fragments
        Bundle bundle = new Bundle();
        bundle.putSerializable("trip_info", trip_info);
        list_frag.setArguments(bundle);
        map_frag.setArguments(bundle);
        fragments.add(map_frag);
        fragments.add(list_frag);

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter = new TripTabsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myViewPageAdapter);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_tripiew, R.id.title, R.id.icon);
        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

    }

    private class TabViewHolder {
        public ImageView mIcon;

    }

}
