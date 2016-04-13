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

import java.util.ArrayList;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ActionTabsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    public static final int PROFILE = 0;
    public static final int SONGS = 1;
    public static final int VIDEOS = 2;
    public static final int WALLPAPER = 3;
    public static final int SUBSCRIBE = 4;
    public static final String UI_TAB_PROFILE = "PROFILE";
    public static final String UI_TAB_SONGS = "SONG";
    public static final String UI_TAB_VIDEOS = "VIDEO";
    public static final String UI_TAB_WALLPAPER = "WALL PAPER";
    public static final String UI_TAB_SUBSCRIBE = "MAIL LIST";

    public ActionTabsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PROFILE:
                return UI_TAB_PROFILE;
            case SONGS:
                return UI_TAB_SONGS;
            case VIDEOS:
                return UI_TAB_VIDEOS;
            case WALLPAPER:
                return UI_TAB_WALLPAPER;
            case SUBSCRIBE:
                return UI_TAB_SUBSCRIBE;
            default:
                break;
        }
        return null;
    }

}
