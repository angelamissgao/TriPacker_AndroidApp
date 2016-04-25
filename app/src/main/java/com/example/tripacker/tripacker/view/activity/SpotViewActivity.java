package com.example.tripacker.tripacker.view.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotViewActivity extends AppCompatActivity implements AsyncCaller {

    //GetSpotAPI - // TODO: 4/11/16
    private static final String SendSpot_URL = "";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    private static final String GetSpot_URL = "";
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_view);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> stuff = bundle.getStringArrayList("spotID");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);


//
//        //// TODO: 4/11/16 request more datas
//        Spot showSpot = new Spot();
        getContent();

    }

    private void getContent() {
        // Http Call
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        String spotid = "2";
        nameValuePairs.add(new BasicNameValuePair("spotid", spotid));

        try{
            APIConnection.SetAsyncCaller(this, getApplicationContext());

            APIConnection.getSpotDetail(nameValuePairs);

        } catch (Exception e) {
            Log.e("getSpots", e.toString());
            e.printStackTrace();
        }
    }


    /**
     * Our Broadcast Receiver. We get notified that the data is ready this way.
     */
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // clear the progress indicator
            if (progress != null)
            {
                progress.dismiss();
            }
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            Toast.makeText(getApplicationContext(),
                    "Response is ready: " + response,
                    Toast.LENGTH_LONG).show();
            Log.i("get response", "RESPONSE = " + response);
            //
            // my old json code was here. this is where you will parse it.
            //
            showSpot();
        }
    };

    private void showSpot() {
        TextView spotName_show = (TextView) findViewById(R.id.spotName_show);

    }


    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        String  response = result.toString();
        Log.e("Spot Get Detail result------>", response);
    }
}
