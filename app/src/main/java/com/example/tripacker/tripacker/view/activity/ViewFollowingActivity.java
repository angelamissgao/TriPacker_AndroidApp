package com.example.tripacker.tripacker.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.view.UserEditProfileDetailsView;
import com.example.tripacker.tripacker.view.adapter.FollowerListAdapter;
import com.example.tripacker.tripacker.view.adapter.TripsTimelineAdapter;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewFollowingActivity extends ActionBarActivity implements View.OnClickListener, AsyncCaller, UserEditProfileDetailsView, AdapterView.OnItemClickListener {

    private static final String TAG = "ViewFollowingActivity";



    private ProgressDialog progressDialog;
    private AlertDialog errorDialog;

    private static SharedPreferences pref;

    private ListView following_listView;
    private int profile_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_following);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        initializeDialog();

        //showLoading();

        pref = getApplicationContext().getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);


        Intent intent = getIntent();

        profile_id  = Integer.parseInt(intent.getStringExtra("profile_id"));




        setUpViewById();



        ArrayList<UserEntity> arrayOfFollowers = new ArrayList<UserEntity>();
        // Create the adapter to convert the array to views
        FollowerListAdapter adapter = new FollowerListAdapter(this, arrayOfFollowers);
        // Attach the adapter to a ListView
        following_listView.setAdapter(adapter);

        UserEntity user0 = new UserEntity("16", "angela", "Angela Gao");
        adapter.add(user0);
        UserEntity user1 = new UserEntity("1", "eileen", "Eileen Wei");
        adapter.add(user1);
        UserEntity user2 = new UserEntity("2", "eileen2", "Eileen Wei 2");
        adapter.add(user2);
        UserEntity user3 = new UserEntity("3", "eileen3", "Eileen Wei 3");
        adapter.add(user3);
        UserEntity user4 = new UserEntity("4", "eileen4", "Eileen Wei 4");
        adapter.add(user4);

        // Setup listerner for clicks
        following_listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub
        UserEntity item = (UserEntity) adapter.getItemAtPosition(position);
        Toast.makeText(this, "CLICK: " + item, Toast.LENGTH_SHORT).show();

        Intent userIntent = new Intent(this, ViewProfileActivity.class);
        userIntent.putExtra("profile_id", item.getUserId());
        startActivity(userIntent);
    }
    public void initializeDialog(){
        progressDialog = new ProgressDialog(ViewFollowingActivity.this,
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
        following_listView = (ListView) findViewById(R.id.followinglistview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.action_menu, menu);
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
            nameValuePairs.add(new BasicNameValuePair("user_id", pref.getString("user_id", null)));
            APIConnection.SetAsyncCaller(this, getApplicationContext());

            APIConnection.getUserProfile(Integer.parseInt(pref.getString("uid", null).trim()), nameValuePairs);

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
                UserEntity user = (new UserEntityJsonMapper()).transformUserEntity(response);
                Log.i(TAG, "User Info= " + user.toString());
                renderUser(user); //Render user
            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void renderUser(UserEntity user) {

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
}
