package com.example.tripacker.tripacker;

/** Author: Eileen (Hao-Chi Wei)
 *
 *
 * This is a class for ActionTabsViewPagerAdapter which handles the tabs actions for the application.
 *
 *
 **/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class TripTabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public static final int LIST = 1;
    public static final int MAP = 0;


    public static final String UI_TAB_LIST = "Spot List";
    public static final String UI_TAB_MAP = "Map";



    public TripTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        Log.e("TripTabsVPAdapter", " #"+pos);
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case LIST:
                return UI_TAB_LIST;
            case MAP:
                return UI_TAB_MAP;
            default:
                break;
        }
        return null;
    }


}
