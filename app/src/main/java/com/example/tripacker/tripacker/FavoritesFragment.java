package com.example.tripacker.tripacker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by angelagao on 4/12/16.
 */
public class FavoritesFragment extends Fragment {
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.favorites_main, container, false);
        return view;
    }
}
