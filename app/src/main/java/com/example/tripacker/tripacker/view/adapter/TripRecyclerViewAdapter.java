package com.example.tripacker.tripacker.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.view.holder.TripViewHolders;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripRecyclerViewAdapter  extends RecyclerView.Adapter<TripViewHolders> {

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
        holder.tripCoverPhoto.setImageResource(tripList.get(position).getCover_photo_id());



    }

    @Override
    public int getItemCount() {
        return this.tripList.size();
    }
}