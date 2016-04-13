package com.example.tripacker.tripacker.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.SpotEdit;
import com.example.tripacker.tripacker.SpotProfileActivity;
import com.example.tripacker.tripacker.adapter.SpotsTimelineAdapter;
import com.example.tripacker.tripacker.model.Spot;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by angelagao on 4/10/16.
 */
public class SpotFragment extends Fragment {
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";

    public static SpotFragment newInstance() {
        SpotFragment f = new SpotFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        final View view = inflater.inflate(R.layout.spot_item, container, false);

        //Search
        SearchView spot_search = (SearchView) view.findViewById(R.id.spot_search);

        //GridView
        ArrayList<Spot> arrayOfSpots = new ArrayList<Spot>();
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        SpotsTimelineAdapter gridAdapter = new SpotsTimelineAdapter(thiscontext, arrayOfSpots);
        gridView.setAdapter(gridAdapter);

        try {
            JSONObject spot1 = new JSONObject();
            spot1.put("name", "Thiland");
            spot1.put("image_main", "Thiland");
            Spot spot = new Spot(spot1);
            gridAdapter.add(spot);
        }catch (Exception e) {

        }

        // relocate to spot profile
        ImageView img1 = (ImageView) view.findViewById(R.id.img1);
        // could be set to onTouchListener
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainInten = new Intent(getActivity(), SpotProfileActivity.class);

                // bundle data to the spot view activity
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("spotID");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("spotId", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);

            }
        });


        // Floating button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_spot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), SpotEdit.class);

                // bundle data to the spot view activity
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("user_id");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_id", spot_info);

                mainInten.putExtras(bundle);
                startActivity(mainInten);

            }
        });

        return view;
    }

}
