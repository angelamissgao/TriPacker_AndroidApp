package com.example.tripacker.tripacker.ws.remote;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Manages getting the Json response for a webservice call.
 * Takes three parameters; a String defining the method call to be made on the webservices, 
 * a HashMap of parameters to pass to the webservice and a class variable defining
 * the class of Java object that the result is to be converted to which is use by Gson.
 * The result is parsed as Json and returned to the calling activity through the onBackgroundTaskCompleted()
 * method defined in the AsyncCaller interface.
 *
 */
public class AsyncJsonGetTask extends AsyncTask<Object, Void, Object> {
	
	final static String TAG = "AsyncJsonGetTask";
	private AsyncCaller activity;
	private int requestCode = 0;
	private boolean startingCallback;
	
	
	/**
	 * Calls a webservice in the background and gets Json response
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently 
	 * 
	 * <h2>For the execute command</h2>
	 * <b>First parameter:</b> webservice call name, such as : <b>GetOrders</b> </br>
	 * <b>Second parameter:</b> List of name value pairs, sample code: </br></br>
	 * List{@literal <NameValuePair>} params = new ArrayList{@literal <NameValuePair>}(); </br>
	 * 		params.add(new BasicNameValuePair("name", value));</br>
	 * 
	 * @param activity activity which extends AsyncCaller abstract class
	 */
	public AsyncJsonGetTask (AsyncCaller activity) {
		this.activity = activity;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}
	
	/**
	 * Calls a webservice in the background and gets Json response
	 * <b>Note:</b> Retrieving Java objects works only with ASP.net services currently 
	 * 
	 * <h2>For the execute command</h2>
	 * <b>First parameter:</b> webservice call name, such as : <b>GetOrders</b> </br>
	 * <b>Second parameter:</b> List of name value pairs, sample code: </br></br>
	 * List{@literal <NameValuePair>} params = new ArrayList{@literal <NameValuePair>}(); </br>
	 * 		params.add(new BasicNameValuePair("name", value));</br>
	 * 
	 * @param activity
	 * @param requestCode
	 */
	public AsyncJsonGetTask(AsyncCaller activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
		startingCallback = AsyncStarting.class.isInstance((Object) activity);
	}

	@Override
	protected void onPreExecute() {
		if (startingCallback) {
			((AsyncStarting) activity).onBackgroundTaskStarted();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object doInBackground(Object... params) {

		HttpResponse response;

		if (params.length > 1) {
			response = WebServices.httpGet((HttpUriRequest) params[0], (String) params[1]);
		} else {
			response = WebServices.httpGet((HttpUriRequest) params[0], null);
		}

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
				APIConnection.setCookies(h.getValue()); //set cookies
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
