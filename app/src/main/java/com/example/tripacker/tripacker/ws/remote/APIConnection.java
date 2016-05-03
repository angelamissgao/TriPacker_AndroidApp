package com.example.tripacker.tripacker.ws.remote;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tripacker.tripacker.UserSessionManager;
import com.example.tripacker.tripacker.entity.mapper.UserEntityJsonMapper;
import com.example.tripacker.tripacker.exception.NetworkConnectionException;


import org.apache.http.Header;
import org.apache.http.HttpMessage;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
       // pref = context.getSharedPreferences("TripackerPref", Context.MODE_PRIVATE);
    }

    public static void setCookie(String cookie){
        UserSessionManager.getSingleInstance(context).setCookies(cookie);
    }


    public static void SetAsyncCaller(AsyncCaller asyncCallercaller, Context thecontext){
        caller = asyncCallercaller;
        context = thecontext;
    }

    public static void registerUser(List<NameValuePair> params) {
        registerUserFromApi(params);
    }

    public static void authenticateUser(List<NameValuePair> params) {
        authenticateUserFromApi(params);
    }

    public static void signoutUser(String user_id, List<NameValuePair> params) {
        signouteUserFromApi(user_id, params);
    }

    public static void getUserPublicProfile(int user_id, List<NameValuePair> params) {
        getUserPublicProfileFromApi(user_id, params);
    }

    public static void getUserProfile(int user_id, List<NameValuePair> params) {
        getUserProfileFromApi(user_id, params);
    }

    public static void updateUserProfile(int user_id, List<NameValuePair> params) {
        updateUserProfileFromApi(user_id, params);
    }

    public void getPopularTrips() {
    }

    public void getPopularTripsByPage() {
    }

    /**
     * Spots Request
     *
     */
    public static void getSpotsList(String id, List<NameValuePair> params) {
        getSpotsListFromApi(id, params);
    }

    public static void editSpot(String id, List<NameValuePair> params){
        editSpotFromApi(id, params);
    }

    public static void createSpot(List<NameValuePair> params) {
        createSpotFromApi(params);
    }

    public static void getSpotDetail(String spotId, List<NameValuePair> params) {
        getSpotDetailFromApi(spotId, params);
    }

    /**
     * Trip Request
     */

    public static void createTrip(List<NameValuePair> params){
        createTripFromApi(params);
    }

    public void getConfiguration() {
    }

    public static void getTripDetail(int trip_id, List<NameValuePair> params) {
        getTripDetailFromApi(trip_id, params);
    }
    public static void getTripsByRate(List<NameValuePair> params){
        getTripsByRateFromApi(params);
    }

    public static void getTripsByOwner(int uid, List<NameValuePair> params ){
        getTripsByOwnerFromApi(uid, params);
    }

    public static void editTrip(int tripId, List<NameValuePair> params ){
        editTripFromApi(tripId, params);
    }


    /**
     * each function to get Api and call Restful request
     * @param params
     */
    private static void registerUserFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.registerUser(), params);
    }
    private static void authenticateUserFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.loginUser(), params);
    }
    private static void signouteUserFromApi(String user_id, List<NameValuePair> params){
        createGetReq(TripPackerAPIs.logoutUser(user_id), params);
    }

    private static void getSpotsListFromApi(String id, List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getSpotsList(id, params), params);
    }

    private static void editSpotFromApi(String id, List<NameValuePair> params) {
        createPutReq(TripPackerAPIs.editSpot(id), params);
    }

    private static void createSpotFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.createSpot(), params);
    }

    private static void getSpotDetailFromApi(String spotId, List<NameValuePair> params) {
        createGetReq(TripPackerAPIs.getSpotDetail(spotId), params);
    }

    private static void createTripFromApi(List<NameValuePair> params){
        createPostReq(TripPackerAPIs.createTrip(), params);
    }

    private static void getTripDetailFromApi(int tripId, List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getTripDetail(tripId), params);
    }

    private static void getTripsByRateFromApi(List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getTripsByRate(params), params);
    }

    private static void getTripsByOwnerFromApi(int uid, List<NameValuePair> params) {
        createGetReq(TripPackerAPIs.getTripsByOwner(uid), params);
    }

    private static void editTripFromApi(int tripID,List<NameValuePair> params ){
        createPutReq(TripPackerAPIs.editTrip(tripID), params);
    }

/*    private String getUserEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApi.API_URL_GET_USER_LIST).requestSyncCall();
    }*/

    private static void getUserPublicProfileFromApi(int user_id, List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getUserPublicProfile(user_id), params);
    }
    // Current user
    private static void getUserProfileFromApi(int user_id, List<NameValuePair> params){
        createGetReq(TripPackerAPIs.getUserProfile(user_id), params);
    }

    private static void updateUserProfileFromApi(int user_id, List<NameValuePair> params){
        createPutReq(TripPackerAPIs.getUserProfile(user_id), params);
    }


    private static void createDeleteReq(String url, List<NameValuePair> params){
    }

    private static void createPutReq(String url, List<NameValuePair> params){
        if (true) {
            HttpPut httpPut = new HttpPut(url);
            setRequestCookies(httpPut);

            AsyncJsonPutTask putTask = new AsyncJsonPutTask(caller);
            try {
                httpPut.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            putTask.execute(httpPut, "");
        } else {
            try {
                throw new NetworkConnectionException();
            } catch (NetworkConnectionException e) {
                e.displayMessageBox("Error", "NetworkConnectionException");
            }
        }
    }

    private static void createPostReq(String url, List<NameValuePair> params){
        Log.e("Network? ", ""+isThereInternetConnection());
            if (isThereInternetConnection()) {
                HttpPost httpPost = new HttpPost(url);
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
                    e.displayMessageBox("Network not available", "Please check your network connection!");
                }
            }
    }


    private static void createGetReq(String url, List<NameValuePair> params) {
        Log.e("Network? ", ""+isThereInternetConnection());
        if (isThereInternetConnection()) {
            HttpGet httpGet = new HttpGet(url);
            Log.e("Cookie? ", UserSessionManager.getSingleInstance(context).getCookies()+".");
            setRequestCookies(httpGet);

            AsyncJsonGetTask getTask = new AsyncJsonGetTask(caller);
            getTask.execute(httpGet, "");

        } else {
            try {
                throw new NetworkConnectionException();
            } catch (NetworkConnectionException e) {
                e.displayMessageBox("Error", "NetworkConnectionException");
                e.displayMessageBox("Network not available", "Please check your network connection!");

            }
        }
    }


    /**
     * set request header
     * @param reqMsg
     */
    private static void setRequestCookies(HttpMessage reqMsg) {
        UserSessionManager session = UserSessionManager.getSingleInstance(context);
        if(session.isUserLoggedIn() && !session.getUserDetails().get("cookies").isEmpty()){
            reqMsg.setHeader("Set-Cookie", session.getUserDetails().get("cookies"));
        }
    }
    /**
     * append the old cookies to the new cookies
     * @param resMsg
     */
    private static void appendCookies(HttpMessage resMsg) {
        UserSessionManager session = UserSessionManager.getSingleInstance(context);
        Header setCookieHeader=resMsg.getFirstHeader("Set-Cookie");
        if (setCookieHeader != null
                && session.getUserDetails().get("cookies").isEmpty()) {
            String setCookie=setCookieHeader.getValue();
            if(session.getCookies().isEmpty()){
                session.setCookies(setCookie);
            }else{
                session.setCookies(session.getCookies()+"; "+setCookie);
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
