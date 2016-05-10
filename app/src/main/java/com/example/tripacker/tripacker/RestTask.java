package com.example.tripacker.tripacker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Android RestTask (REST) from the Android Tripacker.
 */
public class RestTask extends AsyncTask<HttpUriRequest, Void, String> {


    public static final String HTTP_RESPONSE = "httpResponse";
    private static final String TAG = "AARestTask";
    private Context mContext;
    private HttpClient mClient;
    private String mAction;

    public RestTask(Context context, String action) {
        mContext = context;
        mAction = action;
        mClient = new DefaultHttpClient();
    }

    public RestTask(Context context, String action, HttpClient client) {
        mContext = context;
        mAction = action;
        mClient = client;
    }

    /*
    * the worker method that gets called on the background thread
    * when execute() is invoked on an instance of AysncTask
    * */
    @Override
    protected String doInBackground(HttpUriRequest... params) {
        try {
            Log.e("--> doInBackground: ", "start...");
            HttpUriRequest request = params[0];
            HttpResponse serverResponse = mClient.execute(request);
            serverResponse.getHeaders("Cookie");
            BasicResponseHandler handler = new BasicResponseHandler();
            return handler.handleResponse(serverResponse);
        } catch (Exception e) {
            // TODO handle this properly
            e.printStackTrace();
            return "";
        }
    }

    // When the RestTask is finished, the onPostExecute method is executed
    @Override
    protected void onPostExecute(String result) {
        Log.i(TAG, "RESULT = " + result);
        Intent intent = new Intent(mAction);
        intent.putExtra(HTTP_RESPONSE, result);

        // broadcast the completion
        mContext.sendBroadcast(intent);
    }

}

