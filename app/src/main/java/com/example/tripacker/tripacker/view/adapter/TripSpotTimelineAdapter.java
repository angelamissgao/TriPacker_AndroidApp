package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.entity.TripEntity;

import java.util.ArrayList;


public class TripSpotTimelineAdapter extends ArrayAdapter<SpotEntity> {
    public TripSpotTimelineAdapter(Context context, ArrayList<SpotEntity> spots) {
        super(context, 0, spots);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SpotEntity spot = (SpotEntity) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_spotitem, parent, false);
        }
        // Lookup view for data population
        TextView tripspotDate = (TextView) convertView.findViewById(R.id.tripspot_date);
        TextView tripspotLocation = (TextView) convertView.findViewById(R.id.tripspot_location);
        TextView tripspotName = (TextView) convertView.findViewById(R.id.tripspot_name);

        //    // Populate the data into the template view using the data object
        tripspotDate.setText(spot.getGmt_create());
        tripspotLocation.setText(spot.getCity_id());
        tripspotName.setText(spot.getName());
        // Return the completed view to render on screen

        return convertView;
    }
}
