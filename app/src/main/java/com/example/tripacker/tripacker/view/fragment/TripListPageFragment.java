package com.example.tripacker.tripacker.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.view.SpotListView;
import com.example.tripacker.tripacker.view.adapter.TripSpotTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiger
 * @since March 30, 2016 12:34 PM
 */
public class TripListPageFragment extends Fragment implements AsyncCaller, SpotListView {
    private static final String TAG = "TripListPageFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int REQUEST_EDIT = 0;
    private static final int RESULT_OK = 200;
    private static final int RESULT_NOTSAVED = 400;
    private static SharedPreferences pref;

    private ProgressDialog progressDialog;

    private ImageView profile_pic;
    private TextView username_view;
    private ListView trip_spotlistView;
    private ImageView editProfileButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.trip_list_fragment, container, false);

        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        setUpViewById(view);

        renderSpotList(null);

        Log.e(TAG, "-------> " + pref.getString("name", null));
        Log.e(TAG, "-------> " + pref.getString("cookies", null));

     //   getContent();




        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);






        return view;
    }

    private void setUpViewById(View view){
        trip_spotlistView = (ListView) view.findViewById(R.id.tripspotlistview);
    }

    private void getContent(){
        showLoading();
        Log.e("Get All spots of a trip", "-------> Get Content");


        // the request
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_id", pref.getString("user_id", null)));
            APIConnection.SetAsyncCaller(this, thiscontext);

            APIConnection.getUserProfile(nameValuePairs);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

        String response = result.toString();

        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject finalResult = new JSONObject(tokener);
            Log.i(TAG, "RESPONSE CODE= " + finalResult.getString("success"));

            Log.i(TAG, "RESPONSE CODE= " + finalResult.getString("success"));


            if(finalResult.getString("success").equals("true")){
                hideLoading();

                Log.i(TAG, "RESPONSE BODY= " + response);
                // Parse user json object
                UserEntity user = (new UserEntityJsonMapper()).transformUserEntity(response);
                Log.i(TAG, "User Info= " + user.toString());
                //renderUser(user);

            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult ", "requestCode= " + requestCode + "resultCode= " + resultCode);
        if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(thiscontext, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(thiscontext, "Cancel Update", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void renderSpotList(ArrayList<SpotEntity> Spots) {
        // Construct the data source
        ArrayList<SpotEntity> arrayOfSpots = new ArrayList<SpotEntity>();
        // Create the adapter to convert the array to views
        TripSpotTimelineAdapter adapter = new TripSpotTimelineAdapter(thiscontext, arrayOfSpots);
        // Attach the adapter to a ListView
        trip_spotlistView.setAdapter(adapter);


        // Add item to adapter

        try {

           JSONObject js_spot1 = new JSONObject();
            js_spot1.put("name", "Stanford University");
            js_spot1.put("city_id", "Palo Alto, CA");
            js_spot1.put("gmt_create", "04/10/2015");
            SpotEntity newSpot1 = new SpotEntity(js_spot1);
            adapter.add(newSpot1);

            JSONObject js_spot2 = new JSONObject();
            js_spot2.put("name", "Stanford Shopping Mall");
            js_spot2.put("city_id", "Palo Alto, CA");
            js_spot2.put("gmt_create", "04/10/2015");
            SpotEntity newSpot2 = new SpotEntity(js_spot2);
            adapter.add(newSpot2);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(thiscontext,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
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
}