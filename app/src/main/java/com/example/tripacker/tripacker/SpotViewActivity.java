package com.example.tripacker.tripacker;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotViewActivity extends AppCompatActivity {

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

//
//        //// TODO: 4/11/16 request more datas
//        Spot showSpot = new Spot();
//        getContent();

    }

    private void getContent() {
//        HttpResponse spot;
//        JSONObject json = new JSONObject();
//        try{
//            HttpGet httpGet = new HttpGet(new URI(GetSpot_URL));
//
//            RestTask task = new RestTask(this, ACTION_FOR_INTENT_CALLBACK);
//             //doInBackground runs
//            progress = ProgressDialog.show(this, "Getting Spot Data ...", "Waiting For Results...", true);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

//        WebServices.setURL(GetSpot_URL);
//        HttpGet httpGet = new HttpGet(new URI(GetSpot_URL));
//        AsyncJsonGetTask getTask = new AsyncJsonGetTask(this);
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//        nameValuePairs.add(new BasicNameValuePair("spotname", "thailand"));
////        httpGet.setHeader();
//
//        getTask.execute(httpGet, "");
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


}
