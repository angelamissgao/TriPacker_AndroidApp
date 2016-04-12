package com.example.tripacker.tripacker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotProfileFragment extends Fragment {
    private Context thiscontext;
    public static SpotProfileFragment newInstance(SpotPageFragmentListener listener) {
        SpotProfileFragment f = new SpotProfileFragment();
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Log.e("spotviewFrag", "------->show");
        View view = inflater.inflate(R.layout.spot_view, container, false);


        return view;
    }
}
