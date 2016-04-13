package com.example.tripacker.tripacker.ws;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * Manages getting the Json response for a webservice call.
 * Takes up to three parameters; a String defining the method call to be made on the webservices,
 * a Json string as the payload for the POST and a class variable defining
 * the class of Java object that the result is to be converted to (optional).
 * The result is parsed from Json and returned to the calling activity through the onBackgroundTaskCompleted()
 * method defined in the AsyncCaller interface.
 * @author Saad Farooq
 *
 */
public class AsyncJsonPostTask extends AsyncTask<Object, Void, Object> {
	
	final static String TAG = "AsyncJsonPostTask";
	private AsyncCaller activity;
	private int requestCode = 0;
	private boolean startingCallback;
		
	/**
	 * Perform a POST to a server and optionally convert response from Json string into a Java object
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently
	 * @param activity
	 */
	public AsyncJsonPostTask (AsyncCaller activity) {
		this.activity = activity;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}
	
	/**
	 * Perform a POST to a server and optionally convert response from Json string into a Java object
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently
	 * @param activity
	 */
	public AsyncJsonPostTask (AsyncCaller activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
	}
	
	@Override
	protected void onPreExecute() {
		if (startingCallback) {
			((AsyncStarting) activity).onBackgroundTaskStarted();
		}
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		
		String response = WebServices.httpPost((HttpUriRequest)params[0], (String)params[1]);
	
		if (params.length == 3) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(response);
			jsonObject = jsonObject.get("d").getAsJsonObject();
			
			Object result = new Gson().fromJson(jsonObject, (Class<?>) params[2]);
			
			return result;
		} else {
			return response;
		}
		
	}
	
	@Override
	protected void onPostExecute(Object result) {
		activity.onBackgroundTaskCompleted(requestCode, result);
	}
}
