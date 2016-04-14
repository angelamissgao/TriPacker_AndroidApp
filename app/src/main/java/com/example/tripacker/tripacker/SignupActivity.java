package com.example.tripacker.tripacker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripacker.tripacker.ws.AsyncCaller;
import com.example.tripacker.tripacker.ws.AsyncJsonPostTask;
import com.example.tripacker.tripacker.ws.WebServices;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity implements AsyncCaller {
    private static final String TAG = "SignupActivity";
/*
    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
*/


    private EditText usernameText;
    private EditText passwordText;
    private EditText emailText;
    private Button signupButton;
    private TextView loginLink;

    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//      ButterKnife.inject(this);

        // Setup view elements
        usernameText = (EditText) findViewById(R.id.input_username);
        emailText = (EditText) findViewById(R.id.input_email);
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
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        getContent();

    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
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
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        // the request
        try{
        /*    HttpPost httpPost = new HttpPost(new URI(TEST_URL));
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", "eileen"));
            nameValuePairs.add(new BasicNameValuePair("password", "111111"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            RestTask task = new RestTask(this, ACTION_FOR_INTENT_CALLBACK);
            task.execute(httpPost); //doInBackground runs*/


            HttpPost httpPost = new HttpPost(new URI(WebServices.getBaseUrl()+"/member/register/doregister"));
            AsyncJsonPostTask postTask = new AsyncJsonPostTask(this);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            postTask.execute(httpPost, "");

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onBackgroundTaskCompleted(int requestCode, Object result) {

        progressDialog.dismiss();

        // clear the progress indicator
        if (progressDialog != null){
            progressDialog.dismiss();
        }

        HttpResponse response = (HttpResponse) result;
        int code = response.getStatusLine().getStatusCode();
        Log.i(TAG, "RESPONSE CODE= " + code);
        if(code == 200){
            onSignupSuccess();

            // Get cookie
            Header[] cookie = response.getHeaders("Set-Cookie");
            for (int i=0; i < cookie.length; i++) {
                Header h = cookie[i];
                Log.i(TAG, "Cookie Header names: "+h.getName());
                Log.i(TAG, "Cookie Header Value: "+h.getValue());
            }
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = "";
            // Response Body
            try {
                responseBody = responseHandler.handleResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "RESPONSE BODY= " + responseBody);
            // Parse user json object
        }else{
            onSignupFailed();
        }
    }
}
