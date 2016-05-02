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
import android.widget.ListView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.SpotEntity;
import com.example.tripacker.tripacker.view.SpotListView;
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

/**
 * Created by angelagao on 4/29/16.
 */
public class TripEditSpotActivity extends AppCompatActivity implements SpotListView, AsyncCaller {

    String TAG = "TripEditSpotActivity";
    //View Element
    EditText tripNameInput;
    EditText beginDateInput;
    EditText endDateInput;
    private ListView addSpotListView;
    private ArrayList<SpotEntity> arrayOfSpots = new ArrayList<>();

    private Integer[] imagesource = {
            R.drawable.paris,
            R.drawable.thai_temple,
            R.drawable.new_zealand,
            R.drawable.sf_night,
            R.drawable.sf_museum
    };

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

        // Set Up List View
        addSpotListView = (ListView) findViewById(R.id.addSpotListView);

        //HTTP GET requests
        getContent();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);


//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


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

    private void getContent() {
        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        String cityId = "1";
        String pageId = "1";
        String pageSize = "30";

        nameValuePairs.add(new BasicNameValuePair("pageId", pageId));
        nameValuePairs.add(new BasicNameValuePair("pageSize", pageSize));

        try{
            APIConnection.SetAsyncCaller(this, getApplicationContext());

            APIConnection.getSpotsList(cityId, nameValuePairs);

        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        arrayOfSpots.clear();
        String  response = result.toString();
        JSONTokener tokener = new JSONTokener(response);
        Log.e( TAG + "Get All Spots to Add to Trip ----->", response);

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

            Toast.makeText(getApplicationContext(), "Get spots success", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
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
}
