package com.example.tripacker.tripacker.exception;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by angelagao on 5/6/16.
 */
public class GPSProviderException extends Exception {
    private Context context;
    private String exceptionTitle;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public GPSProviderException() {
        super();
    }

    public GPSProviderException(Context context) {
        super();
        this.context = context;
    }

    public GPSProviderException(Context context, final String message) {
        super(message);
    }

    public GPSProviderException(Context context, final String message, final Throwable cause) {
        super(message, cause);
    }

    public GPSProviderException(Context context, final Throwable cause) {
        super(cause);
    }

    public AlertDialog.Builder displayMessageBox() {
        String message = "GPS providing get Error!";
        Log.d("EXCEPTION: " + exceptionTitle, message);

        builder = new AlertDialog.Builder(context);
        dialog = builder.create();

        builder.setTitle("ERROR !!");
        builder.setMessage(message);
        return builder;
    }
}
