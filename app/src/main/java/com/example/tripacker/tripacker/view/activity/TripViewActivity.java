package com.example.tripacker.tripacker.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tripacker.tripacker.ActionTabsViewPagerAdapter;
import com.example.tripacker.tripacker.PageAdapter;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.TripTabsViewPagerAdapter;
import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;
import com.example.tripacker.tripacker.view.fragment.ExploreFragment;
import com.example.tripacker.tripacker.view.fragment.FavoritesFragment;
import com.example.tripacker.tripacker.view.fragment.ProfilePageFragment;
import com.example.tripacker.tripacker.view.fragment.SpotFragment;
import com.example.tripacker.tripacker.view.fragment.TripFragment;
import com.example.tripacker.tripacker.view.fragment.TripListPageFragment;
import com.example.tripacker.tripacker.view.fragment.TripMapPageFragment;
import com.example.tripacker.tripacker.ws.remote.WebServices;

import java.util.ArrayList;

public class TripViewActivity extends ActionBarActivity {
    // Runner IO for calling external APIs

    //new added
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private TripTabsViewPagerAdapter myViewPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);

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


        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        TripMapPageFragment map_frag = new TripMapPageFragment();
        TripListPageFragment list_frag = new TripListPageFragment();


        fragments.add(map_frag);
        fragments.add(list_frag);


        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter =new TripTabsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myViewPageAdapter);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_tripiew, R.id.title, R.id.icon);
        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);


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


    private class TabViewHolder {
        public ImageView mIcon;

    }

}
