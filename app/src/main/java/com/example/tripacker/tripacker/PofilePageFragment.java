package com.example.tripacker.tripacker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tripacker.tripacker.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.model.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class PofilePageFragment extends Fragment {
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        view = inflater.inflate(R.layout.profile_fragment, container, false);


        // Construct the data source
        ArrayList<Trip> arrayOfTrips = new ArrayList<Trip>();
        // Create the adapter to convert the array to views
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(thiscontext, arrayOfTrips);
        // Attach the adapter to a ListView
        ListView listView = (ListView) view.findViewById(R.id.triplistview);
        listView.setAdapter(adapter);

        // Add item to adapter
        Trip newTrip = new Trip("San Diego Trip","04/10/2015",  "San Diego, CA");
        adapter.add(newTrip);
        Trip newTrip1 = new Trip("SFMA", "06/12/2015", "San Fancisco, CA");
        adapter.add(newTrip1);
        Trip newTrip2 = new Trip("Stanford University", "08/12/2015", "Palo Alto, CA");
        adapter.add(newTrip2);
        Trip newTrip3 = new Trip("NASA Research Park", "12/12/2015", "Mountain View, CA");
        adapter.add(newTrip3);
        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);



        return view;
    }


}
