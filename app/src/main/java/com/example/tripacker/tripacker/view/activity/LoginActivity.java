package com.example.tripacker.tripacker.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;

import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.RestTask;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncJsonPostTask;
import com.example.tripacker.tripacker.ws.remote.WebServices;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

//import butterknife.ButterKnife;
//import butterknife.InjectView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginActivity extends AppCompatActivity implements AsyncCaller{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


/*  @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
*/


//    private TextInputEditText usernameText;
//    private TextInputEditText passwordText;
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    // User Session Manager Class
    private UserSessionManager session;

    private String user_username = "";
    private String user_id = "";


    // Configuration for calling a REST service
    //private static final String TEST_URL                   = "http://47.88.12.177/api/member/login/dologin";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Setup view elements
//        usernameText = (TextInputEditText) findViewById(R.id.input_username);
//        passwordText = (TextInputEditText) findViewById(R.id.input_password);
        usernameText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);



        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

//        ButterKnife.inject(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed("Invalid input");
            return;
        }

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        Log.e("User Authentication", "-------> Starting");
        // TODO: Implement your own authentication logic here.
        getContent();  //starts the RestTask



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        // Creating user login session
        session.createUserLoginSession(user_username, user_id, APIConnection.getCookies());
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        //if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        if (username.isEmpty()) {
            usernameText.setError("enter a valid username");
            valid = false;
        } else {
            usernameText.setError(null);

        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private void getContent(){
        Log.e("User Authentication", "-------> Get Content");
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        // the request
        try{
            Log.e("User Authentication", "-------> Get Content1");
            APIConnection.SetAsyncCaller(this, getApplicationContext());
            Log.e("User Authentication", "-------> Get Content2");
            APIConnection.authenticateUser(nameValuePairs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {
        // clear the progress indicator
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        String response = result.toString();

        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject finalResult = new JSONObject(tokener);
            Log.i(TAG, "RESPONSE CODE= " + finalResult.getString("success"));


            if(finalResult.getString("success").equals("true")){


                Log.i(TAG, "RESPONSE BODY= " + response);
                // Parse session json object
                user_username = finalResult.getString("username");
                user_id = finalResult.getString("uid");
                onLoginSuccess();

            }else{
                onLoginFailed(finalResult.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
