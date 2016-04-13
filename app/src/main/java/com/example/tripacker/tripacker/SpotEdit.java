package com.example.tripacker.tripacker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by angelagao on 4/11/16.
 */
public class SpotEdit extends AppCompatActivity {
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

        //TODO: 4/11/16 send more attribut to spot
//        EditText inputName = (EditText) findViewById(R.id.spotNameInput);
//        Spot spot = new Spot();
//        spot.setName(inputName.getText().toString());
//        sendContent();
    }

    private void sendContent() {
        try{
            HttpGet httpGet = new HttpGet(new URI(SendSpot_URL));
            RestTask task = new RestTask(this, ACTION_FOR_INTENT_CALLBACK);
            task.execute(httpGet); //doInBackground runs
            progress = ProgressDialog.show(this, "Getting Spot Data ...", "Waiting For Results...", true);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
