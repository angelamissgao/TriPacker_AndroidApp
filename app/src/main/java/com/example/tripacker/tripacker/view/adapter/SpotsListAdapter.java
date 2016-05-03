package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;

import java.util.ArrayList;

/**
 * Created by angelagao on 5/2/16.
 */
public class SpotsListAdapter extends ArrayAdapter<SpotEntity> {
    public SpotsListAdapter(Context context, ArrayList<SpotEntity> spots) {
        super(context, 0, spots);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SpotEntity spot = (SpotEntity) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spot_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tripspotDate = (TextView) convertView.findViewById(R.id.tripspot_date);
        TextView tripspotLocation = (TextView) convertView.findViewById(R.id.tripspot_location);
        TextView tripspotName = (TextView) convertView.findViewById(R.id.tripspot_name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.spot_pic);

        //    // Populate the data into the template view using the data object
//        tripspotDate.setText(spot.getGmt_create());
        Log.i("TripSpotTimeline", spot.toString());
//        tripspotLocation.setText(spot.getCity_id());
        tripspotName.setText(spot.getName());
        imageView.setBackgroundResource(spot.getImage_local());

        // Return the completed view to render on screen

        return convertView;
    }
}
