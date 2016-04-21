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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.presenter.SpotListPresenter;
import com.example.tripacker.tripacker.view.SpotListView;
import com.example.tripacker.tripacker.view.activity.SpotEditActivity;
import com.example.tripacker.tripacker.view.activity.SpotViewActivity;
import com.example.tripacker.tripacker.view.adapter.SpotsTimelineAdapter;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.example.tripacker.tripacker.ws.remote.AsyncJsonGetTask;
import com.example.tripacker.tripacker.ws.remote.WebServices;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
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
    @Inject
    SpotListPresenter spotListPresenter;


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

        //HTTP GET requests
        ArrayList<SpotEntity> arrayOfSpots = getContent();

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        SpotsTimelineAdapter gridAdapter = new SpotsTimelineAdapter(thiscontext, arrayOfSpots);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainInten = new Intent(getActivity(), SpotViewActivity.class);

                // bundle data to the spot view activity
                ArrayList<String> spot_info = new ArrayList<String>();
                //Todo: added spot json
                spot_info.add("spotID");

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("spotId", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);
            }
        });

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_spot);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), SpotEditActivity.class);

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

//    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.spotListPresenter.setView(this);
//        if(savedInstanceState == null) {
//
//        }
//
//    }

    private ArrayList<SpotEntity> getContent() {
        ArrayList<SpotEntity> arrayOfSpots = new ArrayList<SpotEntity>();
        try {
//            JSONObject spot1 = new JSONObject();
//            spot1.put("name", "Thiland");
////            spot1.put("image_main", "Thiland");
//            SpotEntity Spot1 = new SpotEntity(spot1);
//            arrayOfSpots.add(Spot1);
//
//            JSONObject spot2 = new JSONObject();
//            spot2.put("name", "new Zealand");
////            spot1.put("image_main", "Thiland");
//            SpotEntity Spot2 = new SpotEntity(spot2);
//            arrayOfSpots.add(Spot2);
//
//
//            Toast.makeText(getContext(), "SpotsTineAdapter", Toast.LENGTH_LONG).show();
//            Log.e("SpotsTineAdapter", "----->");

        }catch (Exception e) {

        }

        HttpResponse spots;
        JSONObject json = new JSONObject();
        try{
            HttpGet httpGet = new HttpGet(new URI(TEST_URL));
            RestTask tast = new RestTask(getActivity(), ACTION_FOR_INTENT_CALLBACK);
            tast.execute(httpGet);
//            AsyncJsonGetTask getTask = new AsyncJsonGetTask(this);
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//            nameValuePairs.add(new BasicNameValuePair("city", "SanFransisco"));
//            nameValuePairs.add(new BasicNameValuePair("state", "California"));
//            getTask.execute(httpGet, nameValuePairs);
            Log.d("get Request finished", "------------->");
        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }

        return arrayOfSpots;
    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().registerReceiver(receiver,new IntentFilter(ACTION_FOR_INTENT_CALLBACK) );

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
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

        HttpResponse  response = (HttpResponse) result;
        int code = response.getStatusLine().getStatusCode();
        Log.i(TAG, "RESPONSE CODE= " + code);

        String responseBody = "";

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        if(code == 200) {
            try {
                responseBody = responseHandler.handleResponse(response);
                Toast.makeText(getContext(), "Get spots success", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
        }

    }

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
