package com.example.tripacker.tripacker.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotEditActivity extends AppCompatActivity implements AsyncCaller {
    //EditSpotAPI - // TODO: 4/11/16
    private static final String SendSpot_URL = "";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    private static final String GetSpot_URL = "";
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_edit);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> stuff = bundle.getStringArrayList("UserID");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

//        EditText inputName = (EditText) findViewById(R.id.spotNameInput);
//        Spot spot = new Spot();
//        spot.setName(inputName.getText().toString());

        // Post request to add a spot
        Button button_addSpot = (Button) findViewById(R.id.addSpot);
        button_addSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendContent();
            }
        });

    }

    private void sendContent() {
        String name = "San Jose Museum";
        String categoryId = "1";
        String cityId = "11";
        String address = "131 Homer Rd.";
        String geoLatitude = "-28.12345";
        String geoLongitude = "25.12345";
        String description = "This is a good place";
        String img="/res/drawable/tie";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("categoryId", categoryId));
        nameValuePairs.add(new BasicNameValuePair("cityId", cityId));
        nameValuePairs.add(new BasicNameValuePair("address", address));
        nameValuePairs.add(new BasicNameValuePair("geoLatitude", geoLatitude));
        nameValuePairs.add(new BasicNameValuePair("geoLongitude", geoLongitude));
        nameValuePairs.add(new BasicNameValuePair("description", description));
        nameValuePairs.add(new BasicNameValuePair("img", img));

        try{
            Log.e("Create Spots set caller", "-------> create spot 1");
            APIConnection.SetAsyncCaller( this , getApplicationContext());
            Log.e("Post request", "-------> create spot 2");
            APIConnection.createSpot(nameValuePairs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result){
        String  response = result.toString();
//        JSONTokener tokener = new JSONTokener(response);

        try {
//            JSONObject finalResult = new JSONObject(tokener);
            Log.e("Spot Post result------>", response);
            Toast.makeText(getApplicationContext(), "create spots success", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
