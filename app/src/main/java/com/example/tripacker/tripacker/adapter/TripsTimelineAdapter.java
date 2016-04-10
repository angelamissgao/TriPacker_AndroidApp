package com.example.tripacker.tripacker.adapter;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.model.Trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EILEENWEI on 4/9/16.
 */
public class TripsTimelineAdapter extends ArrayAdapter<Trip> {
    public TripsTimelineAdapter(Context context, ArrayList<Trip> trips) {
        super(context, 0, trips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trip trip = (Trip) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_item, parent, false);
        }
        // Lookup view for data population
        TextView tripDate = (TextView) convertView.findViewById(R.id.trip_date);
        TextView tripName = (TextView) convertView.findViewById(R.id.trip_name);
        // Populate the data into the template view using the data object
        tripDate.setText(trip.getDate());
        tripName.setText(trip.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
