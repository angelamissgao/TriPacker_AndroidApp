package com.example.tripacker.tripacker.ws.remote;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;

import java.io.IOException;

/**
 * Manages getting the Json response for a webservice call.
 * Takes up to three parameters; a String defining the method call to be made on the webservices,
 * a Json string as the payload for the POST and a class variable defining
 * the class of Java object that the result is to be converted to (optional).
 * The result is parsed from Json and returned to the calling activity through the onBackgroundTaskCompleted()
 * method defined in the AsyncCaller interface.
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

		HttpResponse response = WebServices.httpPost((HttpUriRequest) params[0], (String) params[1]);

		int code = response.getStatusLine().getStatusCode();
		String responseBody = "";

		Log.i(TAG, "RESPONSE CODE= " + code);

		if(code == 200) {

			// Get cookie
			Header[] cookie = response.getHeaders("Set-Cookie");
			for (int i = 0; i < cookie.length; i++) {
				Header h = cookie[i];
				Log.i(TAG, "Cookie Header names: " + h.getName());
				Log.i(TAG, "Cookie Header Value: " + h.getValue());
				APIConnection.setCookie(h.getValue());//set cookies

			}
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			// Response Body
			try {
				responseBody = responseHandler.handleResponse(response);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Log.i(TAG, "RESPONSE BODY= " + responseBody);
			// Parse user json object
		}else{
			Log.i(TAG, "Some Error");
		}

		return responseBody;
		/*
		if (params.length ==3) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(response);
			jsonObject = jsonObject.get("d").getAsJsonObject();
			
			Object result = new Gson().fromJson(jsonObject, (Class<?>) params[2]);
			
			return result;
		} else {
			return response;
		}*/
		
	}
	
	@Override
	protected void onPostExecute(Object result) {
		try {
			activity.onBackgroundTaskCompleted(requestCode, result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
