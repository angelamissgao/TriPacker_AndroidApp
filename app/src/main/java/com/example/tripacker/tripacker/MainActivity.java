package com.example.tripacker.tripacker;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import com.example.tripacker.tripacker.navigation.slidingtab.SlidingTabLayout;
import com.example.tripacker.tripacker.ws.WebServices;
import com.example.tripacker.tripacker.fragment.ExploreFragment;
import com.example.tripacker.tripacker.fragment.FavoritesFragment;
import com.example.tripacker.tripacker.fragment.ProfilePageFragment;
import com.example.tripacker.tripacker.fragment.SpotFragment;
import com.example.tripacker.tripacker.fragment.TripFragment;
import com.example.tripacker.tripacker.model.Trip;
import com.example.tripacker.tripacker.model.User;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    // Runner IO for calling external APIs

    private TabLayout mTabLayout;

    //bottom navigation
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
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    //new added
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ActionTabsViewPagerAdapter myViewPageAdapter;




    // Configuration for calling a REST service
    private static final String TEST_URL                   = "http://47.88.12.177/api";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // start login activity
  //      Intent intent = new Intent(this, LoginActivity.class);
  //      startActivity(intent);


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
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        // Register models to ActiveAndroid
        com.activeandroid.Configuration.Builder configurationBuilder = new com.activeandroid.Configuration.Builder(this);
        configurationBuilder.addModelClass(Trip.class);
        configurationBuilder.addModelClass(User.class);
        ActiveAndroid.initialize(configurationBuilder.create());


        //
        WebServices.setURL(TEST_URL);



    }

    // menu drawer
    private void addDrawerItems() {
        String[] osArray = { "Profile", "My Spot", "My Trip", "Bookmark", "Logout" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
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
/*
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public final FragmentManager mFragmentManager;

        public final int PAGE_COUNT = 5;

        private final String[] mTabsTitle = {"Explore", "Favorites", "Trip", "Spot", "Profile"};

<<<<<<< HEAD
        private PofilePageFragment profile_fragment = new PofilePageFragment();
        private Fragment spot_fragment = new SpotFragment();
        private TripFragment trip_fragment = new TripFragment();

=======
        private ProfilePageFragment profile_fragment = new ProfilePageFragment();
>>>>>>> top_sliding_bar
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return new ExploreFragment();
                case 1:
                    return new FavoritesFragment();
                case 2:
                    return trip_fragment;
                case 3:{
                    return spot_fragment;
                }
                case 4:
                    return profile_fragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }*/


    private class TabViewHolder {
        public ImageView mIcon;

    }

}
