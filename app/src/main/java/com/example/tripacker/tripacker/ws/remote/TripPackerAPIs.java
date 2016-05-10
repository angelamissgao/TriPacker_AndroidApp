package com.example.tripacker.tripacker.ws.remote;


import org.apache.http.NameValuePair;

import java.util.List;

public class TripPackerAPIs {

    final static String API_BASE_URL = WebServices.getBaseUrl();


    // api/v1/member/register
    public static String registerUser() {
        return API_BASE_URL + "/member/register";
    }

    public static String loginUser() {
        return API_BASE_URL + "/member/login";
    }

    // api/v1/member/:id/logout
    public static String logoutUser(String user_id) {
        return API_BASE_URL + "/member/" + user_id + "/logout";
    }
    // Get User

    // api/v1/member/:id
    public static String getUserPublicProfile(int user_id) {
        return API_BASE_URL + "/member/" + user_id;
    }

    // api/v1/member/:id/profile
    public static String getUserProfile(int user_id) {
        return API_BASE_URL + "/member/" + user_id + "/profile";
    }

    // api/v1/member/:id/profile
    public static String updateUserProfile(int user_id) {
        return API_BASE_URL + "/member/" + user_id + "/profile";
    }

    // Get Trip

    public static String createTrip() {
        return API_BASE_URL + "/trip/create";
    }

    public static String getPopularTrips() {
        return API_BASE_URL + "/trips/polular";
    }

    public static String getPopularTripsByPage() {
        return API_BASE_URL + "/trips/polular";
    }

    public static String getTripDetail(int trip_id) {
        return API_BASE_URL + "/trip/" + trip_id;
    }

    public static String getTripsByRate(List<NameValuePair> params) {
        String url = API_BASE_URL + "/trip/getByRate";
        if (params.size() != 0) {
            url += "?";
            for (int i = 0; i < params.size(); i++) {
                url += params.get(i).getName();
                url += "=";
                url += params.get(i).getValue();
                if (i != params.size() - 1) {
                    url += "&";
                }
            }
        }
        return url;
    }

    public static String getTripsByOwner(int uid) {
        return API_BASE_URL + "/trip/getByOwner/" + uid;
    }

    public static String editTrip(int trip_id) {
        return API_BASE_URL + "/trip/" + trip_id;
    }

    // Get Spot

    public static String getSpotsList(String id, List<NameValuePair> params) {
        String url = API_BASE_URL + "/spot/getByCity/" + id;
        if (params.size() != 0) {
            url += "?";
            for (int i = 0; i < params.size(); i++) {
                url += params.get(i).getName();
                url += "=";
                url += params.get(i).getValue();
                if (i != params.size() - 1) {
                    url += "&";
                }
            }
        }
        return url;
    }

    public static String createSpot() {
        return API_BASE_URL + "/spot/create";
    }

    public static String editSpot(String spotId) {
        return API_BASE_URL + "/spot/" + spotId;
    }

    public static String getPopularSpots() {
        return API_BASE_URL + "/spots/polular";
    }

    public static String getPopularSpotsByPage() {
        return API_BASE_URL + "/spots/polular";
    }

    public static String getSpotDetail(String spotId) {
        return API_BASE_URL + "/spot/" + spotId;
    }


    // Get configuration

    public String getConfiguration() {
        return API_BASE_URL + "/configuration";
    }

}
