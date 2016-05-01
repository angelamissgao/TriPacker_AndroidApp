package com.example.tripacker.tripacker.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.UserProfileEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.entity.mapper.UserProfileEntityJsonMapper;
import com.example.tripacker.tripacker.view.UserDetailsView;
import com.example.tripacker.tripacker.view.UserProfileView;
import com.example.tripacker.tripacker.view.activity.EditProfileActivity;
import com.example.tripacker.tripacker.view.activity.SpotCreateActivity;
import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

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
public class ProfilePageFragment extends Fragment implements AsyncCaller, UserProfileView {
    private static final String TAG = "PofilePageFragment";
    private Context thiscontext;
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int REQUEST_EDIT = 0;
    private static final int RESULT_OK = 200;
    private static final int RESULT_NOTSAVED = 400;
    private static SharedPreferences pref;

    private ProgressDialog progressDialog;

    private ImageView profile_pic;
    private TextView username_view;
    private ListView trip_listView;
    private ImageView editProfileButton;
    private LinearLayout viewFollowingBtn;

    private TextView tripnum_view;
    private TextView followingnum_view;
    private TextView followernum_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        setUpViewById(view);


        //From session
        Log.e(TAG+" sess", "->" + pref.getString("username", null));
        Log.e(TAG+" sess", "->" + pref.getString("uid", null));
        Log.e(TAG+" sess", "->" + pref.getString("cookies", null));

        getContent();




        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
        //JSONArray jsonArray = ...;
        //ArrayList<User> newUsers = User.fromJson(jsonArray)
        //adapter.addAll(newUsers);


        // Edit button

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), SpotCreateActivity.class);

                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivityForResult(intent, REQUEST_EDIT);

            }
        });

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


        return view;
    }

    private void setUpViewById(View view){
        profile_pic = (ImageView) view.findViewById(R.id.profile_image);
        username_view = (TextView) view.findViewById(R.id.user_name);
        trip_listView = (ListView) view.findViewById(R.id.triplistview);
        editProfileButton = (ImageView) view.findViewById(R.id.edit_profile_btn);
        viewFollowingBtn = (LinearLayout) view.findViewById(R.id.view_following_btn);


        tripnum_view = (TextView) view.findViewById(R.id.trip_num);
        followingnum_view = (TextView) view.findViewById(R.id.following_num);
        followernum_view = (TextView) view.findViewById(R.id.follower_num);

        View.OnClickListener button_click = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewFollowing();
            }
        };
        viewFollowingBtn.setOnClickListener(button_click);
    }

    private void getContent(){
        showLoading();
        Log.e("Get User Profile", "-------> Get Content");


        // the request
        try{

            APIConnection.SetAsyncCaller(this, thiscontext);

            APIConnection.getUserPublicProfile(Integer.parseInt(pref.getString("uid", null).trim()), null);

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


            if(finalResult.getString("success").equals("true")){
                hideLoading();

                Log.i(TAG, "RESPONSE BODY= " + response);
                // Parse user json object
                UserProfileEntity user = (new UserProfileEntityJsonMapper()).transformUserProfileEntity(response);
                Log.i(TAG, "User Info= " + user.toString());
                renderUserProfile(user);

            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult ", "requestCode= " + requestCode + "resultCode= "+resultCode);
        if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(thiscontext, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(thiscontext, "Cancel Update", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void renderUserProfile(UserProfileEntity user) {
        username_view.setText(user.getNickname());
        tripnum_view.setText(user.getTotalTrips());
        followernum_view.setText(user.getTotalFollowers());
        followingnum_view.setText(user.getTotalFollowings());
        Picasso
                .with(thiscontext)
                .load("http://weknowyourdreamz.com/images/minions/minions-07.jpg")
                .fit() // will explain later
                .into((ImageView) profile_pic);
    }

    @Override
    public void renderTrip(ArrayList<TripEntity> TripEntities) {
        // Construct the data source
        ArrayList<TripEntity> arrayOfTrips = new ArrayList<TripEntity>();
        // Create the adapter to convert the array to views
        TripsTimelineAdapter adapter = new TripsTimelineAdapter(thiscontext, arrayOfTrips);
        // Attach the adapter to a ListView
        trip_listView.setAdapter(adapter);


        // Add item to adapter
/*
        try {

           JSONObject js_trip1 = new JSONObject();
            js_trip1.put("name", "San Diego Trip");
            js_trip1.put("gmt_create", "04/10/2015");
            TripEntity newTrip1 = new TripEntity(js_trip1);
            adapter.add(newTrip1);

            JSONObject js_trip2 = new JSONObject();
            js_trip2.put("name", "SFMA");
            js_trip2.put("gmt_create", "06/12/2015");
            TripEntity newTrip2 = new TripEntity(js_trip2);
            adapter.add(newTrip2);

            JSONObject js_trip3 = new JSONObject();
            js_trip3.put("name", "Stanford University");
            js_trip3.put("gmt_create", "08/12/2015");
            TripEntity newTrip3 = new TripEntity(js_trip3);
            adapter.add(newTrip3);

            JSONObject js_trip4 = new JSONObject();
            js_trip4.put("name", "NASA Research Park");
            js_trip4.put("gmt_create", "12/12/2015");
            TripEntity newTrip4 = new TripEntity(js_trip4);
            adapter.add(newTrip4);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
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


    public void viewFollowing() {

            Intent followingInten = new Intent(getActivity(), ViewFollowingActivity.class);
            startActivity(followingInten);

    }
}
