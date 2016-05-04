package com.example.tripacker.tripacker.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.view.activity.TripCreateActivity;
import com.example.tripacker.tripacker.view.adapter.TripRecyclerViewAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/12/16.
 */
public class TripFragment extends Fragment implements AsyncCaller {

    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private static SharedPreferences pref;
    private ArrayList<TripEntity> arrayOfTrips = new ArrayList<>();
    RecyclerView recyclerView;
    TripRecyclerViewAdapter rcAdapter;
    private Integer[] imagesource = {
            R.drawable.new_zealand,
            R.drawable.thai_temple,
            R.drawable.paris,
            R.drawable.profile_cover,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();
        View view = inflater.inflate(R.layout.trip_main, container, false);

        //get user ID
        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_trip_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //HTTP get all trips
        getContent();

        //render List


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

        //
        List<TripEntity> listViewItems = new ArrayList<TripEntity>();
        listViewItems.add(new TripEntity("Alkane", R.drawable.new_zealand));
        listViewItems.add(new TripEntity("Ethane", R.drawable.thai_temple));
        listViewItems.add(new TripEntity("Alkyne", R.drawable.paris));
        listViewItems.add(new TripEntity("Benzene", R.drawable.profile_cover));
        listViewItems.add(new TripEntity("Amide", R.drawable.new_zealand));
        listViewItems.add(new TripEntity("Amino Acid", R.drawable.paris));


        return listViewItems;
    }

    private void getContent(){
        // the request
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            String pageId = "1";
            String pageSize = "30";

            nameValuePairs.add(new BasicNameValuePair("pageId", pageId));
            nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));

            APIConnection.SetAsyncCaller(this, getContext());
            APIConnection.getTripsByRate(nameValuePairs);

        }
        catch (Exception e)
        {
            Log.e("GetTripException", e.toString());
        }

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        arrayOfTrips.clear();
        String response = result.toString();
        JSONTokener tokener = new JSONTokener(response);
        Log.e("Get Gall Trips------>", response);

        try {
            JSONObject finalResult = new JSONObject(tokener);
            JSONArray Trips = finalResult.getJSONArray("tripList");
            for(int i = 0; i < Trips.length(); i++ ) {
                JSONObject childJSONObject = Trips.getJSONObject(i);
                TripEntity tripEntity = new TripEntity(childJSONObject);

                //get image
                int postion = (int)(Math.random() * imagesource.length);
                int img_main = imagesource[postion];
                tripEntity.setImage_local(img_main);

                arrayOfTrips.add(tripEntity);
            }
            renderSpotList(arrayOfTrips);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void renderSpotList(final ArrayList<TripEntity> Trips) {
        rcAdapter = new TripRecyclerViewAdapter(thiscontext, Trips);
        recyclerView.setAdapter(rcAdapter);

    }



}
