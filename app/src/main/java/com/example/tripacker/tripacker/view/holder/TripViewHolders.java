package com.example.tripacker.tripacker.view.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.view.activity.TripViewActivity;

public class TripViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    private String TAG = "TripViewHolders";
    public TextView tripName;
    public ImageView tripCoverPhoto;
    private final Context context;


    public TripViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tripName = (TextView) itemView.findViewById(R.id.trip_name_trips);
        tripCoverPhoto = (ImageView) itemView.findViewById(R.id.trip_cover_photo);
        context = itemView.getContext();
    }

    @Override
    public void onClick(View view) {


        Intent mainInten = new Intent(context, TripViewActivity.class);

        this.getItemId();
        Log.e(TAG,  String.valueOf(this.getItemId()) );

        context.startActivity(mainInten);
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

}
