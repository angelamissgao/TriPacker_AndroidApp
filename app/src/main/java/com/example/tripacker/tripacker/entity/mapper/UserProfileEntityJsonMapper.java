package com.example.tripacker.tripacker.entity.mapper;

import android.util.Log;

import com.example.tripacker.tripacker.entity.UserProfileEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class UserProfileEntityJsonMapper {
    private static final String TAG = "UserProfileJsMapper";
    private final Gson gson;


    public UserProfileEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link UserProfileEntity}.
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link UserProfileEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public UserProfileEntity transformUserProfileEntity(String userJsonResponse) throws JsonSyntaxException {
        Log.i(TAG, "" + userJsonResponse);
        try {
            UserProfileEntity userEntity = this.gson.fromJson(userJsonResponse, UserProfileEntity.class);

            return userEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link UserProfileEntity}.
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of {@link UserProfileEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<UserProfileEntity> transformUserEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {

        List<UserProfileEntity> userEntityCollection;
        try {
            Type listOfUserEntityType = new TypeToken<List<UserProfileEntity>>() {
            }.getType();
            userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

            return userEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}

