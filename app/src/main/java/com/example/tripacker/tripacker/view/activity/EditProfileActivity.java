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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.view.UserEditProfileDetailsView;
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

public class EditProfileActivity extends ActionBarActivity implements View.OnClickListener, AsyncCaller, UserEditProfileDetailsView {

    private static final String TAG = "EditProfileActivity";

    private  EditText usernameEtxt;
    private  EditText locationEtxt;
    private  EditText emailEtxt;
    private  EditText birthdayEtxt;
    private  EditText phoneEtxt;
    private  EditText introductionEtxt;
    private Spinner gender_spinner;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private ProgressDialog progressDialog;
    private AlertDialog errorDialog;

    private static SharedPreferences pref;

    private ArrayAdapter<CharSequence> gender_arr_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D98A67")));
        getSupportActionBar().setElevation(0);

        initializeDialog();
        showLoading();

        pref = getApplicationContext().getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);

        getProfile();

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        setUpViewById();


        // Create an ArrayAdapter using the string array and a default spinner layout
        gender_arr_adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_value, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        gender_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
        gender_spinner.setAdapter(gender_arr_adapter);

        setDateTimeField();

    }
    public void initializeDialog(){
        progressDialog = new ProgressDialog(EditProfileActivity.this,
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
        usernameEtxt = (EditText) findViewById(R.id.row1editText);
        locationEtxt = (EditText) findViewById(R.id.row2editText);
        emailEtxt = (EditText) findViewById(R.id.row3editText);
        birthdayEtxt = (EditText) findViewById(R.id.row4editText);
        phoneEtxt = (EditText) findViewById(R.id.row6editText);
        introductionEtxt = (EditText) findViewById(R.id.row7editText);
        gender_spinner = (Spinner) findViewById(R.id.row5spinner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
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
            updateProfile();
            setResult(200, null);
            finish();
        }

        if (id == android.R.id.home) {
            Log.e(TAG, "-> click!"+ item.getItemId()+"");
            setResult(400, null);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    private void setDateTimeField() {
        birthdayEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthdayEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


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

    private void updateProfile(){
        Log.e("Update User Profile", "-> Update Profile");

        // the request
        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            nameValuePairs.add(new BasicNameValuePair("uid", pref.getString("uid", null)));
            nameValuePairs.add(new BasicNameValuePair("gender", gender_spinner.getSelectedItemPosition()+""));
            nameValuePairs.add(new BasicNameValuePair("tel", phoneEtxt.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("birthday", birthdayEtxt.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("nickname", usernameEtxt.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("email", emailEtxt.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("introduction", introductionEtxt.getText().toString()));

            APIConnection.SetAsyncCaller(this, getApplicationContext());
            APIConnection.updateUserProfile(Integer.parseInt(pref.getString("uid", null).trim()), nameValuePairs);

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    public void onClick(View view) {
        if(view == birthdayEtxt) {
            datePickerDialog.show();
        }
    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException {
        String response = result.toString();
        Log.i(TAG, "requestCode= " + requestCode);

        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject finalResult = new JSONObject(tokener);
            Log.i(TAG, "RESPONSE CODE= " + finalResult.getString("success"));
            Log.i(TAG, "RESPONSE BODY= " + response);

            if(finalResult.getString("success").equals("true")){
                if(finalResult.getString("code").equals("150")){
                    hideLoading();
                    // Parse user json object
                    UserEntity user = (new UserEntityJsonMapper()).transformUserEntity(response);
                    Log.i(TAG, "User Info= " + user.toString());
                    renderUser(user); //Render user
                }else if(finalResult.getString("code").equals("140")){
                    Log.i(TAG, finalResult.getString("message"));
                }

            }else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void renderUser(UserEntity user) {
        usernameEtxt.setText(user.getNickname());
        locationEtxt.setText("");
        emailEtxt.setText(user.getEmail());
        birthdayEtxt.setText(user.getBirthday());
        phoneEtxt.setText(user.getTel());
        introductionEtxt.setText(user.getIntroduction());
        gender_spinner.setSelection(Integer.parseInt(user.getGender()));
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
