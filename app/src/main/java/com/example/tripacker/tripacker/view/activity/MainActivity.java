package com.example.tripacker.tripacker.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.tripacker.tripacker.ActionTabsViewPagerAdapter;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.view.adapter.DrawerItemCustomAdapter;
import com.example.tripacker.tripacker.ws.remote.WebServices;

import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;

import com.example.tripacker.tripacker.view.fragment.ExploreFragment;
import com.example.tripacker.tripacker.view.fragment.FavoritesFragment;
import com.example.tripacker.tripacker.view.fragment.ProfilePageFragment;
import com.example.tripacker.tripacker.view.fragment.SpotFragment;
import com.example.tripacker.tripacker.view.fragment.TripFragment;

import java.util.ArrayList;
/*
    Main Activity with functions defining the Main Navigation
 */
public class MainActivity extends ActionBarActivity {
    // Runner IO for calling external APIs
    private String TAG = "Main Activity";
    private TabLayout mTabLayout;

    //top navigation
    private int[] mTabsIcons = {
            R.drawable.ic_main_selector,
            R.drawable.ic_favorite_selector,
            R.drawable.ic_trip_selector,
            R.drawable.ic_place_selector,
            R.drawable.ic_profile_selector};

    private int[] mTabsSelectedIcons = {
            R.drawable.ic_main_selected_24dp,
            R.drawable.ic_favorite_selected_24dp,
            R.drawable.ic_trip_selected_24dp,
            R.drawable.ic_place_selected_24dp,
            R.drawable.ic_profile_selected_24dp};


    //menu drawer
    String navTitles[];
    TypedArray navIcons;
    RecyclerView mDrawerListView;
    public DrawerLayout mDrawerLayout;
    RecyclerView.Adapter mAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    private static SharedPreferences pref;

    //new added
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ActionTabsViewPagerAdapter myViewPageAdapter;


    // Configuration for calling a REST service
    private static final String TEST_URL                   = "http://47.88.12.177/api/v1";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebServices.setURL(TEST_URL);

//      start login activity

        //From session
        Log.e(TAG + " sess", "->" + UserSessionManager.getSingleInstance(this).getUserDetails().get("username"));
        Log.e(TAG+" sess", "->" + UserSessionManager.getSingleInstance(this).getUserDetails().get("uid"));
        Log.e(TAG+" sess", "->" + UserSessionManager.getSingleInstance(this).getUserDetails().get("cookies"));

//        if(UserSessionManager.getSingleInstance(this).isUserLoggedIn()){
//
//        }
       Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
   //   UserSessionManager.getSingleInstance(this).checkLogin();



        //pref = this.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);
        // Define SlidingTabLayout (shown at top)
        // and ViewPager (shown at bottom) in the layout.
        // Get their instances.
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        ProfilePageFragment profile_frag = new ProfilePageFragment();
        SpotFragment spot_frag = new SpotFragment();
        TripFragment trip_frag = new TripFragment();
        ExploreFragment explore_frag = new ExploreFragment();
        FavoritesFragment fav_frag = new FavoritesFragment();


        fragments.add(explore_frag);
        fragments.add(fav_frag);
        fragments.add(trip_frag);
        fragments.add(spot_frag);
        fragments.add(profile_frag);

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter =new ActionTabsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myViewPageAdapter);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, R.id.title, R.id.icon);
        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);


        // Setup the menu bar
        mDrawerListView = (RecyclerView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


        addDrawerItems();
        //Finally setup ActionBarDrawerToggle
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

    }

    // menu drawer
    private void addDrawerItems() {
        //Setup Titles and Icons of Navigation Drawer
        navTitles = getResources().getStringArray(R.array.navDrawerItems);
        navIcons = getResources().obtainTypedArray(R.array.navDrawerIcons);

        /**
         *Here , pass the titles and icons array to the adapter .
         *Additionally , pass the context of 'this' activity .
         *So that , later we can use the fragmentManager of this activity to add/replace fragments.
         */

        mAdapter = new DrawerItemCustomAdapter(navTitles,navIcons,this);
        mDrawerListView.setAdapter(mAdapter);

        /**
         *It is must to set a Layout Manager For Recycler View
         *As per docs ,
         *RecyclerView allows client code to provide custom layout arrangements for child views.
         *These arrangements are controlled by the RecyclerView.LayoutManager.
         *A LayoutManager must be provided for RecyclerView to function.
         */

        mDrawerListView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class TabViewHolder {
        public ImageView mIcon;

    }





}
