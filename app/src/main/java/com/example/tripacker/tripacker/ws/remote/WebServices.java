package com.example.tripacker.tripacker.ws.remote;

import android.util.Base64;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public class WebServices {
    static final String TAG = "WebServices";
    static final int CONNECTION_TIMEOUT = 1000;
    static final int SOCKET_TIMOUT = 2000;
    static DefaultHttpClient client = getThreadSafeClient();
    private static String BASE_URL;
    private static String WS_USERNAME;
    private static String WS_PASSWORD;
    private static boolean DEBUG = false;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setURL(String url) {
        BASE_URL = url;
    }

    public static void setURL(String url, boolean debug) {
        BASE_URL = url;
        DEBUG = debug;
    }

    public static void setURL(String url, String username, String password) {
        BASE_URL = url;
        WS_USERNAME = username;
        WS_PASSWORD = password;
    }

    public static void setURL(String url, String username, String password, boolean debug) {
        BASE_URL = url;
        WS_USERNAME = username;
        WS_PASSWORD = password;
        DEBUG = debug;
    }


    public static HttpResponse httpGet(HttpUriRequest requestGet, String payLoad) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMOUT);

        HttpUriRequest request = requestGet;

        if (WS_USERNAME != null && WS_PASSWORD != null) {
            Log.d(TAG, "Setting request credentials");
            // Apparently this doesn't work with POST Request
            //client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(WS_USERNAME,WS_PASSWORD));
            request.setHeader("Authorization", "Basic " + Base64.encodeToString((WS_USERNAME + ":" + WS_PASSWORD).getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
        }

        Header[] cookie = request.getHeaders("Set-Cookie");
        for (int i = 0; i < cookie.length; i++) {
            Header h = cookie[i];
            Log.i(TAG, "%% Cookie Header names: " + h.getName());
            Log.i(TAG, "%% Cookie Header Value: " + h.getValue());
            APIConnection.setCookie(h.getValue());//set cookies
        }
        HttpResponse jsonResponse = null;
        Log.d(TAG, "HttpGetURL: " + requestGet.getURI());

        try {
            //	request.setEntity(new StringEntity(payLoad, "UTF-8"));
            request.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
            BasicResponseHandler handler = new BasicResponseHandler();
            //	if (DEBUG) Log.d(TAG, "Executing post request with payload "+payLoad);
            //    jsonResponse = client.execute(request, handler);
            jsonResponse = client.execute(request);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return jsonResponse;
    }

    public static HttpResponse httpPost(HttpUriRequest requestPost, String payLoad) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMOUT);

        HttpUriRequest request = requestPost;

        if (WS_USERNAME != null && WS_PASSWORD != null) {
            Log.d(TAG, "Setting request credentials");
            // Apparently this doesn't work with POST Request
            //client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(WS_USERNAME,WS_PASSWORD));
            request.setHeader("Authorization", "Basic " + Base64.encodeToString((WS_USERNAME + ":" + WS_PASSWORD).getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
        }

        HttpResponse jsonResponse = null;
        Log.d(TAG, "HttpPost URL: " + requestPost.getURI());

        try {
            //	request.setEntity(new StringEntity(payLoad, "UTF-8"));
            request.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
            BasicResponseHandler handler = new BasicResponseHandler();
            //	if (DEBUG) Log.d(TAG, "Executing post request with payload "+payLoad);
            //    jsonResponse = client.execute(request, handler);
            jsonResponse = client.execute(request);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return jsonResponse;
    }


    public static HttpResponse httpPut(HttpUriRequest requestPut, String payLoad) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMOUT);

        HttpUriRequest request = requestPut;

        if (WS_USERNAME != null && WS_PASSWORD != null) {
            Log.d(TAG, "Setting request credentials");
            // Apparently this doesn't work with POST Request
            //client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials(WS_USERNAME,WS_PASSWORD));
            request.setHeader("Authorization", "Basic " + Base64.encodeToString((WS_USERNAME + ":" + WS_PASSWORD).getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
        }

        HttpResponse jsonResponse = null;
        Log.d(TAG, "HttpPut URL: " + requestPut.getURI());

        try {
            request.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");
            BasicResponseHandler handler = new BasicResponseHandler();
            //	if (DEBUG) Log.d(TAG, "Executing post request with payload "+payLoad);
            //    jsonResponse = client.execute(request, handler);
            jsonResponse = client.execute(request);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return jsonResponse;
    }

    private static DefaultHttpClient getThreadSafeClient() {

        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,

                mgr.getSchemeRegistry()), params);
        return client;
    }

}
