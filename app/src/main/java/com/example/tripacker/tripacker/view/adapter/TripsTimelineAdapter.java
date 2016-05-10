package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;

import java.util.ArrayList;


public class TripsTimelineAdapter extends ArrayAdapter<TripEntity> {
    public TripsTimelineAdapter(Context context, ArrayList<TripEntity> trips) {
        super(context, 0, trips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        TripEntity trip = (TripEntity) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_item, parent, false);
        }
        // Lookup view for data population
        TextView tripDate = (TextView) convertView.findViewById(R.id.trip_date);
        TextView tripName = (TextView) convertView.findViewById(R.id.trip_name);
        //    // Populate the data into the template view using the data object
        tripDate.setText(trip.getGmt_create());
        tripName.setText(trip.getName());
        // Return the completed view to render on screen

        return convertView;
    }
}
