package com.example.tripacker.tripacker.view;

import android.content.Context;

/**
 * Created by angelagao on 4/13/16.
 */
public interface LoadDataView {
    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    Context context();

}
