package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.UserEntity;

/**
 * Created by angelagao on 4/14/16.
 */
public interface UserEditProfileDetailsView extends LoadDataView {
    void renderUser(UserEntity user);
}
