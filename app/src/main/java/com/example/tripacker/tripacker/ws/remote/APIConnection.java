package com.example.tripacker.tripacker.ws.remote;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.exception.NetworkConnectionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class APIConnection{

    private static Context context;
    private final UserEntityJsonMapper userEntityJsonMapper;
    private static AsyncCaller caller;

    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     * @param userEntityJsonMapper {@link UserEntityJsonMapper}.
     */
    public APIConnection(Context context, UserEntityJsonMapper userEntityJsonMapper) {
        if (context == null || userEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.userEntityJsonMapper = userEntityJsonMapper;
    }

    public static void SetAsyncCaller(AsyncCaller asyncCallercaller, Context thecontext){
        caller = asyncCallercaller;
        context = thecontext;
    }


    public static void authenticateUser(List<NameValuePair> params) {
        authenticateUserFromApi(params);
    }

    public void getUserDetail(int user_id) {
    }

    public void getPopularTrips() {
    }

    public void getPopularTripsByPage() {
    }

    public void getTripDetail(int trip_id) {
    }

    public static void getSpotsList(List<NameValuePair> params) {
        getSpotsListFromApi(params);

    }

    public void getPopularSpots() {
    }

    public void getPopularSpotsByPage() {
    }

    public void getSpotDetail(int spot_id) {
    }

    public void getConfiguration() {
    }


    /**
     * each function to get Api and call Restful request
     * @param params
     */
    private static void authenticateUserFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.loginUser(), params);
    }

    private static void getSpotsListFromApi(List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getSpotsList(), params);
    }

/*    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall();
    }

    private String getUserDetailsFromApi(int userId) throws MalformedURLException {
        String apiUrl = RestApi.API_URL_GET_USER_DETAILS + userId + ".json";
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }
*/
    private static void createPostReq(String url, List<NameValuePair> params){
        //if (isThereInternetConnection()) {
            if (true) {
                HttpPost httpPost = new HttpPost(url);
                AsyncJsonPostTask postTask = new AsyncJsonPostTask(caller);
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                postTask.execute(httpPost, "");
            } else {
                try {
                    throw new NetworkConnectionException();
                } catch (NetworkConnectionException e) {
                    e.displayMessageBox("Error", "NetworkConnectionException");
                }
            }
    }

    private static void createGetReq(String url,List<NameValuePair> params) {

        if (true) {
            HttpGet httpGet = new HttpGet(url);
            AsyncJsonGetTask getTask = new AsyncJsonGetTask(caller);
            try {
                httpGet.setHeader("test", "test");
            } catch (Exception e) {
                e.printStackTrace();
            }
            getTask.execute(httpGet, params);
            Log.e("createGetReq---->","Get Request sent!");
        } else {
            try {
                throw new NetworkConnectionException();
            } catch (NetworkConnectionException e) {
                e.displayMessageBox("Error", "NetworkConnectionException");
            }
        }
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private static boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

}
