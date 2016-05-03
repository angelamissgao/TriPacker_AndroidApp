package com.example.tripacker.tripacker.exception;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class NetworkConnectionException extends Exception {
    private Context context;
    private String exceptionTitle;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public NetworkConnectionException() {
        super();
    }

    public NetworkConnectionException(Context context) {
        super();
        this.context = context;
    }

    public NetworkConnectionException(Context context, final String message) {
        super(message);
    }

    public NetworkConnectionException(Context context, final String message, final Throwable cause) {
        super(message, cause);
    }

    public NetworkConnectionException(Context context, final Throwable cause) {
        super(cause);
    }
    public AlertDialog.Builder displayMessageBox() {
        String message = "Sorry there was an error getting data from the Internet. Network Unavailable!";
        Log.d("EXCEPTION: " + exceptionTitle, message);

        builder = new AlertDialog.Builder(context);
        dialog = builder.create();

        builder.setTitle("ERROR !!");
        builder.setMessage(message);
        return builder;
    }
}
