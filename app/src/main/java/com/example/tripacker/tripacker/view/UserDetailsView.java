package com.example.tripacker.tripacker.view;

import com.example.tripacker.tripacker.entity.User;
import com.example.tripacker.tripacker.view.LoadDataView;

/**
 * Created by angelagao on 4/14/16.
 */
public interface UserDetailsView extends LoadDataView {
    void renderUser(User user);
}
