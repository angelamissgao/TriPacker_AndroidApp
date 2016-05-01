package com.example.tripacker.tripacker.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.UserProfileEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.entity.mapper.UserProfileEntityJsonMapper;
import com.example.tripacker.tripacker.view.UserEditProfileDetailsView;
import com.example.tripacker.tripacker.view.UserProfileView;
import com.example.tripacker.tripacker.view.adapter.FollowerListAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileActivity extends ActionBarActivity implements View.OnClickListener, AsyncCaller, UserProfileView {

    private static final String TAG = "ViewProfileActivity";



    private ProgressDialog progressDialog;
    private AlertDialog errorDialog;


    private ImageView profile_pic;
    private TextView username_view;
    private ListView trip_listView;
    private ImageView editProfileButton;
    private LinearLayout viewFollowingBtn;

    private TextView tripnum_view;
    private TextView followingnum_view;
    private TextView followernum_view;



    private static SharedPreferences pref;

    private int profile_id;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        initializeDialog();
        //showLoading();



        Intent intent = getIntent();

        profile_id  = Integer.parseInt(intent.getStringExtra("profile_id"));




        setUpViewById();

        getProfile();





    }
    public void initializeDialog(){
        progressDialog = new ProgressDialog(ViewProfileActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR !!");
        builder.setMessage("Sorry there was an error getting data from the Internet.\nNetwork Unavailable!");

        errorDialog = builder.create();
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //runTask();
            }
        });
    }
    private void setUpViewById(){

        profile_pic = (ImageView) findViewById(R.id.profile_image);
        username_view = (TextView) findViewById(R.id.user_name);
        trip_listView = (ListView) findViewById(R.id.triplistview);
        editProfileButton = (ImageView) findViewById(R.id.edit_profile_btn);
        viewFollowingBtn = (LinearLayout) findViewById(R.id.view_following_btn);


        tripnum_view = (TextView) findViewById(R.id.trip_num);
        followingnum_view = (TextView) findViewById(R.id.following_num);
        followernum_view = (TextView) findViewById(R.id.follower_num);

        View.OnClickListener button_click = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewFollowing();
            }
        };
        viewFollowingBtn.setOnClickListener(button_click);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if( id == android.R.id.home){
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    private void getProfile(){
        Log.e("Get User Profile Edit", "-> Get Content");


        // the request
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_id", profile_id+""));
            APIConnection.SetAsyncCaller(this, getApplicationContext());

            APIConnection.getUserPublicProfile(profile_id, nameValuePairs);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }




    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
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
                renderUserProfile(user); //Render user
            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void renderUserProfile(UserProfileEntity user) {
        username_view.setText(user.getNickname());
        tripnum_view.setText(user.getTotalTrips());
        followernum_view.setText(user.getTotalFollowers());
        followingnum_view.setText(user.getTotalFollowings());
        Picasso
                .with(this)
                .load("http://weknowyourdreamz.com/images/minions/minions-07.jpg")
                .fit() // will explain later
                .into((ImageView) profile_pic);
    }

    @Override
    public void renderTrip(ArrayList<TripEntity> TripEntities) {

    }

    @Override
    public void showLoading() {
        errorDialog.hide();
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
        hideLoading();
        showError("");
    }

    @Override
    public void hideRetry() {
        errorDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        errorDialog.show();
    }

    @Override
    public Context context() {
        return null;
    }

    public void viewFollowing() {

        Intent followingInten = new Intent(this, ViewFollowingActivity.class);
        startActivity(followingInten);

    }
}

