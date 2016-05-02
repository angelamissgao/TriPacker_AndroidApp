package com.example.tripacker.tripacker.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/29/16.
 */
public class TripEditSpotActivity extends AppCompatActivity implements AsyncCaller {

    String TAG = "TripEditSpotActivity";
    //View Element
    EditText tripNameInput;
    EditText beginDateInput;
    EditText endDateInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_edit_spot);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            //sendContent();
            setResult(200, null);
            finish();
        }

        if (id == android.R.id.home) {
            setResult(400, null);
            finish();
        }

        if( id == R.id.action_search){
            Log.e("Trip Add Spot", "search");
            onSearchRequested();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null, false, appData, false);
        return true;
    }

    private void getContent(){
        //showLoading();
        Log.e("Get User Profile", "-------> Get Content");


        // the request
        try{

            //APIConnection.SetAsyncCaller(this, thiscontext);

            //APIConnection.getUserPublicProfile(Integer.parseInt(pref.getString("uid", null).trim()), null);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        String  response = result.toString();
//        JSONTokener tokener = new JSONTokener(response);

        try {
//            JSONObject finalResult = new JSONObject(tokener);
            Log.e("Trip Post result------>", response);
            Toast.makeText(getApplicationContext(), "create spots success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
