package com.example.tripacker.tripacker.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.view.adapter.TripRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/12/16.
 */
public class TripFragment extends Fragment {
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.trip_main, container, false);


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_trip_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<TripEntity> gaggeredList = getListItemData();

        TripRecyclerViewAdapter rcAdapter = new TripRecyclerViewAdapter(thiscontext, gaggeredList);
        recyclerView.setAdapter(rcAdapter);


        return view;
    }

    private List<TripEntity> getListItemData(){
        List<TripEntity> listViewItems = new ArrayList<TripEntity>();
        listViewItems.add(new TripEntity("Alkane", R.drawable.new_zealand));
        listViewItems.add(new TripEntity("Ethane", R.drawable.thai_temple));
        listViewItems.add(new TripEntity("Alkyne", R.drawable.paris));
        listViewItems.add(new TripEntity("Benzene", R.drawable.profile_cover));
        listViewItems.add(new TripEntity("Amide", R.drawable.new_zealand));
        listViewItems.add(new TripEntity("Amino Acid", R.drawable.paris));


        return listViewItems;
    }
}
