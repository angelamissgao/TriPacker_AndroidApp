package com.example.tripacker.tripacker.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.UserProfileEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.entity.mapper.UserProfileEntityJsonMapper;
import com.example.tripacker.tripacker.exception.NetworkConnectionException;
import com.example.tripacker.tripacker.view.UserDetailsView;
import com.example.tripacker.tripacker.view.UserProfileView;
import com.example.tripacker.tripacker.view.activity.EditProfileActivity;
import com.example.tripacker.tripacker.view.activity.SpotCreateActivity;
import com.example.tripacker.tripacker.view.activity.TripCreateActivity;
import com.example.tripacker.tripacker.view.activity.TripViewActivity;
import com.example.tripacker.tripacker.view.activity.ViewFollowingActivity;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tiger
 * @since March 30, 2016 12:34 PM
 */
public class ProfilePageFragment extends Fragment implements AsyncCaller, UserProfileView, AdapterView.OnItemClickListener {
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

    private String user_id;


    private ArrayList<TripEntity> arrayOfTrips;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        pref = thiscontext.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);
        Log.e(TAG, "-------> Get Public profile");

        //get user ID
        user_id = UserSessionManager.getSingleInstance(thiscontext).getUserDetails().get("uid");

        setUpViewById(view);


        //From session
        Log.e(TAG+" sess", "->" + pref.getString("username", null));
        Log.e(TAG+" sess", "->" + pref.getString("uid", null));
        Log.e(TAG+" sess", "->" + pref.getString("cookies", null));

        getContent();
        getTripContent(Integer.parseInt(user_id));


        // Edit button

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //Create a new Trip
        FloatingActionButton ButtonAddTrip = (FloatingActionButton) view.findViewById(R.id.create_trip_frag);
        ButtonAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainInten = new Intent(getActivity(), TripCreateActivity.class);
                ArrayList<String> spot_info = new ArrayList<String>();
                spot_info.add("user_id");
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_id", spot_info);
                mainInten.putExtras(bundle);

                startActivity(mainInten);
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
        Log.e(TAG, "-------> Call API");


        // the request
        try{

            APIConnection.SetAsyncCaller(this, thiscontext);
            APIConnection.getUserPublicProfile(Integer.parseInt(pref.getString("uid", null).trim()), null);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }

    public void getTripContent(int uid) {
        if(isThereInternetConnection()) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                APIConnection.SetAsyncCaller(this, thiscontext);
                APIConnection.getTripsByOwner(uid, nameValuePairs);

            } catch (Exception e) {
                Log.e("GetTripException", e.toString());
            }
        }else{
            try {
                throw new NetworkConnectionException(thiscontext);
            } catch (NetworkConnectionException e) {
                progressDialog.dismiss();
                Log.e("Network Error ", "-------> No internet");
                AlertDialog.Builder builder = e.displayMessageBox();
                showAlert(builder);
            }
        }
    }

    public void showAlert(AlertDialog.Builder builder){

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                getContent();
                getTripContent(Integer.parseInt(user_id));
            }
        });
        AlertDialog dialog = builder.create(); // calling builder.create after adding buttons
        dialog.show();
        Toast.makeText(thiscontext, "Network Unavailable!", Toast.LENGTH_LONG).show();

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
                if(finalResult.getString("code").equals("320")){
                    arrayOfTrips = new ArrayList<>();
                    JSONArray Trips = finalResult.getJSONArray("tripList");
                    for(int i = 0; i < Trips.length(); i++ ) {
                        JSONObject childJSONObject = Trips.getJSONObject(i);
                        TripEntity tripEntity = new TripEntity(childJSONObject);


                        arrayOfTrips.add(tripEntity);
                    }
                    renderTrip(arrayOfTrips);

                }else{
                    // Parse user json object
                    UserProfileEntity user = (new UserProfileEntityJsonMapper()).transformUserProfileEntity(response);
                    Log.i(TAG, "User Info= " + user.toString());
                    renderUserProfile(user);
                }


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



        TripsTimelineAdapter adapter = new TripsTimelineAdapter(thiscontext, arrayOfTrips);
        trip_listView.setAdapter(adapter);
        trip_listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        TripEntity item = (TripEntity) adapter.getItemAtPosition(position);
        Toast.makeText(thiscontext, "CLICK: " + item, Toast.LENGTH_SHORT).show();

        ArrayList<Integer> trip_info = new ArrayList<>();
        TripEntity tripEntity = arrayOfTrips.get(position);
        trip_info.add(tripEntity.getTrip_id());
        Intent tripIntent = new Intent(thiscontext, TripViewActivity.class);

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("tripId", trip_info);
        tripIntent.putExtras(bundle);

        startActivity(tripIntent);
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
            followingInten.putExtra("profile_id", user_id);
            startActivity(followingInten);

    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) thiscontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
