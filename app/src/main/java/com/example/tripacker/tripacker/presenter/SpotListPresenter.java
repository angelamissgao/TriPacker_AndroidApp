package com.example.tripacker.tripacker.presenter;

import android.support.annotation.NonNull;

import com.example.tripacker.tripacker.view.SpotListView;

/**
 * Created by angelagao on 4/14/16.
 */
public class SpotListPresenter implements Presenter {
    private SpotListView viewListView;
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void setView(@NonNull SpotListView view) {
        this.viewListView = view;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadSpotList();
    }

    /**
     * Loads all spots.
     */
    private void loadSpotList() {
        this.hideViewRetry();
        this.showViewLoading();
//        this.getUserList();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }


}
