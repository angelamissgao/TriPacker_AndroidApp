package com.example.tripacker.tripacker.entity.mapper;

import android.util.Log;

import com.example.tripacker.tripacker.entity.UserEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class TripEntityJsonMapper {
    private static final String TAG = "UserEntityJsonMapper";
    private final Gson gson;


    public TripEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link UserEntity}.
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link UserEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public UserEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
        try {
            UserEntity userEntity = this.gson.fromJson(userJsonResponse, UserEntity.class);

            return userEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link UserEntity}.
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of {@link UserEntity}.
     * @throws JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<UserEntity> transformUserEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {

        List<UserEntity> userEntityCollection;
        try {
            Type listOfUserEntityType = new TypeToken<List<UserEntity>>() {}.getType();
            userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

            return userEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}

