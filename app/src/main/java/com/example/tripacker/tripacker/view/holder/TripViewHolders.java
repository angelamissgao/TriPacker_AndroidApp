package com.example.tripacker.tripacker.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;

public class TripViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tripName;
    public ImageView tripCoverPhoto;

    public TripViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tripName = (TextView) itemView.findViewById(R.id.trip_name);
        tripCoverPhoto = (ImageView) itemView.findViewById(R.id.trip_cover_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
