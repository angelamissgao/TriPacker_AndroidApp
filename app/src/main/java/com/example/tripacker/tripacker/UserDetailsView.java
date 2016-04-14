package com.example.tripacker.tripacker;

import com.example.tripacker.tripacker.entity.User;

/**
 * Created by angelagao on 4/14/16.
 */
public interface UserDetailsView extends LoadDataView{
    void renderUser(User user);
}
