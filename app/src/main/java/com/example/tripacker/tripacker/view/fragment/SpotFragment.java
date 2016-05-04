package com.example.tripacker.tripacker.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.presenter.SpotListPresenter;
import com.example.tripacker.tripacker.view.SpotListView;
import com.example.tripacker.tripacker.view.activity.SpotCreateActivity;
import com.example.tripacker.tripacker.view.activity.SpotViewActivity;
import com.example.tripacker.tripacker.view.activity.TripCreateActivity;
import com.example.tripacker.tripacker.view.adapter.SpotsGridViewAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by angelagao on 4/10/16.
 */
public class SpotFragment extends Fragment implements AsyncCaller, SpotListView{
    private static final String TEST_URL                   = "http://47.88.12.177/api/spot/getspots";
    private static final String ACTION_FOR_INTENT_CALLBACK = "Spots_test_receiver";

    String TAG = "SpotFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    GridView gridView;

    @Inject
    SpotListPresenter spotListPresenter;
    ArrayList<SpotEntity> arrayOfSpots = new ArrayList<>();

    private Integer[] imagesource = {
            R.drawable.paris,
            R.drawable.thai_temple,
            R.drawable.new_zealand,
            R.drawable.sf_night,
            R.drawable.sf_museum
    };


    public static SpotFragment newInstance() {
        SpotFragment f = new SpotFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        final View view = inflater.inflate(R.layout.spot_main, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView_spot);


        //HTTP GET requests
        getContent();

        // Spots gridView listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainInten = new Intent(getActivity(), SpotViewActivity.class);

                // bundle data to the spot view activity
                ArrayList<String> spot_info = new ArrayList<String>();

                SpotEntity spot = (SpotEntity) gridView.getAdapter().getItem(position);

                spot_info.add(spot.getSpotId());

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("spotId", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);
            }
        });

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.create_spot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), SpotCreateActivity.class);

                // bundle data to the spot view activity
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("user_id");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_id", spot_info);

                mainInten.putExtras(bundle);
                startActivity(mainInten);

            }
        });

        //Fab - Create a new Trip
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


    private void getContent() {
        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        String cityId = "1";
        String pageId = "1";
        String pageSize = "30";

        nameValuePairs.add(new BasicNameValuePair("pageId", pageId));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));

        try{
            APIConnection.SetAsyncCaller(this, getActivity().getApplicationContext());

            APIConnection.getSpotsList(cityId, nameValuePairs);

        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }
    }


    @Override
    /**
     * Get Spot List request response result;
     */
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        arrayOfSpots.clear();
        String  response = result.toString();
        JSONTokener tokener = new JSONTokener(response);
        Log.e("Get All Spots----->", response);

        try {
            JSONObject finalResult = new JSONObject(tokener);
            JSONArray Spots = finalResult.getJSONArray("spotList");
            for (int i = 0; i < Spots.length(); i++) {  // **line 2**
                JSONObject childJSONObject = Spots.getJSONObject(i);
                if(childJSONObject.getString("spotName").length() == 0) {
                    continue;
                }
                SpotEntity spotEntity = new SpotEntity(childJSONObject);

                //get image
                int postion = (int)(Math.random() * imagesource.length);
                int img_main = imagesource[postion];
                spotEntity.setImage_source_local(img_main);

                //add Spot Entity to array
                arrayOfSpots.add(spotEntity);
                renderSpotList(arrayOfSpots);

            }

            Toast.makeText(getContext(), "Get spots success", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));

    }

    @Override
    public void onPause(){
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            Log.e("receive---->", response);
        }
    };


    @Override
    public void renderSpotList(ArrayList<SpotEntity> Spots) {
        SpotsGridViewAdapter gridAdapter = new SpotsGridViewAdapter(thiscontext, Spots);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

//    private void loadSpotList() {this.spotListPresenter.}
}
