package com.example.tripacker.tripacker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import java.net.URI;
import org.apache.http.client.methods.HttpGet;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    // User Session Manager Class
    private UserSessionManager session;
    private boolean isAuthenticated = false;


    // Configuration for calling a REST service
    private static final String TEST_URL                   = "http://47.88.12.177/api/backdoor/login";
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

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
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        Log.e("User Authentication", "-------> Starting");
        // TODO: Implement your own authentication logic here.
        getContent();  //starts the RestTask
        if(username.equals("eileenwei") && password.equals("800105")){
            // Creating user login session
            session.createUserLoginSession("Eileen Wei", "eileenwei0105@gmail.com");
            Log.e("User Authentication", "-------> success!");
            isAuthenticated = true;
        }else{
            // username / password doesn't match&
            Toast.makeText(getApplicationContext(),
                    "Username/Password is incorrect",
                    Toast.LENGTH_LONG).show();
            isAuthenticated = false;
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(isAuthenticated)
                            onLoginSuccess();
                        else
                            onLoginFailed();

                        progressDialog.dismiss();
                    }
                }, 3000);
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
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        //if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        if (username.isEmpty()) {

            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);

        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void getContent(){
        // the request
        try{
            HttpGet httpGet = new HttpGet(new URI(TEST_URL));
            RestTask task = new RestTask(this, ACTION_FOR_INTENT_CALLBACK);
            task.execute(httpGet); //doInBackground runs
            progress = ProgressDialog.show(this, "Getting Data ...", "Waiting For Results...", true);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.unregisterReceiver(receiver);
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
                    "Response is ready: "+response,
                    Toast.LENGTH_LONG).show();
            Log.i(TAG, "RESPONSE = " + response);
            //
            // my old json code was here. this is where you will parse it.
            //
        }
    };
}
