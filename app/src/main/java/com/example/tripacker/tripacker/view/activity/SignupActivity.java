package com.example.tripacker.tripacker.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.R;
import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.exception.NetworkConnectionException;
import com.example.tripacker.tripacker.ws.remote.APIConnection;
import com.example.tripacker.tripacker.ws.remote.AsyncCaller;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements AsyncCaller, View.OnClickListener {
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
    private String user_nickname = "";
    private String user_id = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // Setup view elements
        setUpViewById();
        setUpClickEvent();

    }

    private void setUpClickEvent() {
        signupButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.btn_signup:
                signup();
                break;
            case R.id.link_login:
                finish();
                break;
        }
    }

    private void setUpViewById() {
        usernameText = (EditText) findViewById(R.id.input_username);
        nameText = (EditText) findViewById(R.id.input_name);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);
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

        getContent();

    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        session.createUserLoginSession(user_username, user_nickname, user_id, session.getCookies());
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

    private void getContent() {
        String username = usernameText.getText().toString();
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();

        if (isThereInternetConnection()) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("nickname", name));
            nameValuePairs.add(new BasicNameValuePair("password", password));

            // the request
            try {
                APIConnection.SetAsyncCaller(this, getApplicationContext());
                APIConnection.registerUser(nameValuePairs);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new NetworkConnectionException(this);
            } catch (NetworkConnectionException e) {
                progressDialog.dismiss();
                Log.e("Network Error ", "-------> No internet");
                AlertDialog.Builder builder = e.displayMessageBox();
                showAlert(builder);
            }
        }

    }

    public void showAlert(AlertDialog.Builder builder) {

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                signupButton.setEnabled(true);
            }
        });

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                signup();
            }
        });
        AlertDialog dialog = builder.create(); // calling builder.create after adding buttons
        dialog.show();
        Toast.makeText(this, "Network Unavailable!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

        // clear the progress indicator
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        String response = result.toString();

        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject finalResult = new JSONObject(tokener);
            Log.i(TAG, "RESPONSE CODE= " + finalResult.getString("success"));


            if (finalResult.getString("success").equals("true")) {

                Log.i(TAG, "RESPONSE BODY= " + response);
                // Parse session json object
                user_username = finalResult.getString("username");
                user_nickname = finalResult.getString("nickname");
                user_id = finalResult.getString("uid");
                onSignupSuccess();

            } else {
                onSignupFailed(finalResult.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }


}
