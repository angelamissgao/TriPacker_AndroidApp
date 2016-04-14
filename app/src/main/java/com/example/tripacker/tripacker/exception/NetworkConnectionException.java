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
    public NetworkConnectionException() {
        super();
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
    public void displayMessageBox(String exceptionTitle, String message) {
        Log.d("EXCEPTION: " + exceptionTitle, message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
        messageBox.setTitle(exceptionTitle);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }
}
