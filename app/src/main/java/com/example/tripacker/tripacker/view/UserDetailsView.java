package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.TripEntity;
import com.example.tripacker.tripacker.entity.UserEntity;

import java.util.ArrayList;

/**
 * Created by angelagao on 4/14/16.
 */
public interface UserDetailsView extends LoadDataView {
    void renderUser(UserEntity user);
    void renderTrip(ArrayList<TripEntity> TripEntities);
}
