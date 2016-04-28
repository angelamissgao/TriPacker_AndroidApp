package com.example.tripacker.tripacker.ws.remote;


public class TripPackerAPIs {

    final static String API_BASE_URL = WebServices.getBaseUrl();


    public static String loginUser(){
        return API_BASE_URL+"/member/login";
    }
    // Get User

    public static String getUserProfile(int user_id){
        return API_BASE_URL+"/member/profile/getprofile";
    }
    public static String updateUserProfile(int user_id){
        return API_BASE_URL+"/member/"+user_id+"/profile";
    }

    // Get Trip

    public static String getPopularTrips(){
        return API_BASE_URL+"/trips/polular";
    }

    public static String getPopularTripsByPage(){
        return API_BASE_URL+"/trips/polular";
    }

    public static String getTripDetail(int trip_id){
        return API_BASE_URL+"/trips/"+trip_id;
    }

    // Get Spot

    public static String getSpotsList() {return API_BASE_URL + "/spot/getByCity";}

    public static String createSpot() {return API_BASE_URL + "/spot/create";}

    public static String editSpot() {return API_BASE_URL + "/spot";}

    public static String getPopularSpots(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getPopularSpotsByPage(){
        return API_BASE_URL+"/spots/polular";
    }

    public static String getSpotDetail(){return API_BASE_URL+"/spot";}


    // Get configuration

    public String getConfiguration(){
        return API_BASE_URL+"/configuration";
    }

}
