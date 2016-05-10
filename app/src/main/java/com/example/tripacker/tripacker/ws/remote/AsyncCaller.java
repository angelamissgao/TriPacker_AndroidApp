package com.example.tripacker.tripacker.ws.remote;

import org.json.JSONException;

public interface AsyncCaller {

    public void onBackgroundTaskCompleted(int requestCode, Object result) throws JSONException;

}
