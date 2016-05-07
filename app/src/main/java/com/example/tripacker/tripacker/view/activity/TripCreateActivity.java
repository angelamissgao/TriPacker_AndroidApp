package com.example.tripacker.tripacker.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
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
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity provide user a page to create the basic information of one Trip;
 */
public class TripCreateActivity extends AppCompatActivity implements AsyncCaller {

    //View Element
    private EditText tripNameInput, beginDateInput, endDateInput;
    private Button play,stop,record;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_create);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        //Set View Widget
        setView();

        //Audio Recorder
        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;
        TripAudioRecord();
    }

    private void setView() {
        tripNameInput = (EditText) findViewById(R.id.tripNameInput);
        beginDateInput = (EditText) findViewById(R.id.startDate);
        endDateInput = (EditText) findViewById(R.id.endDate);
        play=(Button)findViewById(R.id.Play);
        stop=(Button)findViewById(R.id.Stop);
        record=(Button)findViewById(R.id.Record);
    }

    private void TripAudioRecord() {
        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            sendContent();
            setResult(200, null);
        }

        if (id == android.R.id.home) {
            setResult(400, null);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendContent() {
        String tripName = tripNameInput.getText().toString();
        String beginDate = beginDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();
        String spots = "19,27,32,20";

        if(validateInput(tripNameInput) && validateInput(beginDateInput) && validateInput(endDateInput)){
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("tripName", tripName));
            nameValuePairs.add(new BasicNameValuePair("beginDate", beginDate));
            nameValuePairs.add(new BasicNameValuePair("endDate", endDate));
            nameValuePairs.add(new BasicNameValuePair("spots",spots));

            try{
                APIConnection.SetAsyncCaller(this, getApplicationContext());
                APIConnection.createTrip(nameValuePairs);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        String  response = result.toString();

        try {
            JSONTokener tokener = new JSONTokener(response);
            JSONObject finalResult = new JSONObject(tokener);
            String message = finalResult.getString("success");
            if(message.equals("true")){
                onCreateSpotSuccess();
            }
            Log.e("Trip Post result------>", response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check Input
    private boolean validateInput(EditText etText) {
        String text = etText.getText().toString();
        if (text.isEmpty()){
            etText.setError("Input must not be empty!");
            return false;
        }
        return true;
    }

    private void onCreateSpotSuccess() {
        finish();
    }
}
