package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;
import com.example.tripacker.tripacker.entity.UserProfileEntity;

import java.util.ArrayList;

/**
 * Created by angelagao on 4/14/16.
 */
public interface UserProfileView extends LoadDataView {
    void renderUserProfile(UserProfileEntity user);
    void renderTrip(ArrayList<TripEntity> TripEntities);
}
