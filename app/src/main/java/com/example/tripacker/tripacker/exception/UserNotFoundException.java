package com.example.tripacker.tripacker.exception;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class UserNotFoundException extends Exception {
    private Context context;
    private String exceptionTitle;
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Context context, final String message) {
        super(message);
    }

    public UserNotFoundException(Context context, final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Context context, final Throwable cause) {
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

