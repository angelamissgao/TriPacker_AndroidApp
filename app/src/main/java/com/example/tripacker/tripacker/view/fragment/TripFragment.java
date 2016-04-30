package com.example.tripacker.tripacker.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.view.activity.TripCreateActivity;
import com.example.tripacker.tripacker.view.adapter.TripRecyclerViewAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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

        // FAB
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fab_frame_layout);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fab_trip_spot);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }
            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        //Create a new Trip
        FloatingActionButton ButtonAddTrip = (FloatingActionButton) view.findViewById(R.id.create_trip_frag);
        ButtonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), TripCreateActivity.class);
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("user_id");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_id", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);
            }
        });

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
