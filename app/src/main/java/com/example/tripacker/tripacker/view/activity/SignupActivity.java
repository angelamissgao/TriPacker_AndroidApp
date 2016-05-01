package com.example.tripacker.tripacker.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;
import com.example.tripacker.tripacker.ws.remote.AsyncJsonPostTask;
import com.example.tripacker.tripacker.ws.remote.WebServices;

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

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements AsyncCaller {
    private static final String TAG = "SignupActivity";

    private EditText usernameText;
    private EditText nameText;
    private EditText passwordText;
    private Button signupButton;
    private TextView loginLink;

    private ProgressDialog progressDialog;

    // User Session Manager Class
    private UserSessionManager session;

    private String user_username = "";
    private String user_id = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // Setup view elements
        usernameText = (EditText) findViewById(R.id.input_username);
        nameText = (EditText) findViewById(R.id.input_name);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed("Invalid input");
            return;
        }

        signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        getContent();

    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        session.createUserLoginSession(user_username, user_id, APIConnection.getCookies());
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

/*        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private void getContent(){
        String username = usernameText.getText().toString();
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("nickname", name));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        // the request
        try{
            APIConnection.SetAsyncCaller(this, getApplicationContext());
            APIConnection.registerUser(nameValuePairs);

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
                onSignupSuccess();

            }else{
                onSignupFailed(finalResult.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
