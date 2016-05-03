package com.example.tripacker.tripacker.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.view.activity.TripViewActivity;

import java.util.ArrayList;
import java.util.List;

public class TripRecyclerViewAdapter  extends RecyclerView.Adapter<TripRecyclerViewAdapter.TripViewHolders> {

    private List<TripEntity> tripList;
    private Context context;


    public TripRecyclerViewAdapter(Context context, List<TripEntity> tripList) {
        this.tripList = tripList;
        this.context = context;
    }

    @Override
    public TripViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list, null);
        TripViewHolders rcv = new TripViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TripViewHolders holder, int position) {

     //   StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams)holder.itemView.getLayoutParams();
     //   if(position == 0)
     //       layoutParams.setFullSpan(true);

        holder.tripName.setText(tripList.get(position).getName());
//        holder.tripCoverPhoto.setImageResource(tripList.get(position).getCover_photo_id());
    }

    @Override
    public int getItemCount() {
        return this.tripList.size();
    }


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

           // Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "Clicked Position = " + tripList.get(getPosition()).toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG + "tripinfo in Recycler Adapter-->", tripList.get(getPosition()).toString());

            ArrayList<Integer> trip_info = new ArrayList<>();
            TripEntity tripEntity = tripList.get(getPosition());
            trip_info.add(tripEntity.getTrip_id());
            Intent mainInten = new Intent(context, TripViewActivity.class);

            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("tripId", trip_info);
            mainInten.putExtras(bundle);

            this.getItemId();
            Log.e(TAG, String.valueOf(this.getItemId()));

            context.startActivity(mainInten);
        }

    }



}