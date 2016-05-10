package com.example.tripacker.tripacker.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.UserEntity;

import java.util.ArrayList;


public class FollowerListAdapter extends ArrayAdapter<UserEntity> {
    public FollowerListAdapter(Context context, ArrayList<UserEntity> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        // Get the data item for this position
        UserEntity user = (UserEntity) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.follower_item, parent, false);
        }
        // Lookup view for data population
        TextView follow_nickname = (TextView) convertView.findViewById(R.id.follow_nickname);
        TextView follow_username = (TextView) convertView.findViewById(R.id.follow_username);
        //    // Populate the data into the template view using the data object
        follow_nickname.setText(user.getNickname());
        follow_username.setText(user.getUsername());
        // Return the completed view to render on screen


        return convertView;
    }


}
