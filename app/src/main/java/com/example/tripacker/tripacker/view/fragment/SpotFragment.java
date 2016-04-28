package com.example.tripacker.tripacker.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.tripacker.tripacker.view.adapter.SpotsTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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

        return view;
    }


    private void getContent() {
        ArrayList<SpotEntity> arrayOfSpots = new ArrayList<SpotEntity>();

        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        String cityId = "1";
        String pageId = "1";
        String pageSize = "10";

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

        String  response = result.toString();
        JSONTokener tokener = new JSONTokener(response);


        try {
            JSONObject finalResult = new JSONObject(tokener);
            JSONArray Spots = finalResult.getJSONArray("spotList");
            for (int i = 0; i < Spots.length(); i++) {  // **line 2**
                JSONObject childJSONObject = Spots.getJSONObject(i);

                String spotName = childJSONObject.getString("spotName");
                String spotId = childJSONObject.getString("spotId");

                JSONObject spoti = new JSONObject();
                spoti.put("name", spotName);
                spoti.put("spotId", spotId);

                SpotEntity spotEntity = new SpotEntity(spoti);
                arrayOfSpots.add(spotEntity);

            }

            GridView gridView = (GridView) getView().findViewById(R.id.gridView_spot);
            SpotsTimelineAdapter gridAdapter = new SpotsTimelineAdapter(thiscontext, arrayOfSpots);
            gridView.setAdapter(gridAdapter);

            Log.e("Spots Get result------>", response);
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
    public void renderSpotList(ArrayList<SpotEntity> Spot) {

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
