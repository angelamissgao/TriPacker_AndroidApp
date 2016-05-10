package com.example.tripacker.tripacker.view.adapter;

/**
 * Author: Eileen (Hao-Chi Wei)
 * <p>
 * <p>
 * This is a class for ActionTabsViewPagerAdapter which handles the tabs actions for the application.
 **/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ActionTabsViewPagerAdapter extends FragmentPagerAdapter {
    public static final int EXPLORE = 0;
    public static final int FAV = 1;
    public static final int TRIP = 2;
    public static final int SPOT = 3;
    public static final int PROFILE = 4;
    public static final String UI_TAB_EXPLORE = "EXPLORE";
    public static final String UI_TAB_FAV = "FAV";
    public static final String UI_TAB_TRIP = "TRIP";
    public static final String UI_TAB_SPOT = "SPOT";
    public static final String UI_TAB_PROFILE = "PROFILE";
    private ArrayList<Fragment> fragments;


    public ActionTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos) {
        return fragments.get(pos);
    }

    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case EXPLORE:
                return UI_TAB_EXPLORE;
            case FAV:
                return UI_TAB_FAV;
            case TRIP:
                return UI_TAB_TRIP;
            case SPOT:
                return UI_TAB_SPOT;
            case PROFILE:
                return UI_TAB_PROFILE;
            default:
                break;
        }
        return null;
    }


}
