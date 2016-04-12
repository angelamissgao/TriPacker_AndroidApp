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
import android.util.Log;
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
import com.example.tripacker.tripacker.model.Trip;
import com.example.tripacker.tripacker.model.User;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // start login activity
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);


        // Setup the viewPager for bottom navigation
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        // Setup the landing fragment to profile fragment
        mTabLayout.getTabAt(4).getCustomView().setSelected(true);


        // Setup the menu bar
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));


        // Register models to ActiveAndroid
        com.activeandroid.Configuration.Builder configurationBuilder = new com.activeandroid.Configuration.Builder(this);
        configurationBuilder.addModelClass(Trip.class);
        configurationBuilder.addModelClass(User.class);
        ActiveAndroid.initialize(configurationBuilder.create());



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

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public final FragmentManager mFragmentManager;

//        // replace fragment in viewpager
        private final class SpotPageListener implements SpotPageFragmentListener {
            @Override
            public void onSwitchToNextFragment() {
                mFragmentManager.beginTransaction().remove(spot_fragment).commit();
                if(spot_fragment instanceof SpotFragment) {
                    spot_fragment = SpotProfileFragment.newInstance(listner);
                } else {
                    spot_fragment = SpotFragment.newInstance(listner);
                }
                notifyDataSetChanged();
            }
        }

        SpotPageListener listner = new SpotPageListener();

        public final int PAGE_COUNT = 5;

        private final String[] mTabsTitle = {"Explore", "Favorites", "Trip", "Spot", "Profile"};

        private PofilePageFragment profile_fragment = new PofilePageFragment();
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        //spot fragment
        private Fragment spot_fragment ;



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
                    return new TripFragment();
                case 3:{
                    if(spot_fragment == null) {
                        spot_fragment = SpotFragment.newInstance(listner);
                    }
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
    }


    private class TabViewHolder {
        public ImageView mIcon;

    }

}
