package com.example.tripacker.tripacker.ws.remote;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.exception.NetworkConnectionException;

import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
    private static String cookies = "";

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

    public static void setCookies(String thiscookies){
        cookies = thiscookies;
    }
    public static String getCookies(){
        return cookies;
    }
    public static void SetAsyncCaller(AsyncCaller asyncCallercaller, Context thecontext){
        caller = asyncCallercaller;
        context = thecontext;
    }


    public static void authenticateUser(List<NameValuePair> params) {
        authenticateUserFromApi(params);
    }

    public static void getUserProfile(int user_id) {
        getUserProfileFromApi(user_id);
    }

    public void getPopularTrips() {
    }

    public void getPopularTripsByPage() {
    }

    public void getTripDetail(int trip_id) {
    }

    public void getPopularSpots() {
    }

    public void getPopularSpotsByPage() {
    }

    public void getSpotDetail(int spot_id) {
    }

    public void getConfiguration() {
    }

    private static void authenticateUserFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.loginUser(), params);
    }

/*    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall();
    }*/

    private static void getUserProfileFromApi(int userId){
        createGetReq(TripPackerAPIs.getUserProfile(0), userId);
    }

    private static void createGetReq(String url, int id){
        //if (isThereInternetConnection()) {
        if (true) {
            HttpGet httpGet = new HttpGet(url);
            setRequestCookies(httpGet);
            AsyncJsonGetTask getTask = new AsyncJsonGetTask(caller);
    /*        try {
                httpGet.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/

            getTask.execute(httpGet, "");
        } else {
            try {
                throw new NetworkConnectionException();
            } catch (NetworkConnectionException e) {
                e.displayMessageBox("Error", "NetworkConnectionException");
            }
        }


    }

    private static void createPostReq(String url, List<NameValuePair> params){
        //if (isThereInternetConnection()) {
            if (true) {
                HttpPost httpPost = new HttpPost(TripPackerAPIs.loginUser());
                setRequestCookies(httpPost);
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

    /**
     * set request header
     * @param reqMsg
     */
    private static void setRequestCookies(HttpMessage reqMsg) {
        if(!cookies.isEmpty()){
            reqMsg.setHeader("Set-Cookie", cookies);
        }
    }
    /**
     * append the old cookies to the new cookies
     * @param resMsg
     */
    private static void appendCookies(HttpMessage resMsg) {
        Header setCookieHeader=resMsg.getFirstHeader("Set-Cookie");
        if (setCookieHeader != null
                && cookies.isEmpty()) {
            String setCookie=setCookieHeader.getValue();
            if(cookies.isEmpty()){
                cookies=setCookie;
            }else{
                cookies=cookies+"; "+setCookie;
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
