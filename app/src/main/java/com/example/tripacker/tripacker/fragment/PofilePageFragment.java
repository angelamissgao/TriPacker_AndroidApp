package com.example.tripacker.tripacker.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Configuration;
import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.ws.AsyncCaller;
import com.example.tripacker.tripacker.ws.AsyncJsonPostTask;
import com.example.tripacker.tripacker.ws.WebServices;
import com.example.tripacker.tripacker.model.Trip;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class PofilePageFragment extends Fragment implements AsyncCaller {
    private static final String TAG = "PofilePageFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        SharedPreferences pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);


        TextView username_view = (TextView) view.findViewById(R.id.user_name);
        username_view.setText(pref.getString("name", null));
        Log.e("From Session", "-------> "+pref.getString("name", null));

        // Construct the data source
        ArrayList<Trip> arrayOfTrips = new ArrayList<Trip>();
        // Create the adapter to convert the array to views
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(thiscontext, arrayOfTrips);
        // Attach the adapter to a ListView
        ListView listView = (ListView) view.findViewById(R.id.triplistview);
        listView.setAdapter(adapter);


        // Add item to adapter

        try {

           JSONObject js_trip1 = new JSONObject();
            js_trip1.put("name", "San Diego Trip");
            js_trip1.put("gmt_create", "04/10/2015");
            Trip newTrip1 = new Trip(js_trip1);
            adapter.add(newTrip1);

            JSONObject js_trip2 = new JSONObject();
            js_trip2.put("name", "SFMA");
            js_trip2.put("gmt_create", "06/12/2015");
            Trip newTrip2 = new Trip(js_trip2);
            adapter.add(newTrip2);

            JSONObject js_trip3 = new JSONObject();
            js_trip3.put("name", "Stanford University");
            js_trip3.put("gmt_create", "08/12/2015");
            Trip newTrip3 = new Trip(js_trip3);
            adapter.add(newTrip3);

            JSONObject js_trip4 = new JSONObject();
            js_trip4.put("name", "NASA Research Park");
            js_trip4.put("gmt_create", "12/12/2015");
            Trip newTrip4 = new Trip(js_trip4);
            adapter.add(newTrip4);

        } catch (JSONException e) {
            e.printStackTrace();
        }




        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);



        return view;
    }

    private void getContent(){
        // the request
        try{
            HttpGet httpGet = new HttpGet(new URI(WebServices.getBaseUrl()+"/member/profile/getprofile"));
            AsyncJsonPostTask postTask = new AsyncJsonPostTask(this);


            postTask.execute(httpGet, "");

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

    }
}
